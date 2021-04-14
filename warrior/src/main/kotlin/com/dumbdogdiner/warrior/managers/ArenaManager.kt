package com.dumbdogdiner.warrior.managers

import com.dumbdogdiner.warrior.WithWarriorPlugin
import com.dumbdogdiner.warrior.api.managers.WarriorArenaManager
import java.util.HashMap
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.api.translation.Placeholders
import com.dumbdogdiner.warrior.util.DefaultMessages
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.sessions.SessionType
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.user.User
import com.dumbdogdiner.warrior.util.JSONUtil
import org.bukkit.entity.Player
import java.io.File
import java.util.ArrayList

class ArenaManager : WithWarriorPlugin, WarriorArenaManager {

    private val arenaMap = HashMap<Int, Arena>()
    override fun registerArena(arena: Arena) {
        arenaMap[arenaMap.size] = arena
    }

    override fun getArenas(): List<Arena> {
        return ArrayList(arenaMap.values)
    }

    override fun get(arenaName: String): Arena? {
        for (a in arenaMap.values) {
            if (a.name.equals(arenaName, ignoreCase = true)) return a
        }
        return null
    }

    override fun loadArenas() {
        val dataFolder = File(JSONUtil.ARENA_DATA_PATH)
        val files = dataFolder.listFiles() ?: return

        var i = 0
        for (f in files) {
            registerArena(Arena(f, i))
            i++
        }

        val msg = Placeholders.applyPlaceholders(DefaultMessages.LOADED_OBJECT, object : HashMap<String, String>() {
            init {
                put("amount", i.toString())
                put("type", "arena")
            }
        })
        getLogger().info(msg)
    }

    fun getPlayers(arena: Arena?): MutableList<User> {
        return Warrior.userCache.getListOf { user: User ->
            val session = user.session!!

            if(session is ArenaSession && session.type == SessionType.GAME)
                return@getListOf session.arena == arena

            false
        }.toMutableList()
    }

    override fun isPlaying(player: Player): Boolean {
        val user = Warrior.userCache[player.uniqueId]
        return if (user.session == null) false else user.session!!.type == SessionType.GAME
    }

    override fun getSession(player: Player): ArenaSession? {
        if (isPlaying(player)) {
            val user = Warrior.userCache[player.uniqueId]
            return user.session as ArenaSession
        }
        return null
    }

    override fun remove(a: Arena) {
        arenaMap.remove(a.id)
        JSONUtil.removeFile(a)
    }
}