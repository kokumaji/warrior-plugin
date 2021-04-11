package com.dumbdogdiner.warrior;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.WarriorLogger;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities;
import com.dumbdogdiner.warrior.api.translation.Translator;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.commands.DebugCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaCreateCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaFlagsCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaJoinCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaLeaveCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaRateCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaRemoveCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaSetupCommand;
import com.dumbdogdiner.warrior.commands.arena.ArenaSpectateCommand;
import com.dumbdogdiner.warrior.commands.kit.KitCommand;
import com.dumbdogdiner.warrior.commands.kit.KitCreateCommand;
import com.dumbdogdiner.warrior.commands.misc.SettingsCommand;
import com.dumbdogdiner.warrior.commands.misc.StatisticsCommand;
import com.dumbdogdiner.warrior.commands.misc.SymbolCommand;
import com.dumbdogdiner.warrior.commands.misc.SymbolsSearchCommand;
import com.dumbdogdiner.warrior.commands.warrior.WarriorAboutCommand;
import com.dumbdogdiner.warrior.commands.warrior.WarriorCommand;
import com.dumbdogdiner.warrior.commands.warrior.WarriorHelpCommand;
import com.dumbdogdiner.warrior.commands.warrior.WarriorLobbyCommand;
import com.dumbdogdiner.warrior.commands.warrior.WarriorReloadCommand;
import com.dumbdogdiner.warrior.listeners.ArenaListener;
import com.dumbdogdiner.warrior.listeners.ArenaSessionListener;
import com.dumbdogdiner.warrior.listeners.GameFlagListener;
import com.dumbdogdiner.warrior.listeners.GameStateListener;
import com.dumbdogdiner.warrior.listeners.ItemInteractListener;
import com.dumbdogdiner.warrior.listeners.LobbySessionListener;
import com.dumbdogdiner.warrior.listeners.PlayerListener;
import com.dumbdogdiner.warrior.listeners.RegionExitListener;
import com.dumbdogdiner.warrior.listeners.SessionChangeListener;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.GameBarManager;
import com.dumbdogdiner.warrior.managers.KitManager;
import com.dumbdogdiner.warrior.managers.LevelManager;
import com.dumbdogdiner.warrior.managers.LobbyManager;
import com.dumbdogdiner.warrior.managers.NotificationManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.util.DatabaseConnection;
import kr.entree.spigradle.annotations.PluginMain;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@PluginMain
public class Warrior extends JavaPlugin {
    private static final List<Command> cmds = new ArrayList<Command>();
    private CommandMap cMap;

    @Getter
    private static Translator translator;

    @Getter
    private static DatabaseConnection connection;

    @Getter
    private static Team specTeam;

    private static WarriorLogger logger;

    @Override
    public void onLoad() {
        WarriorAPI.registerService(this, new ApiProvider());
        // register logger.
        logger = new WarriorLogger(WarriorAPI.getService());
        saveDefaultConfig();
        try {
            translator = new Translator(this, getConfig());
            connection = new DatabaseConnection(this, getConfig());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // managers
    @Getter
    private final ArenaManager arenaManager = new ArenaManager();
    @Getter
    private final LobbyManager lobbyManager = new LobbyManager();
    @Getter
    private final GUIManager guiManager = new GUIManager();
    @Getter
    private final KitManager kitManager = new KitManager();
    @Getter
    private final NotificationManager notificationManager = new NotificationManager();
    @Getter
    private final PlayerManager playerManager = new PlayerManager();
    @Getter
    private final GameBarManager gameBarManager = new GameBarManager();
    @Getter
    private final LevelManager levelManager = new LevelManager();

    @Override
    public void onEnable() {
        getCommand("debug").setExecutor(new DebugCommand());
        this.getCommandMap();

        cmds.add(new SettingsCommand());
        cmds.add(new StatisticsCommand("stats", this));
        cmds.add(new WarriorCommand("warrior", this)
                .addSubCommand(new WarriorHelpCommand())
                .addSubCommand(new WarriorAboutCommand())
                .addSubCommand(new WarriorLobbyCommand())
                .addSubCommand(new WarriorReloadCommand()));
        cmds.add(new ArenaCommand("arena", this)
                .addSubCommand(new ArenaCreateCommand())
                .addSubCommand(new ArenaRemoveCommand())
                .addSubCommand(new ArenaJoinCommand())
                .addSubCommand(new ArenaSetupCommand())
                .addSubCommand(new ArenaRateCommand())
                .addSubCommand(new ArenaSpectateCommand())
                .addSubCommand(new ArenaLeaveCommand())
                .addSubCommand(new ArenaFlagsCommand()));
        cmds.add(new SymbolCommand().addSubCommand(new SymbolsSearchCommand()));
        cmds.add(new KitCommand("kit")
                .addSubCommand(new KitCreateCommand()));
        cMap.registerAll(this.getName().toLowerCase(), cmds);

        arenaManager.loadArenas();
        registerTeams();
        registerEvents();

        SpecialAbilities.registerAbility();

        lobbyManager.loadData();
        guiManager.registerGUIs();
        kitManager.registerKits();

        notificationManager.start();

        if(usePlaceholderAPI()) {
            new WarriorPlaceholders().register();
        }

    }

    @Override
    public void onDisable() {
        if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam(specTeam.getName()) != null) {
            specTeam.unregister();
        }

        for(User user : playerManager.getList()) user.saveData();
        for(Arena a : arenaManager.getArenas()) a.save();

    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new ArenaListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArenaSessionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameStateListener(), this);
        Bukkit.getPluginManager().registerEvents(new LobbySessionListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new RegionExitListener(), this);
        Bukkit.getPluginManager().registerEvents(new SessionChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameFlagListener(), this);
    }

    private static void registerTeams() {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = sb.getTeam("warrior_spec");
        if(team == null) {
            team = sb.registerNewTeam("warrior_spec");
            team.setPrefix("§c§lSPEC §7");
            team.setAllowFriendlyFire(false);
            team.setCanSeeFriendlyInvisibles(true);
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM);
        }

        specTeam = team;
    }

    public static boolean usePlaceholderAPI() {
        return
                Warrior.getInstance().getConfig().getBoolean("general-settings.use-placeholderapi")
                && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public static Warrior getInstance() {
        return getPlugin(Warrior.class);
    }

    public static WarriorLogger getPluginLogger() {
        return logger;
    }

    protected void getCommandMap() {
        Field field;
        try {
            field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            cMap = (CommandMap) field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException err) {
            System.out.println("Could not register commands!");
        }
    }

    public static boolean reconnectDatabase() {
        try {
            if(connection != null && connection.isRunning()) connection.close();
            connection = new DatabaseConnection(Warrior.getInstance(), Warrior.getInstance().getConfig());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return connection.isRunning();
    }

}
