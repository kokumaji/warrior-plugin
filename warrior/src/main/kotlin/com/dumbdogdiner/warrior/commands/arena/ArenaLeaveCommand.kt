package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.sessions.SessionType
import com.dumbdogdiner.warrior.Warrior
import java.util.HashMap
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.api.sessions.LobbySession
import com.dumbdogdiner.warrior.api.translation.Constants
import com.dumbdogdiner.warrior.user.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ArenaLeaveCommand : SubCommand {
    override fun getAlias(): String {
        return "leave"
    }

    override fun getSyntax(): String {
        return "/arena leave"
    }

    override fun getPermission(): String {
        return "warrior.arena.leave"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        val user: User = Warrior.userCache[(sender as Player).uniqueId]
        if (user.session!!.type == SessionType.GAME) {
            val msg: String = Warrior.translator.translate(Constants.Lang.ARENA_LEFT, object : HashMap<String, String>() {
                init {
                    put("arena", args[1])
                }
            }, user)
            user.sendMessage(TranslationUtil.getPrefix() + msg)
            user.setSession(LobbySession(user.uniqueId))
            if (user.isSpectating) user.isSpectating = false
        }
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return listOf()
    }
}