package com.dumbdogdiner.warrior.commands.warrior

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList

class WarriorHelpCommand : SubCommand {
    private val commands = arrayOf(
        " ",
        "&b/warrior help &8- &7Displays this help message",
        " ",
        "&b/warrior about &8- &7Displays plugin version and authors",
        " ",
        "&b/warrior reload &8- &7Reloads the plugin config",
        " "
    )

    override fun getAlias(): String {
        return "help"
    }

    override fun getSyntax(): String {
        return "/warrior help"
    }

    override fun getPermission(): String {
        return "warrior.command.admin"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (args.size > 1) return false
        if (sender is Player) sender.sendMessage(TranslationUtil.prettyMessage(*commands)) else for (s in commands) {
            if (s == " ") continue
            sender.sendMessage(TranslationUtil.translateColor(s))
        }
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return ArrayList()
    }
}