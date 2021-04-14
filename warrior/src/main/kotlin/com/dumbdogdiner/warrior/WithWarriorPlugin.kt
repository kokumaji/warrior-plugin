package com.dumbdogdiner.warrior

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.WarriorAPI
import com.dumbdogdiner.warrior.api.WarriorLogger
import java.util.logging.Logger

/**
 * Utility interface for quickly accessing plugin fields.
 */
interface WithWarriorPlugin {
    /**
     * @return The [Warrior] plugin instance.
     */
    fun getPlugin(): Warrior {
        return Warrior.instance
    }

    /**
     * @return The plugin [Logger] provided by Bukkit.
     */
    fun getLogger(): WarriorLogger {
        return Warrior.pluginLogger
    }
}