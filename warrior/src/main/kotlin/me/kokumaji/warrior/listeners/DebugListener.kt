package me.kokumaji.warrior.listeners

import me.kokumaji.HibiscusAPI.HibiscusPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class DebugListener(plugin: HibiscusPlugin): Listener {

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        System.out.println("${e.player.name} joined the server!")
    }
}