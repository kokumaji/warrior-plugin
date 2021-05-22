package com.dumbdogdiner.warrior

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.api.translation.TimeUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.entity.Player

class WarriorPlaceholders : PlaceholderExpansion() {

    // TODO: This class is untested!

    override fun canRegister(): Boolean {
        return true
    }

    override fun getIdentifier(): String {
        return "warrior"
    }

    override fun getAuthor(): String {
        return WarriorPlugin.instance.description.authors[0]
    }

    override fun getVersion(): String {
        return WarriorPlugin.instance.description.version
    }

    override fun onPlaceholderRequest(p: Player, identifier: String): String {
        val args = identifier.split("_").toTypedArray()

        if (args.isEmpty()) return identifier
        if (args[0].equals("player", ignoreCase = true)) {
            val user: User = WarriorPlugin.instance.userCache[p.uniqueId] ?: return identifier
            when (args[1]) {
                "name" -> return user.name
                "timeplayed" -> {
                    val time = user.totalTime + (System.currentTimeMillis() - user.lastJoin)
                    return TimeUtil.formatDuration(time)
                }
                "firstjoin" -> return TimeUtil.formatDate(user.settings.language, user.firstJoin)
                "id", "userid", "uuid" -> return user.userId.toString()
                "ping" -> return user.ping.toString() + "ms"
                "title" -> return user.settings.title.title
                "deathsound" -> return user.gameplaySettings.activeSound.friendlyName()
                "kills" -> return user.kills.toString()
                "deaths" -> return user.deaths.toString()
                "kdr" -> return user.kdr.toString()
                "coins" -> return user.coins.toString()
                "arena" -> {
                    val session = user.session
                    return if (session !is ArenaSession) "(none)" else session.arena.name
                }
            }
        } else if (args[0].equals("arena", ignoreCase = true)) {
            var arena: Arena? = null
            var argument = ""
            val user: User = WarriorPlugin.instance.userCache[p.uniqueId]
                ?: return identifier

            // if no argument provided, get the arena at the players location
            if (args.size == 2) {
                for (arenaIt in WarriorPlugin.instance.arenaManager.arenas) {
                    if (arenaIt.bounds.contains(user.location)) {
                        arena = arenaIt
                        break
                    }
                }
                argument = args[1]
            } else if (args.size == 3) {
                arena = WarriorPlugin.instance.arenaManager[args[1]] ?: return identifier
                argument = args[2]
            }
            if (arena == null) return identifier
            when (argument) {
                "name" -> return arena.name
                "id" -> return arena.id.toString()
                "flags" -> return arena.flags.toString()
                "rating" -> return java.lang.String.valueOf(arena.metadata.averageRating())
                "creator" -> return arena.metadata.creator
                "timestamp" -> return TimeUtil.formatDate(user.settings.language, arena.metadata.createdAt)
                "description" -> return arena.metadata.desc.joinToString(separator = "\n")
            }
        }
        return identifier
    }
}