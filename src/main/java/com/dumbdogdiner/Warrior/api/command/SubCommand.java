package com.dumbdogdiner.Warrior.api.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    String getAlias();

    String getSyntax();

    String getPermission();

    boolean execute(CommandSender sender, String commandLabel, String[] args);

}
