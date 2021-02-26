package com.dumbdogdiner.Warrior.commands;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.kit.effects.DeathSound;
import com.dumbdogdiner.Warrior.api.kit.effects.DeathSounds;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.managers.PlayerManager;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;


public class DebugCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());
        user.unlockSound(DeathSound.THUNDERSTORM);
        user.unlockSound(DeathSound.WITHER_DEATH);

        List<DeathSound> sounds = DeathSounds.getDeathSounds(user);
        System.out.println(Arrays.toString(sounds.stream().map(DeathSound::name).toArray(String[]::new)));
        user.setDeathSound(sounds.get(sounds.size() - 1));

        user.getBukkitPlayer().getInventory().addItem(new ItemStack(Material.MUSHROOM_STEW, 16));
        return true;
    }

}
