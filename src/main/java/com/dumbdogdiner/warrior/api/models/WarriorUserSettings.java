package com.dumbdogdiner.warrior.api.models;

import com.dumbdogdiner.warrior.api.kit.effects.WarriorTitle;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public class WarriorUserSettings {

    @Accessors(fluent = true)
    @Getter @Setter
    private boolean canFly;

    @Getter @Setter
    private int playerVisibility;

    @Accessors(fluent = true)
    @Getter @Setter
    private boolean receiveNotifications;

    @Getter @Setter
    private int privacyLevel;

    @Getter @Setter
    private WarriorTitle title;

    @Getter @Setter
    private long lastReset;

    @Getter
    private final UUID userId;

    public WarriorUserSettings(UUID uuid) {
        this.userId = uuid;
    }

}
