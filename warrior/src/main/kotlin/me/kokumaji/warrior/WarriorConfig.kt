package me.kokumaji.warrior

class WarriorConfig(plugin: WarriorPlugin) {

    /**
     * Setting DEBUG_MODE to true enables access to advanced debugging features
     * and detailed logging.
     */
    var debugMode: Boolean = true

    /**
     * Setting IGNORE_WARNS to true disables all Plugin related Warnings, including
     * version mismatch and use of non-recommended server software.
     */
    var ignoreWarns: Boolean = false

    init {
        val config = plugin.config
        debugMode = config.getBoolean("general-settings.debug-mode")
        ignoreWarns = config.getBoolean("general-settings.ignore-warns")
    }

}
