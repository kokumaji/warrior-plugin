package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarriorHelpCommand implements SubCommand {

    private final String[] commands = {
            Warrior.getInstance().COMMAND_HEADER,
            " ",
            "&b/warrior help &8- &7Displays this help message",
            " ",
            "&b/warrior about &8- &7Displays plugin version and authors",
            " ",
            "&b/warrior reload &8- &7Reloads the plugin config",
            " ",
            "&8" + TranslationUtil.HL
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
        TranslationUtil.centerMessage((Player) sender, commands);
        return true;
    }
}
