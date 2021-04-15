package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.api.arena.ArenaBuilderSession
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.managers.ArenaManager
import com.dumbdogdiner.warrior.Warrior
import java.util.HashMap
import com.dumbdogdiner.warrior.api.arena.ArenaBuilder
import com.dumbdogdiner.warrior.api.translation.Constants
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList

class ArenaCreateCommand : SubCommand {
    override fun getAlias(): String {
        return "create"
    }

    override fun getSyntax(): String {
        return "/arena create <Name>"
    }

    override fun getPermission(): String {
        return "warrior.arena.create"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (args.size < 2 || args[1].length > 18) {
            return false
        }
        val player = sender as Player
        val user: User = Warrior.userCache[player.uniqueId]
        val name = TranslationUtil.capitalize(args[1])
        val session = ArenaBuilderSession(User(player), name)
        val arena: Arena? = Warrior.arenaManager[args[1]]
        if (arena != null) {
            val msg: String =
                Warrior.translator.translate(Constants.Lang.ARENA_ALREADY_EXISTS, object : HashMap<String, String>() {
                    init {
                        put("arena", arena.name)
                    }
                }, user)
            player.sendMessage(TranslationUtil.getPrefix() + msg)
            player.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 0.5f, 1f)
            return true
        }
        ArenaBuilder.registerSession(player.world, session)
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return ArrayList()
    }
}