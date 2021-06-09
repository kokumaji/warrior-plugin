package me.kokumaji.warrior

import kr.entree.spigradle.annotations.PluginMain
import me.kokumaji.warrior.api.WarriorAPI
import me.kokumaji.warrior.api.WarriorLogger
import me.kokumaji.warrior.debug.DebugCommand
import me.kokumaji.warrior.kits.KitContainer
import me.kokumaji.warrior.user.UserCache
import me.kokumaji.HibiscusAPI.HibiscusPlugin
import me.kokumaji.HibiscusAPI.HibiscusProvider
import me.kokumaji.HibiscusAPI.analytics.*
import me.kokumaji.warrior.threads.FooBarThread
import me.kokumaji.warrior.threads.SqlUpdateThread

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class WarriorPlugin : JavaPlugin(), HibiscusPlugin {

    companion object {

        lateinit var pluginLogger: WarriorLogger
        lateinit var instance: WarriorPlugin

    }

    val kitContainer: KitContainer = KitContainer()
    val pluginConfig: WarriorConfig = WarriorConfig(this)
    private lateinit var hibiscusProvider: HibiscusProvider
    val userCache = UserCache()

    override fun onLoad() {
        instance = this
        hibiscusProvider = this.register()
        pluginLogger = WarriorLogger(WarriorProvider())

        this.saveDefaultConfig()

    }

    override fun onEnable() {
        pluginLogger.info(Constants.LogMessages.REGISTER_API)
        WarriorAPI.registerService(this, WarriorProvider())

        if (!isRecommendedPlatform && !pluginConfig.ignoreWarns) {
            pluginLogger.info(
                Constants.LogMessages.PLATFORM_WARN,
            mapOf(
                "Platform" to serverPlatform.name,
                "Recommended" to recommendedPlatform.name
            ))
        }

        SqlUpdateThread(this)
        FooBarThread(this)

        if (!isSupportedVersion && !pluginConfig.ignoreWarns) {
           pluginLogger.info(
               Constants.LogMessages.VERSION_WARN,
               mapOf(
                   "Version" to Version.getBukkitVersion().toString(),
                   "Recommended" to recommendedVersion.toString()
               )
           )
        }

        userCache.registerHandlers()

        if(isInstalled(Constants.SupportedPlugins.PLACEHOLDERAPI)) {
            pluginLogger.info(Constants.LogMessages.FOUND_PLUGIN, mapOf("Plugin" to "PlaceholderAPI"))
            pluginLogger.info(Constants.LogMessages.REGISTER_PAPI)
            // WarriorPlaceholders().register()
        }

        if(isInstalled(Constants.SupportedPlugins.VAULTAPI)) {
            TODO("Need to add VaultAPI integration")
        }

        kitContainer.registerDefaults()
        getCommand("debug")?.setExecutor(DebugCommand())

        if(pluginConfig.debugMode) {
            pluginLogger.info(Constants.LogMessages.REGISTER_LISTENERS_NOTE)
            pluginLogger.debug(Constants.LogMessages.DEBUG_REGISTER_LISTENERS) { registerListeners() }
        } else {
            registerListeners()
        }


    }

    private fun registerListeners() {
        try {
            registerAllListeners()
        } catch (thr: Throwable) {
            pluginLogger.error(Constants.LogMessages.REGISTER_LISTENERS_ERROR)
            thr.printStackTrace()
            pluginLoader.disablePlugin(this)
        }

        pluginLogger.info(Constants.LogMessages.REGISTER_LISTENERS_SUCCESS)

    }

    override fun onDisable() {
        this.unregister()
    }

    private fun isInstalled(pluginName: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName)
    }

    override fun getProvider(): HibiscusProvider {
        return hibiscusProvider
    }

    override fun enableVerbose(): Boolean {
        return true
    }

    override fun asBukkit(): Plugin {
        return this
    }

    override fun getRecommendedPlatform(): ServerPlatform {
        return ServerPlatform.PAPERMC
    }

    override fun getMaxVersion(): Version {
        return MinecraftUpdate.NETHER_UPDATE.version
    }

    override fun getRecommendedVersion(): Version {
        return MinecraftUpdate.NETHER_UPDATE.version
    }

}