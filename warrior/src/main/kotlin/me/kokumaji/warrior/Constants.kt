package me.kokumaji.warrior

import me.kokumaji.warrior.api.translation.ConsoleColor

class Constants {

    object LogMessages {

        const val TASK_FAILURE = "&cTask &4'{TaskIdent}' &cfailed with the following exception: &4{Error}&c.\n&cUse &4/warrior task {TaskIdent} restart &cto run this task again (Task has been disabled)"
        const val VERSION_WARN = "This Server seems to run an unsupported Version of Minecraft ${ConsoleColor.CYAN}({Version})${ConsoleColor.RESET}. Please consider using Version ${ConsoleColor.CYAN}{Recommended}${ConsoleColor.RESET} for an optimal experience."
        const val DEBUG_REGISTER_LISTENERS = "Registering all Listeners took {Time}${ConsoleColor.RESET}"
        const val PLATFORM_WARN = "This Server does not seem to run ${ConsoleColor.CYAN}{Recommended}${ConsoleColor.RESET}. Please note that running this Plugin on ${ConsoleColor.CYAN}{Platform}${ConsoleColor.RESET} may cause issues and is not supported."

        const val REGISTER_LISTENERS_NOTE = "Warrior will now attempt to register all Listeners. This may throw exceptions related to use of Reflection."
        const val REGISTER_LISTENERS_SUCCESS = "Successfully registered all Listeners!"
        const val REGISTER_LISTENERS_ERROR = "Could not register Listeners - Please check the following error for Details:"

        // const val DEFAULT_CONF_ERR = "Could not save plugin config. See error for details..."

        const val REGISTER_API = "Attempting to register WarriorAPI Module..."
        const val REGISTER_PAPI = "Registering PlaceholderAPI extension..."
        const val FOUND_PLUGIN = "Found Plugin {Plugin}."

    }

    object SupportedPlugins {

        const val PLACEHOLDERAPI = "PlaceholderAPI"
        const val VAULTAPI = "Vault"

    }

}