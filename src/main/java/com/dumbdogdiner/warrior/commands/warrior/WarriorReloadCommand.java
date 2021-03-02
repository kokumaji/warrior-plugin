package com.dumbdogdiner.warrior.commands.warrior;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.SubCommand;

import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.utils.DefaultMessages;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
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
        String msg = Warrior.getTranslator().translate(Constants.Lang.CMD_RELOAD_CONFIG);
        sender.sendMessage(TranslationUtil.getPrefix() + msg);
    }

    private void reloadLanguage(CommandSender sender) {
        File f = new File(Warrior.getInstance().getDataFolder(), "translation/messages.en_US.yml");
        Warrior.getTranslator().setLanguageFile(YamlConfiguration.loadConfiguration(f));
        DefaultMessages.update();

        String msg = Warrior.getTranslator().translate(Constants.Lang.CMD_RELOAD_LANGUAGE);
        sender.sendMessage(TranslationUtil.getPrefix() + msg);
    }


    public List<String> getArguments(CommandSender sender, String[] args) {
        List<String> strings = new ArrayList<>();

        if(args.length == 2) {
            strings = List.of("config", "language");
        }

        return strings;
    }
}
