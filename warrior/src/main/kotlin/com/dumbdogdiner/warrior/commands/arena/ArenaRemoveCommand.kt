package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.Constants
import java.util.HashMap
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ArenaRemoveCommand : SubCommand {
    override fun getAlias(): String {
        return "remove"
    }

    override fun getSyntax(): String {
        return "/arena remove <Arena>"
    }

    override fun getPermission(): String {
        return "warrior.arena.remove"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (args.size != 2) return false
        val a: Arena? = Warrior.arenaManager[args[1]]
        if (a != null) {
            val user: User = Warrior.userCache[(sender as Player).uniqueId]
            val msg: String =
                Warrior.translator.translate(Constants.Lang.ARENA_REMOVE, object : HashMap<String, String>() {
                    init {
                        put("arena", args[1])
                    }
                }, user)
            sender.sendMessage(TranslationUtil.getPrefix() + msg)
            Warrior.arenaManager.remove(a)
        }
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return listOf()
    }
}