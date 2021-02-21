package com.dumbdogdiner.Warrior;

import com.dumbdogdiner.Warrior.commands.DebugCommand;
import com.dumbdogdiner.Warrior.commands.arena.*;
import com.dumbdogdiner.Warrior.commands.warrior.*;
import com.dumbdogdiner.Warrior.listeners.PlayerListener;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import com.dumbdogdiner.Warrior.api.translation.Translator;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import org.bukkit.plugin.java.JavaPlugin;

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
                .addSubCommand(new ArenaLeaveCommand()));
        cMap.registerAll(this.getName().toLowerCase(), cmds);

        ArenaManager.loadArenas();

        if(getConfig().getBoolean("arena-settings.prevent-region-exit")) {
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        }

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
