package com.dumbdogdiner.Warrior.api.command;

import org.bukkit.command.CommandSender;
import java.util.List;

public interface SubCommand {

    String getAlias();

    String getSyntax();

    String getPermission();

    boolean execute(CommandSender sender, String commandLabel, String[] args);

    List<String> getArguments(CommandSender sender, String[] arguments);

}
