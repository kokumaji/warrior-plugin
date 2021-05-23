package com.dumbdogdiner.warrior

import com.dumbdogdiner.stickyapi.bukkit.util.StartupUtil
import kr.entree.spigradle.annotations.PluginMain
import com.dumbdogdiner.warrior.api.WarriorAPI
import com.dumbdogdiner.warrior.api.WarriorLogger
import com.dumbdogdiner.warrior.debug.DebugCommand
import com.dumbdogdiner.warrior.kits.KitContainer
import com.dumbdogdiner.warrior.user.UserCache

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class WarriorPlugin : JavaPlugin() {

    companion object {

        lateinit var pluginLogger: WarriorLogger
        lateinit var instance: WarriorPlugin

    }

    val kitContainer: KitContainer = KitContainer()
    val userCache = UserCache()

    override fun onLoad() {
        instance = this
        pluginLogger = WarriorLogger(WarriorProvider())
        if(!StartupUtil.setupConfig(this)) {
            error(WarriorDefaults.LogMessages.DEFAULT_CONF_ERR)
        }
    }

    override fun onEnable() {
        pluginLogger.info(WarriorDefaults.LogMessages.REGISTER_API)
        WarriorAPI.registerService(this, WarriorProvider())

        if(isInstalled("PlaceholderAPI")) {
            pluginLogger.info(WarriorDefaults.LogMessages.FOUND_PLUGIN, mapOf("Plugin" to "PlaceholderAPI"))
            pluginLogger.info(WarriorDefaults.LogMessages.REGISTER_PAPI)
            // WarriorPlaceholders().register()
        }

        if(isInstalled("Vault")) {
            TODO("Need to add VaultAPI integration")
        }

        kitContainer.registerDefaults()
        getCommand("debug")?.setExecutor(DebugCommand())

    }

    override fun onDisable() {

    }

    // Internal Utility Methods

    private fun isInstalled(pluginName: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName)
    }

}