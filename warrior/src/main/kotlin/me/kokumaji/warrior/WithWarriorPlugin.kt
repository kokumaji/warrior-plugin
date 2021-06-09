package me.kokumaji.warrior

import me.kokumaji.warrior.api.WarriorLogger
import java.util.logging.Logger

/**
 * Utility interface for quickly accessing plugin fields.
 */
interface WithWarriorPlugin {
    /**
     * @return The [WarriorPlugin] plugin instance.
     */
    fun getPlugin(): WarriorPlugin {
        return WarriorPlugin.instance
    }

    /**
     * @return The plugin [Logger] provided by Bukkit.
     */
    fun getLogger(): WarriorLogger {
        return WarriorPlugin.pluginLogger
    }

    fun getConfig(): WarriorConfig {
        return WarriorPlugin.instance.pluginConfig
    }

}