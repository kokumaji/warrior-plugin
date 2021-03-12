package com.dumbdogdiner.warrior;

import com.dumbdogdiner.warrior.api.WarriorLogger;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities;
import com.dumbdogdiner.warrior.api.translation.Translator;
import com.dumbdogdiner.warrior.commands.DebugCommand;
import com.dumbdogdiner.warrior.commands.arena.*;
import com.dumbdogdiner.warrior.commands.kit.KitCommand;
import com.dumbdogdiner.warrior.commands.kit.KitCreateCommand;
import com.dumbdogdiner.warrior.commands.misc.StatisticsCommand;
import com.dumbdogdiner.warrior.commands.warrior.*;
import com.dumbdogdiner.warrior.listeners.*;
import com.dumbdogdiner.warrior.managers.*;
import com.dumbdogdiner.warrior.utils.DatabaseConnection;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
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

    public final String COMMAND_HEADER = "&8" + TranslationUtil.HL(15) + " &8[ &3&l" + getName() + " &8] " + TranslationUtil.HL(15);

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
        logger = new WarriorLogger(this);
        saveDefaultConfig();
        try {
            translator = new Translator(this, getConfig());
            connection = new DatabaseConnection(this, getConfig());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getCommand("debug").setExecutor(new DebugCommand());
        this.getCommandMap();

        cmds.add(new StatisticsCommand("stats", this));
        cmds.add(new WarriorCommand("warrior", this)
                .addSubCommand(new WarriorHelpCommand())
                .addSubCommand(new WarriorAboutCommand())
                .addSubCommand(new WarriorLobbyCommand())
                .addSubCommand(new WarriorReloadCommand()));
        cmds.add(new ArenaCommand("arena", this)
                .addSubCommand(new ArenaCreateCommand())
                .addSubCommand(new ArenaRemoveCommand() )
                .addSubCommand(new ArenaJoinCommand())
                .addSubCommand(new ArenaSetupCommand())
                .addSubCommand(new ArenaSpectateCommand())
                .addSubCommand(new ArenaLeaveCommand())
                .addSubCommand(new ArenaFlagsCommand()));
        cmds.add(new KitCommand("kit")
                .addSubCommand(new KitCreateCommand()));
        cMap.registerAll(this.getName().toLowerCase(), cmds);

        ArenaManager.loadArenas();
        registerTeams();
        registerEvents();

        SpecialAbilities.registerAbility();

        LobbyManager.loadData();
        GUIManager.registerGUIs();
        KitManager.registerKits();

        if(usePlaceholderAPI()) {
            new WarriorPlaceholders().register();
        }

    }

    @Override
    public void onDisable() {
        if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam(specTeam.getName()) != null) {
            specTeam.unregister();
        }

        for(WarriorUser user : PlayerManager.getList()) user.saveData();

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

}
