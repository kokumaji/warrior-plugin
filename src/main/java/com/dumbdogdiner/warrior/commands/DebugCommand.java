package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.api.user.WarriorUser;

import com.dumbdogdiner.warrior.gui.ParticleTrailGUI;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player) {
            WarriorUser user = PlayerManager.get(((Player) sender).getUniqueId());
            user.addExperience(125);
        }
        return true;
    }

}
