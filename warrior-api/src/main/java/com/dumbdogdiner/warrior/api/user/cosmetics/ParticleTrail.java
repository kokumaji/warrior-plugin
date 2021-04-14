package com.dumbdogdiner.warrior.api.user.cosmetics;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.util.HeadTexture;
import lombok.Getter;
import org.bukkit.Location;

import java.util.function.BiConsumer;

public enum ParticleTrail {

    /**
     * LGBT TRAILS
     */

    RAINBOW_FLAG("Rainbow Flag", (user, loc) -> {

    }, 0x00000001, HeadTexture.RAINBOW_FLAG),

    LESBIAN("Lesbian", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.LESBIAN),

    BISEXUAL("Bisexual", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.BISEXUAL),

    TRANSGENDER("Transgender", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.TRANSGENDER),

    POLYSEXUAL("Polysexual", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.POLYSEXUAL),

    PANSEXUAL("Pansexual", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.PANSEXUAL),

    ASEXUAL("Asexual", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.ASEXUAL),

    AROMANTIC("Aromantic", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.AROMANTIC),

    AGENDER("Agender", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.AGENDER),

    NON_BINARY("Non-Binary", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.NON_BINARY),

    BIGENDER("Bigender", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.BIGENDER),

    GENDER_QUEER("Gender Queer", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.GENDER_QUEER),

    GENDERFLUID("Genderfluid", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.GENDER_FLUID),

    DEMIGIRL("Demigirl", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.DEMIGIRL),

    DEMIBOY("Demiboy", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.DEMIBOY),

    DEMIGENDER("Demigender", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.DEMIGENDER),

    DEMIANDROGYNE("Demiandrogyne", (user, loc) -> {

    }, RAINBOW_FLAG, HeadTexture.DEMIANDROGYNE);

    @Getter String name;
    @Getter BiConsumer<WarriorUser, Location> consumer;

    @Getter int unlockValue;
    @Getter HeadTexture icon;

    @Getter ParticleTrail parent;

    ParticleTrail(String name, BiConsumer<WarriorUser, Location> consumer, int unlockValue, HeadTexture icon) {
        this.name = name;
        this.consumer = consumer;
        this.unlockValue = unlockValue;
        this.icon = icon;
    }

    ParticleTrail(String name, BiConsumer<WarriorUser, Location> consumer, ParticleTrail parent, HeadTexture icon) {
        this.name = name;
        this.consumer = consumer;
        this.parent = parent;
        this.icon = icon;
    }

}
