package com.dumbdogdiner.Warrior;

import com.dumbdogdiner.Warrior.commands.warrior.WarriorAboutCommand;
import com.dumbdogdiner.Warrior.commands.warrior.WarriorCommand;
import com.dumbdogdiner.Warrior.commands.warrior.WarriorHelpCommand;
import com.dumbdogdiner.Warrior.commands.warrior.WarriorReloadCommand;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import com.dumbdogdiner.Warrior.utils.Translator;

import lombok.Getter;
import lombok.SneakyThrows;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Warrior extends JavaPlugin {

    public final String COMMAND_HEADER = "&8" + TranslationUtil.HL(15) + " &8[ &3&l" + getName() + " &8] " + TranslationUtil.HL(15);

    private static List<Command> cmds = new ArrayList<Command>();
    private CommandMap cMap;

    @Getter
    private Translator translator;

    public static Warrior getInstance() {
        return getPlugin(Warrior.class);
    }

    @SneakyThrows
    @Override
    public void onLoad() {
        saveDefaultConfig();
        this.translator = new Translator(this, getConfig());
    }

    @Override
    public void onEnable() {
        System.out.println("Warrior loaded successfully!");
        this.getCommandMap();

        cmds.add(new WarriorCommand("warrior", this)
                .addSubCommand(new WarriorHelpCommand())
                .addSubCommand(new WarriorAboutCommand())
                .addSubCommand(new WarriorReloadCommand()));
        cMap.registerAll(this.getName().toLowerCase(), cmds);
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
