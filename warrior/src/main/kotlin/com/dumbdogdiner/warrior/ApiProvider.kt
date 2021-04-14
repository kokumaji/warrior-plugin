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

class ApiProvider : WarriorAPI {

    override fun getInstance(): JavaPlugin {
        return Warrior.instance
    }

    override fun getLogger(): WarriorLogger {
        return Warrior.pluginLogger
    }

    override fun getTranslator(): Translator {
        return Warrior.translator
    }

    override fun isDebugMode(): Boolean {
        return Warrior.instance.isDebugMode
    }

    override fun getArenaManager(): WarriorArenaManager {
        return Warrior.instance.arenaManager
    }

    override fun getGameBarManager(): WarriorGameBarManager {
        return Warrior.instance.gameBarManager
    }

    override fun getGUIManager(): WarriorGUIManager {
        return Warrior.instance.guiManager
    }

    override fun getKitManager(): WarriorKitManager {
        return Warrior.instance.kitManager
    }

    override fun getLevelManager(): WarriorLevelManager {
        return Warrior.instance.levelManager
    }

    override fun getLobbyManager(): WarriorLobbyManager {
        return Warrior.instance.lobbyManager
    }

    override fun getNotificationManager(): WarriorNotificationManager {
        return Warrior.instance.notificationManager
    }

    override fun getUserCache(): WarriorUserCache<User> {
        return Warrior.instance.userCache
    }
}