package me.kokumaji.warrior

import me.kokumaji.warrior.api.WarriorAPI
import me.kokumaji.warrior.api.WarriorLogger
import me.kokumaji.warrior.api.kit.KitContainer
import me.kokumaji.warrior.user.UserCache
import org.bukkit.plugin.java.JavaPlugin

class WarriorProvider : WarriorAPI {

    override fun getInstance(): JavaPlugin {
        return WarriorPlugin.instance
    }

    override fun getLogger(): WarriorLogger {
        return WarriorPlugin.pluginLogger
    }

    override fun getUsers(): UserCache {
        return WarriorPlugin.instance.userCache
    }

    override fun getKitContainer(): KitContainer {
        return WarriorPlugin.instance.kitContainer
    }

    fun getPluginConfig(): WarriorConfig {
        return WarriorPlugin.instance.pluginConfig
    }

}