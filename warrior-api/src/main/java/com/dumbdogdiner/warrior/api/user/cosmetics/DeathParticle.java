package com.dumbdogdiner.warrior.api.user.cosmetics;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Particle;

public enum DeathParticle {

    HEART(Particle.HEART, 0x00000001, "Hearts"),
    THUNDER(Particle.VILLAGER_ANGRY, 0x00000001, "Thunder");

    @Getter Particle particle;
    @Getter int unlockValue;

    @Accessors(fluent = true)
    @Getter String friendlyName;

    DeathParticle(Particle particle, int i, String name) {
        this.particle = particle;
        this.unlockValue = i;
        this.friendlyName = name;
    }

    public static boolean hasUnlocked(WarriorUser user, DeathParticle particle) {
        return (user.getTitles() & particle.getUnlockValue()) == particle.getUnlockValue();
    }

    public static boolean canUnlock(DeathParticle particle) {
        return particle.getUnlockValue() != 0;
    }

    public static DeathParticle fromString(String name) {
        for(DeathParticle particle : DeathParticle.values()) {
            if(particle.name().equalsIgnoreCase(name)) return particle;
        }

        return HEART;
    }
}
