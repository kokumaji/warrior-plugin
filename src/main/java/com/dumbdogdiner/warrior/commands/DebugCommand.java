package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.api.translation.Placeholders;
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
        String msg;
        if(sender instanceof Player) {
            msg = Placeholders.parseConditional(String.join(" ", args), ((Player) sender).getPlayer());
        } else msg = Placeholders.parseConditional(String.join(" ", args));
        sender.sendMessage(msg);
        return true;
    }

}
