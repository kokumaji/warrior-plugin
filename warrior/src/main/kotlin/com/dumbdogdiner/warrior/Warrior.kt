package com.dumbdogdiner.warrior

import kr.entree.spigradle.annotations.PluginMain
import com.dumbdogdiner.warrior.api.WarriorAPI
import com.dumbdogdiner.warrior.api.WarriorLogger
import com.dumbdogdiner.warrior.util.DatabaseConnection
import java.io.IOException
import java.sql.SQLException
import com.dumbdogdiner.warrior.managers.ArenaManager
import com.dumbdogdiner.warrior.managers.LobbyManager
import com.dumbdogdiner.warrior.managers.GUIManager
import com.dumbdogdiner.warrior.managers.KitManager
import com.dumbdogdiner.warrior.managers.NotificationManager
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.managers.GameBarManager
import com.dumbdogdiner.warrior.managers.LevelManager
import com.dumbdogdiner.warrior.commands.DebugCommand
import com.dumbdogdiner.warrior.commands.misc.SettingsCommand
import com.dumbdogdiner.warrior.commands.misc.StatisticsCommand
import com.dumbdogdiner.warrior.commands.warrior.WarriorCommand
import com.dumbdogdiner.warrior.commands.warrior.WarriorHelpCommand
import com.dumbdogdiner.warrior.commands.warrior.WarriorAboutCommand
import com.dumbdogdiner.warrior.commands.warrior.WarriorLobbyCommand
import com.dumbdogdiner.warrior.commands.warrior.WarriorReloadCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaCreateCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaRemoveCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaJoinCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaSetupCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaRateCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaSpectateCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaLeaveCommand
import com.dumbdogdiner.warrior.commands.arena.ArenaFlagsCommand
import com.dumbdogdiner.warrior.commands.misc.SymbolCommand
import com.dumbdogdiner.warrior.commands.misc.SymbolsSearchCommand
import com.dumbdogdiner.warrior.commands.kit.KitCommand
import com.dumbdogdiner.warrior.commands.kit.KitCreateCommand
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities
import com.dumbdogdiner.warrior.api.translation.Translator
import com.dumbdogdiner.warrior.listeners.ArenaListener
import com.dumbdogdiner.warrior.listeners.ArenaSessionListener
import com.dumbdogdiner.warrior.listeners.GameStateListener
import com.dumbdogdiner.warrior.listeners.LobbySessionListener
import com.dumbdogdiner.warrior.listeners.PlayerListener
import com.dumbdogdiner.warrior.listeners.RegionExitListener
import com.dumbdogdiner.warrior.listeners.SessionChangeListener
import com.dumbdogdiner.warrior.listeners.ItemInteractListener
import com.dumbdogdiner.warrior.listeners.GameFlagListener
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Team
import java.lang.NoSuchFieldException
import java.lang.SecurityException
import java.lang.IllegalArgumentException
import java.lang.IllegalAccessException
import java.lang.reflect.Field
import java.util.ArrayList

@PluginMain
class Warrior : JavaPlugin() {

    companion object {

        private val cmds: MutableList<Command> = ArrayList()
        private lateinit var specTeam: Team

        lateinit var translator: Translator
        lateinit var connection: DatabaseConnection
        lateinit var pluginLogger: WarriorLogger
        lateinit var instance: Warrior

        // TODO: Replace with NMS
        private fun registerTeams() {
            val sb = Bukkit.getScoreboardManager().mainScoreboard
            var team = sb.getTeam("warrior_spec")
            if (team == null) {
                team = sb.registerNewTeam("warrior_spec")
                team.prefix = "§c§lSPEC §7"
                team.setAllowFriendlyFire(false)
                team.setCanSeeFriendlyInvisibles(true)
                team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM)
            }
            specTeam = team
        }

        fun usePlaceholderAPI(): Boolean {
            return (instance.config.getBoolean("general-settings.use-placeholderapi")
                    && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        }

        fun reconnectDatabase(): Boolean {
            try {
                pluginLogger.info("Database reload was requested. Attempting to reconnect...")
                if (connection.isRunning) connection.close()
                connection = DatabaseConnection(instance, instance.config)
            } catch (throwables: SQLException) {
                throwables.printStackTrace()
                return false
            }
            return connection.isRunning
        }

        // Managers

        val arenaManager = ArenaManager()

        val lobbyManager = LobbyManager()

        val guiManager = GUIManager()

        val kitManager = KitManager()

        val notificationManager = NotificationManager()

        val userCache: UserCache = UserCache()

        val gameBarManager = GameBarManager()

        val levelManager = LevelManager()

        val isDebugMode: Boolean = instance.config.getBoolean("general-settings.debug-mode")

    }

