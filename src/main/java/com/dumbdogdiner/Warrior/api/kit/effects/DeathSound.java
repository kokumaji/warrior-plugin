package com.dumbdogdiner.Warrior.api.kit.effects;

import lombok.Getter;
import org.bukkit.Sound;

public enum DeathSound {

    WATERSPLASH (Sound.ENTITY_FISHING_BOBBER_SPLASH , 0x00000001),
    GHAST_DEATH (Sound.ENTITY_GHAST_DEATH           , 0x00000002),
    THUNDERSTORM(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0x00000004);

    @Getter private Sound sound;
    @Getter private int unlockValue;

    DeathSound(Sound sound, int i) {
        this.sound = sound;
        this.unlockValue = i;
    }

}
