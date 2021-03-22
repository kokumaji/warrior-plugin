package com.dumbdogdiner.warrior.api.user.settings;

import com.dumbdogdiner.warrior.api.user.cosmetics.DeathParticle;
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathSound;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public class GameplaySettings {

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

    public GameplaySettings(UUID uuid) {
        this.userId = uuid;
    }

}
