package com.dumbdogdiner.Warrior.api.kit.abilities;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.kit.Ability;

import org.bukkit.Sound;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PaceMakerAbility extends Ability {

    private final int duration = 30 * 20;

    private static final String[] desc = {
            "&7Grants &fSpeed 1 &7and &fJump Boost 1",
            "&7for 30 seconds"
    };

    public PaceMakerAbility(String name, int cost, int minStreak) {
        super(name, cost, minStreak, false, desc);
    }

    @Override
    public Runnable run(WarriorUser user) {
        return () -> {
            user.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, 1));
            user.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 1));

            user.playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5f, 1f);

            user.setAbilityActive(true);

            new BukkitRunnable() {

                @Override
                public void run() {
                    user.playSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
                    user.setAbilityActive(false);
                }

            }.runTaskLater(Warrior.getInstance(), duration);

            canExecute(user, false);
        };
    }

}
