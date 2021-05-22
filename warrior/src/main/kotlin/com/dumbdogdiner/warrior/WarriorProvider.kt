package com.dumbdogdiner.warrior

import com.dumbdogdiner.warrior.api.WarriorAPI
import com.dumbdogdiner.warrior.api.WarriorLogger
import com.dumbdogdiner.warrior.api.managers.WarriorArenaManager
import com.dumbdogdiner.warrior.api.managers.WarriorGameBarManager
import com.dumbdogdiner.warrior.api.managers.WarriorGUIManager
import com.dumbdogdiner.warrior.api.managers.WarriorKitManager
import com.dumbdogdiner.warrior.api.managers.WarriorLevelManager
import com.dumbdogdiner.warrior.api.managers.WarriorLobbyManager
import com.dumbdogdiner.warrior.api.managers.WarriorNotificationManager
import com.dumbdogdiner.warrior.api.user.WarriorUserCache
import com.dumbdogdiner.warrior.api.translation.Translator
import com.dumbdogdiner.warrior.user.User
import org.bukkit.plugin.java.JavaPlugin

class WarriorProvider : WarriorAPI {

    override fun getInstance(): JavaPlugin {
        return WarriorPlugin.instance
    }

    override fun getLogger(): WarriorLogger {
        return WarriorPlugin.pluginLogger
    }

    override fun getTranslator(): Translator {
        return WarriorPlugin.translator
    }

    override fun isDebugMode(): Boolean {
        return WarriorPlugin.instance.isDebugMode
    }

    override fun getArenaManager(): WarriorArenaManager {
        return WarriorPlugin.instance.arenaManager
    }

    override fun getGameBarManager(): WarriorGameBarManager {
        return WarriorPlugin.instance.gameBarManager
    }

    override fun getGUIManager(): WarriorGUIManager {
        return WarriorPlugin.instance.guiManager
    }

    override fun getKitManager(): WarriorKitManager {
        return WarriorPlugin.instance.kitManager
    }

    override fun getLevelManager(): WarriorLevelManager {
        return WarriorPlugin.instance.levelManager
    }

    override fun getLobbyManager(): WarriorLobbyManager {
        return WarriorPlugin.instance.lobbyManager
    }

    override fun getNotificationManager(): WarriorNotificationManager {
        return WarriorPlugin.instance.notificationManager
    }

    override fun getUserCache(): WarriorUserCache<User> {
        return WarriorPlugin.instance.userCache
    }
}