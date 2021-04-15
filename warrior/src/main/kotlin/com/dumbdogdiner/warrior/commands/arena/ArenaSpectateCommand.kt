package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.sessions.GameState
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.Constants
import java.util.HashMap
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.ArrayList
import java.util.function.Function
import java.util.stream.Collectors

class ArenaSpectateCommand : SubCommand {
    override fun getAlias(): String {
        return "spectate"
    }

    override fun getSyntax(): String {
        return "/arena spectate <arena>"
    }

    override fun getPermission(): String {
        return "warrior.arena.spectate"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        val a: Arena? = Warrior.arenaManager[args[1]]
        val u: User = Warrior.userCache[(sender as Player).uniqueId]
        if (a == null) return false

        if (u.session is ArenaSession) {
            if ((u.session as ArenaSession?)!!.state == GameState.IN_GAME) return false
        }

        object : BukkitRunnable() {
            override fun run() {
                val msg: String =
                    Warrior.translator.translate(Constants.Lang.ARENA_SPECTATE, object : HashMap<String, String>() {
                        init {
                            put("arena", args[1])
                        }
                    }, u)
                sender.sendMessage(TranslationUtil.getPrefix() + msg)
                u.setSession(ArenaSession(u.uniqueId, a, GameState.SPECTATING))
                u.isSpectating = true
            }
        }.runTask(Warrior.instance)
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        var strings: List<String> = ArrayList()
        if (arguments.size == 2) strings = Warrior.arenaManager.arenas
            .map<String>(Function { obj: Arena -> obj.name })
            .collect<List<String>, Any>(Collectors.toList<String>())
        return strings
    }
}