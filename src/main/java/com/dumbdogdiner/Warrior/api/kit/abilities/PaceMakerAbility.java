package com.dumbdogdiner.Warrior.api.kit.abilities;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.kit.Ability;

import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PaceMakerAbility implements Ability {

    @Override
    public String getName() {
        return "Pacemaker";
    }

    @Override
    public String getDescription() {
        return "Grants Speed 1 and Jump Boost 1 for 30 seconds.";
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getStreakMinimum() {
        return 3;
    }

    @Override
    public Runnable run(WarriorUser user) {
        return () -> {
            user.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30 * 20, 1));
            user.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30 * 20, 1));

            user.playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 1f);

            canExecute(user, false);
        };
    }

    @Override
    public boolean canExecute(WarriorUser user, boolean value) {
        if(value) {
            ((ArenaSession)user.getSession()).canUseAbility(true);
            ItemStack item = user.getBukkitPlayer().getInventory().getItem(8);
            item.setType(Material.MAGMA_CREAM);

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ACTIVE_ABILITY_STRING);
            item.setItemMeta(meta);

            user.getBukkitPlayer().getInventory().setItem(8, item);

        } else {
            ((ArenaSession)user.getSession()).canUseAbility(false);
            ItemStack item = user.getBukkitPlayer().getInventory().getItem(8);
            item.setType(Material.FIREWORK_STAR);

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(DEACTIVATED_ABILITY_STRING);
            item.setItemMeta(meta);

            user.getBukkitPlayer().getInventory().setItem(8, item);
        }
        return true;
    }

    @Override
    public boolean availableOnStart() {
        return false;
    }
}
