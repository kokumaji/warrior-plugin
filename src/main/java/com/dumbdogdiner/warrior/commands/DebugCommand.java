package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.api.translation.Placeholders;
import com.dumbdogdiner.warrior.api.translation.Symbols;
import com.dumbdogdiner.warrior.api.user.WarriorUser;

import com.dumbdogdiner.warrior.managers.LevelManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage(Symbols.parseSymbols(String.join(" ", args)));
        return true;
    }

}
