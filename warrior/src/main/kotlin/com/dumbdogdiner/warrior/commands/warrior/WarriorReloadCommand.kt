package com.dumbdogdiner.warrior.commands.warrior

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.stickyapi.bukkit.util.PlayerSelector
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.util.DefaultMessages
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.Constants
import com.dumbdogdiner.warrior.user.UserCache
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.util.ArrayList

class WarriorReloadCommand : SubCommand {
    override fun getAlias(): String {
        return "reload"
    }

    override fun getSyntax(): String {
        return "/warrior reload"
    }

    override fun getPermission(): String {
        return "warrior.command.reload"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (args.size == 1) {
            reloadConfig(sender)
        } else {
            if (args[1].equals("language", ignoreCase = true)) {
                reloadLanguage(sender)
            } else if (args[1].equals("config", ignoreCase = true)) {
                reloadConfig(sender)
            } else if (args[1].equals("database", ignoreCase = true)) {
                val admins = PlayerSelector.selectPlayers { p: Player -> p.hasPermission("warrior.command.admin") }
                TranslationUtil.sendToMultiple(
                    admins,
                    TranslationUtil.translateColor(TranslationUtil.getPrefix() + DefaultMessages.SQL_CONNECT_ATTEMPT),
                    true
                )
                val success = Warrior.reconnectDatabase()
                if (success) {
                    TranslationUtil.sendToMultiple(
                        admins,
                        TranslationUtil.translateColor(TranslationUtil.getPrefix() + DefaultMessages.SQL_CONNECT_SUCCESS),
                        true
                    )
                    for (user in Warrior.userCache.list) {
                        user.loadData()
                    }
                }
                if (!success && sender is Player) {
                    TranslationUtil.sendToMultiple(
                        admins,
                        TranslationUtil.translateColor(TranslationUtil.getPrefix() + DefaultMessages.SQL_CONNECT_FAILURE)
                    )
                }
            }
        }
        return true
    }

    private fun reloadConfig(sender: CommandSender) {
        Warrior.instance.reloadConfig()
        val msg: String = Warrior.translator.translate(Constants.Lang.CMD_RELOAD_CONFIG)
        sender.sendMessage(TranslationUtil.getPrefix() + msg)
    }

    private fun reloadLanguage(sender: CommandSender) {
        val f: File = File(Warrior.instance.dataFolder, "translation/messages.en_US.yml")
        Warrior.translator.englishFile = YamlConfiguration.loadConfiguration(f)
        val f2: File = File(Warrior.instance.dataFolder, "translation/messages.de_DE.yml")
        Warrior.translator.germanFile = YamlConfiguration.loadConfiguration(f2)
        DefaultMessages.update()
        val msg: String = Warrior.translator.translate(Constants.Lang.CMD_RELOAD_LANGUAGE)
        sender.sendMessage(TranslationUtil.getPrefix() + msg)
    }

    override fun getArguments(sender: CommandSender, args: Array<String>): List<String> {
        var strings: List<String> = ArrayList()
        if (args.size == 2) {
            strings = listOf("config", "language", "database")
        }
        return strings
    }
}