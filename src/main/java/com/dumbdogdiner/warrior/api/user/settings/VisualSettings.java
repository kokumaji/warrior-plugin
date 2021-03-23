package com.dumbdogdiner.warrior.api.user.settings;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class VisualSettings {

    @Getter
    private UUID userId;

    /**
     * 0 = off, 1 = enabled (2 = easter egg)
     */
    @Getter @Setter
    private int goreLevel;

    /**
     * 0 = disabled, 1 = reduced, 2 = full
     */
    @Getter @Setter
    private int particleMode;

    public VisualSettings(UUID uuid) {
        this.userId = uuid;
    }

}
