package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.Warrior
import java.util.HashMap
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.translation.Constants
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.ArrayList
import java.util.function.Function
import java.util.stream.Collectors

class ArenaJoinCommand : SubCommand {
    override fun getAlias(): String {
        return "join"
    }

    override fun getSyntax(): String {
        return "/arena join <Arena>"
    }

    override fun getPermission(): String {
        return "warrior.arena.join"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (args.size < 2 || args[1].length > 18) {
            return false
        }
        val user: User = Warrior.userCache[(sender as Player).uniqueId]
        val a: Arena? = Warrior.arenaManager[args[1]]
        if (a == null) {
            val msg: String =
                Warrior.translator.translate(Constants.Lang.ARENA_DOESNT_EXIST, object : HashMap<String, String>() {
                    init {
                        put("arena", args[1])
                    }
                }, user)
            user.sendMessage(TranslationUtil.getPrefix() + msg)
            user.playSound(Sound.ENTITY_ITEM_BREAK, 0.5f, 1f)
            return true
        }
        object : BukkitRunnable() {
            override fun run() {
                user.setSession(ArenaSession(user.uniqueId, a))
                val msg: String = Warrior.translator.translate(Constants.Lang.ARENA_TELEPORT, object : HashMap<String?, String?>() {
                        init {
                            put("arena", a.name)
                        }
                    }, user)
                user.sendMessage(TranslationUtil.getPrefix() + msg)
                user.playSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1f)
            }
        }.runTask(Warrior.instance)
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        var strings: List<String> = ArrayList()
        if (arguments.size == 2) strings =
            Warrior.arenaManager.arenas.stream().map<String>(Function { obj: Arena -> obj.name })
                .collect<List<String>, Any>(Collectors.toList<String>())
        return strings
    }
}