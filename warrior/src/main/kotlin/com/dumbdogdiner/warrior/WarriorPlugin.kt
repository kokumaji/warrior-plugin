package com.dumbdogdiner.warrior

import com.dumbdogdiner.stickyapi.bukkit.util.StartupUtil
import kr.entree.spigradle.annotations.PluginMain
import com.dumbdogdiner.warrior.api.WarriorAPI
import com.dumbdogdiner.warrior.api.WarriorLogger
import com.dumbdogdiner.warrior.commands.DebugCommand
import com.dumbdogdiner.warrior.kits.KitContainer
import com.dumbdogdiner.warrior.listeners.AbilityListener
import com.dumbdogdiner.warrior.user.UserCache

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class WarriorPlugin : JavaPlugin() {

    companion object {

        lateinit var pluginLogger: WarriorLogger
        lateinit var instance: WarriorPlugin

    }

    lateinit var kitContainer: KitContainer
    lateinit var userCache: UserCache

    override fun onLoad() {

        WarriorAPI.registerService(this, WarriorProvider())

        instance = this
        pluginLogger = WarriorLogger(WarriorProvider())

        pluginLogger.info(WarriorDefaults.LogMessages.REGISTER_API)

        if(!StartupUtil.setupConfig(this)) {
            pluginLogger.error(WarriorDefaults.LogMessages.DEFAULT_CONF_ERR)
        }
    }

    override fun onEnable() {

        kitContainer = KitContainer()
        userCache = UserCache(this)

        // Bukkit.getPluginManager().registerEvents(UserCacheListener(), this)
        Bukkit.getPluginManager().registerEvents(AbilityListener(), this)

        if(isInstalled("PlaceholderAPI")) {
            pluginLogger.info(WarriorDefaults.LogMessages.FOUND_PLUGIN, mapOf("Plugin" to "PlaceholderAPI"))
            pluginLogger.info(WarriorDefaults.LogMessages.REGISTER_PAPI)
            // WarriorPlaceholders().register()
        }

        if(isInstalled("Vault")) {
            TODO("Need to add VaultAPI integration")
        }

        kitContainer.registerDefaults()
        userCache.registerHandlers()

        getCommand("debug")?.setExecutor(DebugCommand())

    }

    override fun onDisable() {

    }

    // Internal Utility Methods

    private fun isInstalled(pluginName: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName)
    }

}