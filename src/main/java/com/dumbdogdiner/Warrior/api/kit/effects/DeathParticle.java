package com.dumbdogdiner.Warrior.api.kit.effects;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Particle;

public enum DeathParticle {

    HEARTS(Particle.HEART, 0x00000001, "In Love");

    @Getter
    private final Particle particle;
    @Getter private final int unlockValue;

    @Getter @Accessors(fluent = true)
    private final String friendlyName;

    DeathParticle(Particle particle, int i, String s) {
        this.particle = particle;
        this.unlockValue = i;
        this.friendlyName = s;
    }

}