    private var cMap: CommandMap? = null

    override fun onLoad() {
        instance = this

        // register logger.
        pluginLogger = WarriorLogger(WarriorAPI.getService())
        saveDefaultConfig()

        try {
            translator = Translator(this, config)
            connection = DatabaseConnection(this, config)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun onEnable() {
        getCommand("debug")!!.setExecutor(DebugCommand())
        WarriorAPI.registerService(this, ApiProvider())

        pluginLogger.info("Registering Plugin Commands")
        registerCommands()

        registerTeams()

        pluginLogger.info("Registering Plugin Events")
        registerEvents()
        SpecialAbilities.registerAbility()

        pluginLogger.info("Initializing Managers")
        arenaManager.loadArenas()
        lobbyManager.loadData()
        guiManager.registerGUIs()
        kitManager.registerKits()
        notificationManager.start()

        if (usePlaceholderAPI()) {
            pluginLogger.info("Found PlaceholderAPI, registering Placeholders!")
            WarriorPlaceholders().register()
        }
    }

    override fun onDisable() {
        if (Bukkit.getScoreboardManager().mainScoreboard.getTeam(specTeam.name) != null) {
            specTeam.unregister()
        }

        pluginLogger.info("Attempting to save user data...")
        for (user in userCache.list) user.saveData()

        pluginLogger.info("Attempting to save arenas...")
        for (a in arenaManager.arenas) a.save()
    }

    private fun registerEvents() {
        Bukkit.getPluginManager().registerEvents(ArenaListener(), this)
        Bukkit.getPluginManager().registerEvents(ArenaSessionListener(), this)
        Bukkit.getPluginManager().registerEvents(GameStateListener(), this)
        Bukkit.getPluginManager().registerEvents(LobbySessionListener(), this)
        Bukkit.getPluginManager().registerEvents(PlayerListener(), this)
        Bukkit.getPluginManager().registerEvents(RegionExitListener(), this)
        Bukkit.getPluginManager().registerEvents(SessionChangeListener(), this)
        Bukkit.getPluginManager().registerEvents(ItemInteractListener(), this)
        Bukkit.getPluginManager().registerEvents(GameFlagListener(), this)
    }

    private fun registerCommands() {
        commandMap
        cmds.add(SettingsCommand())
        cmds.add(StatisticsCommand())
        cmds.add(
            WarriorCommand()
                .addSubCommand(WarriorHelpCommand())
                .addSubCommand(WarriorAboutCommand())
                .addSubCommand(WarriorLobbyCommand())
                .addSubCommand(WarriorReloadCommand())
        )
        cmds.add(
            ArenaCommand()
                .addSubCommand(ArenaCreateCommand())
                .addSubCommand(ArenaRemoveCommand())
                .addSubCommand(ArenaJoinCommand())
                .addSubCommand(ArenaSetupCommand())
                .addSubCommand(ArenaRateCommand())
                .addSubCommand(ArenaSpectateCommand())
                .addSubCommand(ArenaLeaveCommand())
                .addSubCommand(ArenaFlagsCommand())
        )
        cmds.add(SymbolCommand().addSubCommand(SymbolsSearchCommand()))
        cmds.add(
            KitCommand()
                .addSubCommand(KitCreateCommand())
        )

        cMap!!.registerAll(this.name.toLowerCase(), cmds)
    }

    private val commandMap: Unit
        get() {
            val f: Field
            try {
                f = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
                f.isAccessible = true
                cMap = f[Bukkit.getServer()] as CommandMap
            } catch (err: NoSuchFieldException) {
                println("Could not register commands!")
            } catch (err: SecurityException) {
                println("Could not register commands!")
            } catch (err: IllegalArgumentException) {
                println("Could not register commands!")
            } catch (err: IllegalAccessException) {
                println("Could not register commands!")
            }
        }

}