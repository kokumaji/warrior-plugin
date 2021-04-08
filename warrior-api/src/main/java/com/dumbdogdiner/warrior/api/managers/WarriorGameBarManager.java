package com.dumbdogdiner.warrior.api.managers;

import com.dumbdogdiner.warrior.api.builders.GameBossBar;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import org.bukkit.boss.BarColor;

/**
 * Manages game bars.
 * TODO: Javadoc
 */
public interface WarriorGameBarManager {
    /**
     * Add a player to the target boss bar.
     * @param user The target player
     * @param bossBar The boss bar to add them to
     */
    void addPlayer(WarriorUser user, GameBossBar bossBar);

    /**
     * Remove the target player from the manager.
     * @param user
     */
    void removePlayer(WarriorUser user);

    /**
     * Get the target player's active boss bar.
     * @param user The target player
     * @return A {@link GameBossBar} if the player is attached to one.
     */
    GameBossBar getBossBar(WarriorUser user);

    /**
     * Update the boss bar title for the target player.
     * @param user The target player
     * @param title The new title
     */
    void updateTitle(WarriorUser user, String title);

    /**
     * Update the boss bar color for the target player.
     * @param user The target player
     * @param color The new color
     */
    void updateColor(WarriorUser user, BarColor color);

    /**
     * Update the boss bar progress for the target player.
     * @param user The target player
     * @param val The new value
     */
    void updateProgress(WarriorUser user, double val);
}
