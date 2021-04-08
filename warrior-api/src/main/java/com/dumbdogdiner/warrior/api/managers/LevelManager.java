package com.dumbdogdiner.warrior.api.managers;

import com.dumbdogdiner.warrior.api.user.WarriorUser;

/**
 * Manages XP and levels.
 */
public interface LevelManager {
    /**
     * Convert an amount of xp to level.
     * @param xp The xp to convert
     * @return The level this amount of xp represents.
     */
    int xpToLevel(int xp);

    /**
     * Convert a level to the xp required to obtain it.
     * @param level The level to convert
     * @return The amount of xp required to obtain it.
     */
    int levelToXp(int level);


    /**
     * Fetch the level progress of the target user.
     * @param user The target user
     * @return The user's xp level.
     */
    double getProgress(WarriorUser user);
}
