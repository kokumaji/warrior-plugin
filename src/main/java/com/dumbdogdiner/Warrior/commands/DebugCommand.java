package com.dumbdogdiner.Warrior.commands;

import com.dumbdogdiner.Warrior.utils.DefaultMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //sender.sendMessage(DefaultMessages.PLUGIN_RELOAD_SUCCESS);
        return true;
    }
}
