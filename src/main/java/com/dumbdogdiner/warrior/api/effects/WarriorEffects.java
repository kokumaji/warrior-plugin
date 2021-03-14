package com.dumbdogdiner.warrior.api.effects;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.sound.Sounds;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class WarriorEffects {

    public static final Consumer<WarriorUser> CONFETTI = (user) -> {
        user.getBukkitPlayer().spawnParticle(Particle.TOTEM, user.getLocation(), 100, 0.25, 0.25, 0.25, 0.85);
        user.getBukkitPlayer().spawnParticle(Particle.FIREWORKS_SPARK, user.getLocation(), 100, 3, 3, 3, 0);

        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, user, 6L);
        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, user, 12L);
        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, user, 15L);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

}
