package com.dumbdogdiner.warrior.api.user.cosmetics;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import lombok.Getter;

public enum WarriorTitle {

    ROOKIE("Rookie", 0x00000001), // Level 0
    LVL_5("Level 5", 0x00000002), // Level 5
    LVL_10("Level 10", 0x00000004),
    LVL_20("Level 20", 0x00000008),
    LVL_30("Level 30", 0x00000010),
    LVL_50("Level 50", 0x00000020),
    LVL_75("Level 75", 0x00000040),
    LVL_100("Level 100", 0x00000080),

    // EMPTY = title disabled
    PLUGIN_DEV("&3&lPlugin Developer &r", 0),
    EMPTY("", 0);

    @Getter private final String title;
    @Getter private final int unlockValue;

    WarriorTitle(String s, int i) {
        this.title = s;
        this.unlockValue = i;
    }

    public static boolean hasUnlocked(WarriorUser user, WarriorTitle title) {
        return (user.getTitles() & title.getUnlockValue()) == title.getUnlockValue();
    }

    public static boolean canUnlock(WarriorTitle title) {
        return title.getUnlockValue() != 0;
    }

    public static WarriorTitle fromString(String title) {
        for(WarriorTitle wt : WarriorTitle.values()) {
            if(wt.name().equalsIgnoreCase(title)) return wt;
        }

        return EMPTY;
    }
}
