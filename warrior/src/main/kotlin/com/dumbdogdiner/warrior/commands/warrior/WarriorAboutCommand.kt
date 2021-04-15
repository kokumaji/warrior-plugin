package com.dumbdogdiner.warrior.commands.warrior

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.Placeholders
import java.util.HashMap
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.ArrayList

class WarriorAboutCommand : SubCommand {
    private val about = arrayOf(
        " ",
        "&3&l{PluginName} &7developed by &b{Authors}",
        "&7Running version &b{Version}",
        " ",
        "&7SQL Status &8» {SqlStatus}",
        " "
    )

    override fun getAlias(): String {
        return "about"
    }

    override fun getSyntax(): String {
        return "/warrior about"
    }

    override fun getPermission(): String {
        return "warrior.command.admin"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (args.size > 1) return false
        val self: Plugin = Warrior.instance
        val message =
            Placeholders.applyPlaceholders(java.lang.String.join("\n", *about), object : HashMap<String?, String?>() {
                init {
                    put("Description", self.description.description)
                    put("PluginName", self.name)
                    put("Authors", TranslationUtil.stringifyList(self.description.authors).replace("and", "&7and&b"))
                    put("Version", self.description.version)
                    put("SqlStatus", Warrior.connection.status)
                }
            })
        if (sender is Player) sender.sendMessage(TranslationUtil.prettyMessage(message)) else for (s in message.split("\n")
            .toTypedArray()) Warrior.pluginLogger.info(s)
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return ArrayList()
    }
}