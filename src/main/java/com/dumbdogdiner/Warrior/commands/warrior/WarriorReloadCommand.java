package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.utils.DefaultMessages;
import org.bukkit.command.CommandSender;

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
        Warrior.getInstance().reloadConfig();
        sender.sendMessage(DefaultMessages.PLUGIN_RELOAD_SUCCESS);
        return true;
    }
}
