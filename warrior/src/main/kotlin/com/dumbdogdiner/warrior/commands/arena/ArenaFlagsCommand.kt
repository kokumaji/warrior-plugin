package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import org.bukkit.command.CommandSender
import java.util.ArrayList

class ArenaFlagsCommand : SubCommand {

    override fun getAlias(): String {
        return "flags"
    }

    override fun getSyntax(): String {
        return "/arena flags <arena>"
    }

    override fun getPermission(): String {
        return "warrior.flags.list"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        val a: Arena = Warrior.arenaManager[args[1]] ?: return false
        val container = a.flags
        val msg = String.format(
            "%s §7Flags for arena §b%2s§7:\n(%3s)",
            TranslationUtil.getPrefix(), a.name, container.toString()
        )
        sender.sendMessage(msg)
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return ArrayList()
    }
}