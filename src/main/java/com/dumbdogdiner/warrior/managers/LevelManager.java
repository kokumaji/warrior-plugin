package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.api.user.WarriorUser;

public class LevelManager {

    public static final int MIN_XP = 5;
    public static final int MAX_XP = 32;

    public static final double BASE_XP = 128.0;

    public static int xpToLevel(int xp) {
        return (int) (1 + Math.sqrt((xp / BASE_XP)));
    }

    public static int levelToXp(int level) {
        if(level > 100) throw new IllegalStateException("Can't go past level 100");
        return (int) Math.floor(BASE_XP * Math.pow((double) level - 1, 2));
    }

    public static float getProgress(WarriorUser user) {
        int currentLevel = user.getLevel();
        double currentXp = user.getExperience() - levelToXp(user.getLevel());

        double nextXp = levelToXp(currentLevel + 1) - levelToXp(currentLevel);

        return (float) (currentXp / nextXp);
    }

}
