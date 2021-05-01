package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.util.DefaultMessages
import com.dumbdogdiner.warrior.api.sessions.LobbySession
import com.dumbdogdiner.warrior.api.events.UserLevelupEvent
import com.dumbdogdiner.warrior.effects.WarriorEffects
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player
        val uuid = p.uniqueId
        if (p.hasPermission("warrior.command.admin") && !Warrior.connection.isRunning) {
            val msg = TranslationUtil.translateColor(DefaultMessages.SQL_ERROR_NOTICE)
            p.sendMessage(" \n$msg\n ")
        }
        if (!Warrior.userCache.contains(uuid)) {
            val user = Warrior.userCache.addUser(uuid)
            user.loadData()
            user.session = LobbySession(user.userId)

            p.teleport(Warrior.lobbyManager.lobbySpawn)
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = 20.0
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onQuit(e: PlayerQuitEvent) {
        Warrior.userCache.remove(e.player.uniqueId)
    }

    @EventHandler
    fun onLevelUp(e: UserLevelupEvent) {
        if (e.level % 5 == 0) e.user.spawnEffect(WarriorEffects.LEVELUP)
    }
}