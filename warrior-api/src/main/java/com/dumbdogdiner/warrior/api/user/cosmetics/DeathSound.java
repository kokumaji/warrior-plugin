package com.dumbdogdiner.warrior.api.user.cosmetics;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

public enum DeathSound {

    DEFAULT (Sound.ENTITY_PLAYER_DEATH, 0x00000000, "Default"),
    WATERSPLASH (Sound.ENTITY_FISHING_BOBBER_SPLASH , 0x00000001, "Watersplash"),
    GHAST_DEATH (Sound.ENTITY_GHAST_DEATH           , 0x00000002, "Ghast"),
    THUNDERSTORM(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0x00000004, "Thunder"),
    CAT_MEOW    (Sound.ENTITY_CAT_AMBIENT           , 0x00000008, "Meow!"),
    DOG_BARK    (Sound.ENTITY_WOLF_AMBIENT          , 0x00000010, "Bark!"),
    FOX_SLEEP   (Sound.ENTITY_FOX_SLEEP             , 0x00000020, "Sleepy Fox"),
    WITHER_DEATH(Sound.ENTITY_WITHER_HURT           , 0x00000040, "Wither"),
    VILLAGER    (Sound.ENTITY_VILLAGER_AMBIENT      , 0x00000080, "Villager");

    @Getter private final Sound sound;
    @Getter private final int unlockValue;

    @Getter @Accessors(fluent = true)
    private final String friendlyName;

    DeathSound(Sound sound, int i, String s) {
        this.sound = sound;
        this.unlockValue = i;
        this.friendlyName = s;
    }

    public static DeathSound fromString(String name) {
        for(DeathSound sound : DeathSound.values()) {
            if(sound.name().equalsIgnoreCase(name)) return sound;
        }

        return WATERSPLASH;
    }
}
