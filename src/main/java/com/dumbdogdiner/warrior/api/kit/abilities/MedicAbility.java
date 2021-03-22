package com.dumbdogdiner.warrior.api.kit.abilities;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.kit.Ability;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MedicAbility extends Ability {

    private static final String[] desc = {
            "&7Fully &frestores your health &7and",
            "&7removes all status effects."
    };

    public MedicAbility(String name, int cost, int minStreak) {
        super(name, cost, minStreak, true, desc);
    }


    @Override
    public Runnable run(WarriorUser user) {
        Player p = user.getBukkitPlayer();

        return () -> {
            for(PotionEffect potion : p.getActivePotionEffects()) {
                p.removePotionEffect(potion.getType());
            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
        };
    }
}
