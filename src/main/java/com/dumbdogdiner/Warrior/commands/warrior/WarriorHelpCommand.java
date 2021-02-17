package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class WarriorHelpCommand implements SubCommand {

    private final String[] commands = {
            " ",
            "&b/warrior help &8- &7Displays this help message",
            " ",
            "&b/warrior about &8- &7Displays plugin version and authors",
            " ",
            "&b/warrior reload &8- &7Reloads the plugin config",
            " ",
    };

    @Override
    public String getAlias() {
        return "help";
    }

    @Override
    public String getSyntax() {
        return "/warrior help";
    }

    @Override
    public String getPermission() {
        return "warrior.command.admin";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length > 1) return false;
        sender.sendMessage(TranslationUtil.prettyMessage(commands));
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }
}
