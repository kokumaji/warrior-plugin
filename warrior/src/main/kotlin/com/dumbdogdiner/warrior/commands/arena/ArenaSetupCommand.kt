package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.arena.ArenaBuilderSession
import com.dumbdogdiner.warrior.api.arena.ArenaBuilder
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.translation.Constants
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList

class ArenaSetupCommand : SubCommand {
    override fun getAlias(): String {
        return "setup"
    }

    override fun getSyntax(): String {
        return "/arena setup <pos1|pos2|spawn|confirm|cancel>"
    }

    override fun getPermission(): String {
        return "warrior.arena.setup"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        val user = User((sender as Player))
        val session = ArenaBuilder.getSession(user)
        if (session == null) {
            val msg: String = Warrior.translator.translate(Constants.Lang.ARENA_NO_SETUP_RUNNING, user)
            user.bukkitPlayer.sendMessage(TranslationUtil.getPrefix() + msg)
            return true
        }

        when (args[1].toLowerCase()) {
            "cancel" -> session.endSession(ArenaBuilderSession.SessionResult.CANCEL)
            "pos1" -> ArenaBuilder.setPosition(user.bukkitPlayer.location, ArenaBuilderSession.PositionType.LOC1)
            "pos2" -> ArenaBuilder.setPosition(user.bukkitPlayer.location, ArenaBuilderSession.PositionType.LOC2)
            "spawn" -> ArenaBuilder.setPosition(user.bukkitPlayer.location, ArenaBuilderSession.PositionType.SPAWN)
            "confirm" -> session.endSession(ArenaBuilderSession.SessionResult.CONFIRM)
        }

        return true
    }

    override fun getArguments(sender: CommandSender, args: Array<String>): List<String> {
        var strings: List<String> = ArrayList()
        if (args.size == 2) {
            strings = listOf("cancel", "confirm", "pos1", "pos2", "spawn")
        }
        return strings
    }
}