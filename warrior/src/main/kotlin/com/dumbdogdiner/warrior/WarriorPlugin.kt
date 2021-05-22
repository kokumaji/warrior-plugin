package com.dumbdogdiner.warrior

import kr.entree.spigradle.annotations.PluginMain
import com.dumbdogdiner.warrior.api.WarriorAPI
import com.dumbdogdiner.warrior.api.WarriorLogger
import com.dumbdogdiner.warrior.user.UserCache

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class WarriorPlugin : JavaPlugin() {

    companion object {

        lateinit var pluginLogger: WarriorLogger
        lateinit var instance: WarriorPlugin

    }

    val userCache = UserCache()

    override fun onLoad() {
        instance = this
        pluginLogger = WarriorLogger(WarriorProvider())
    }

    override fun onEnable() {
        pluginLogger.info(WarriorDefaults.LogMessages.REGISTER_API)
        WarriorAPI.registerService(this, WarriorProvider())

        if(isInstalled("PlaceholderAPI")) {
            pluginLogger.info(WarriorDefaults.LogMessages.FOUND_PLUGIN, mapOf("Plugin" to "PlaceholderAPI"))
            pluginLogger.info(WarriorDefaults.LogMessages.REGISTER_PAPI)
            WarriorPlaceholders().register()
        }

        if(isInstalled("Vault")) {
            TODO("Need to add VaultAPI integration")
        }
    }

    override fun onDisable() {

    }

    // Internal Utility Methods

    private fun isInstalled(pluginName: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName)
    }

}