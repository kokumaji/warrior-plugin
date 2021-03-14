package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.kit.effects.DeathSound;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        WarriorUser user = PlayerManager.get(((Player) sender).getUniqueId());
        user.unlockSound(DeathSound.WITHER_DEATH);

        return true;
    }

}
