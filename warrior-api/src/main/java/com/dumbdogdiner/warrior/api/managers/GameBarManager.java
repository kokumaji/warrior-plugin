package com.dumbdogdiner.warrior.api.managers;

import com.dumbdogdiner.warrior.api.builders.GameBossBar;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import org.bukkit.boss.BarColor;

/**
 * Manages game bars.
 * TODO: Javadoc
 */
public interface GameBarManager {
    void addPlayer(WarriorUser user, GameBossBar bossBar);

    void removePlayer(WarriorUser user);

    GameBossBar getBossBar(WarriorUser user);

    void updateTitle(WarriorUser user, String title);

    void updateColor(WarriorUser user, BarColor color);

    void updateProgress(WarriorUser user, double val);
}
