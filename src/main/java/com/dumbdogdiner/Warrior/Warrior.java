package com.dumbdogdiner.Warrior;

import com.dumbdogdiner.Warrior.api.command.CommandType;
import com.dumbdogdiner.Warrior.commands.DebugCommand;
import com.dumbdogdiner.Warrior.commands.arena.*;
import com.dumbdogdiner.Warrior.commands.warrior.*;
import com.dumbdogdiner.Warrior.listeners.GameStateListener;
import com.dumbdogdiner.Warrior.listeners.PlayerListener;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
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

    private static List<Command> cmds = new ArrayList<Command>();
    private CommandMap cMap;

    @Getter
    private static Translator translator;

    public static Warrior getInstance() {
        return getPlugin(Warrior.class);
    }

    @Getter
    private static Team specTeam;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        try {
            translator = new Translator(this, getConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        System.out.println("Warrior loaded successfully!");
        getCommand("debug").setExecutor(new DebugCommand());
        this.getCommandMap();

        cmds.add(new WarriorCommand("warrior", this)
                .addSubCommand(new WarriorHelpCommand())
                .addSubCommand(new WarriorAboutCommand())
                .addSubCommand(new WarriorReloadCommand()));
        cmds.add(new ArenaCommand("arena", this)
                .addSubCommand(new ArenaCreateCommand())
                .addSubCommand(new ArenaJoinCommand())
                .addSubCommand(new ArenaSetupCommand())
                .addSubCommand(new ArenaSpectateCommand())
                .addSubCommand(new ArenaLeaveCommand()));
        cMap.registerAll(this.getName().toLowerCase(), cmds);

        ArenaManager.loadArenas();
        registerTeams();

        if(getConfig().getBoolean("arena-settings.prevent-region-exit")) {
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        }

        Bukkit.getPluginManager().registerEvents(new GameStateListener(), this);

    }

    private static void registerTeams() {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = sb.getTeam("spectating");
        if(team == null) {
            team = sb.registerNewTeam("spectating");
            team.setPrefix("§c§lSPEC §7");
            team.setAllowFriendlyFire(false);
            team.setCanSeeFriendlyInvisibles(true);
        }

        specTeam = team;
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
