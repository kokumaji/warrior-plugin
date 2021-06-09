package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.WithWarriorPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class UserCacheListener : Listener, WithWarriorPlugin {

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player
        val cache = getPlugin().userCache

        val msg = String.format("Adding User %s(%2s) to Cache.", player.name, player.uniqueId)
        getLogger().debug(msg)

        if (!cache.contains(player.uniqueId)) {
            cache.addUser(player.uniqueId)
        } else {
            val msg = String.format("User %s(%2s) is already cached!", player.name, player.uniqueId)
            getLogger().warn(msg)
        }
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        val player = e.player
        val cache = getPlugin().userCache

        val msg = String.format("Removing User %s(%2s) from Cache.", player.name, player.uniqueId)
        getLogger().debug(msg)

        if (!cache.contains(player.uniqueId)) {
            cache.remove(player.uniqueId)
        } else {
            val msg = String.format("User %s(%2s) is not cached!", player.name, player.uniqueId)
            getLogger().warn(msg)
        }
    }

}