package com.dumbdogdiner.warrior.api.user.settings;

import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;
import com.dumbdogdiner.warrior.api.user.cosmetics.WarriorTitle;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public class GeneralSettings {

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

    @Getter @Setter
    private LanguageCode language;

    @Getter
    private final UUID userId;

    public GeneralSettings(UUID uuid) {
        this.userId = uuid;
    }

}
