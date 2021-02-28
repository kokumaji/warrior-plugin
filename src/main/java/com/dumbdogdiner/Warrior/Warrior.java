package com.dumbdogdiner.Warrior;

import com.dumbdogdiner.Warrior.api.WarriorLogger;
import com.dumbdogdiner.Warrior.api.kit.SpecialAbilities;

import com.dumbdogdiner.Warrior.commands.DebugCommand;
import com.dumbdogdiner.Warrior.commands.arena.*;
import com.dumbdogdiner.Warrior.commands.kit.KitCommand;
import com.dumbdogdiner.Warrior.commands.kit.KitCreateCommand;
import com.dumbdogdiner.Warrior.commands.warrior.*;
import com.dumbdogdiner.Warrior.listeners.*;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.managers.GUIManager;
import com.dumbdogdiner.Warrior.managers.KitManager;
import com.dumbdogdiner.Warrior.managers.LobbyManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import com.dumbdogdiner.Warrior.api.translation.Translator;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Warrior extends JavaPlugin {

    public final String COMMAND_HEADER = "&8" + TranslationUtil.HL(15) + " &8[ &3&l" + getName() + " &8] " + TranslationUtil.HL(15);

    private static final List<Command> cmds = new ArrayList<Command>();
    private CommandMap cMap;

    @Getter
    private static Translator translator;

    @Getter
    private static Team specTeam;

    private static WarriorLogger logger;

    @Override
    public void onLoad() {
        logger = new WarriorLogger(this);
        saveDefaultConfig();
        try {
            translator = new Translator(this, getConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getCommand("debug").setExecutor(new DebugCommand());
        this.getCommandMap();

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
                .addSubCommand(new ArenaLeaveCommand()));
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

    }

    @Override
    public void onDisable() {
        if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam(specTeam.getName()) != null) {
            specTeam.unregister();
        }
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
