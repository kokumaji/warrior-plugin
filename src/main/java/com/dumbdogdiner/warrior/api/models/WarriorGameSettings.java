package com.dumbdogdiner.warrior.api.models;

import com.dumbdogdiner.warrior.api.kit.effects.DeathParticle;
import com.dumbdogdiner.warrior.api.kit.effects.DeathSound;
import com.dumbdogdiner.warrior.api.kit.effects.WarriorTitle;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public class WarriorGameSettings {

    @Getter
    private final UUID userId;

    @Accessors(fluent = true)
    @Getter @Setter boolean showKills;

    @Accessors(fluent = true)
    @Getter @Setter boolean showTimer;

    @Getter @Setter
    DeathParticle activeParticle;

    @Getter @Setter
    DeathSound activeSound;

    public WarriorGameSettings(UUID uuid) {
        this.userId = uuid;
    }

}
