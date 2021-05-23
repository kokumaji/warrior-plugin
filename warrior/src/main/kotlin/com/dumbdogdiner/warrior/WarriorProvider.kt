package com.dumbdogdiner.warrior

import com.dumbdogdiner.warrior.api.WarriorAPI
import com.dumbdogdiner.warrior.api.WarriorLogger
import com.dumbdogdiner.warrior.api.kit.KitContainer
import com.dumbdogdiner.warrior.user.UserCache
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

}