package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.SubCommand;

import com.dumbdogdiner.Warrior.api.translation.Constants;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WarriorReloadCommand implements SubCommand {

    @Override
    public String getAlias() {
        return "reload";
    }

    @Override
    public String getSyntax() {
        return "/warrior reload";
    }

    @Override
    public String getPermission() {
        return "warrior.command.reload";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length == 1) {
            reloadConfig(sender);
        } else {
            if(args[1].equalsIgnoreCase("language")) {
                reloadLanguage(sender);
            } else if(args[1].equalsIgnoreCase("config")) {
                reloadConfig(sender);
            }
        }

        return true;
    }

    private void reloadConfig(CommandSender sender) {
        Warrior.getInstance().reloadConfig();
        String msg = Warrior.getTranslator().translate(Constants.Lang.CMD_RELOAD_CONFIG, true);
        sender.sendMessage(msg);
    }

    private void reloadLanguage(CommandSender sender) {
        File f = new File(Warrior.getInstance().getDataFolder(), "translation/messages.en_US.yml");
        Warrior.getTranslator().setLanguageFile(YamlConfiguration.loadConfiguration(f));

        String msg = Warrior.getTranslator().translate(Constants.Lang.CMD_RELOAD_LANGUAGE, true);
        sender.sendMessage(msg);
    }


    public List<String> getArguments(CommandSender sender, String[] args) {
        List<String> strings = new ArrayList<>();

        if(args.length == 2) {
            strings = List.of("config", "language");
        }

        return strings;
    }
}
