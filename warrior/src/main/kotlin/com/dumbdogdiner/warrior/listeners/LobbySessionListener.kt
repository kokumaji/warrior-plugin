package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.sessions.LobbySession
import com.dumbdogdiner.warrior.user.User
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class LobbySessionListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onDamage(e: EntityDamageEvent) {
        if (e.entity !is Player) return
        if ((e.entity as Player).gameMode == GameMode.CREATIVE) return

        val user: User = Warrior.userCache[e.entity.uniqueId]
        if (user.session !is LobbySession) return

        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onItemDrop(e: PlayerDropItemEvent) {
        if (e.player.gameMode == GameMode.CREATIVE) return

        val user: User = Warrior.userCache[e.player.uniqueId]
        if (user.session !is LobbySession) return

        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onItemSwap(e: PlayerSwapHandItemsEvent) {
        if (e.player.gameMode == GameMode.CREATIVE) return

        val user: User = Warrior.userCache[e.player.uniqueId]
        if (user.session !is LobbySession) return

        e.isCancelled = true
    }

    @EventHandler
    fun onItemPickup(e: EntityPickupItemEvent) {
        if (e.entity !is Player) return
        if ((e.entity as Player).gameMode == GameMode.CREATIVE) return

        val user: User = Warrior.userCache[e.entity.uniqueId]
        if (user.session !is LobbySession) return

        e.isCancelled = true
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        if (e.entity.gameMode == GameMode.CREATIVE) return

        val user: User = Warrior.userCache[e.entity.uniqueId]
        if (user.session !is LobbySession) return

        e.drops.clear()
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onItemClick(e: InventoryClickEvent) {
        if (e.whoClicked.gameMode == GameMode.CREATIVE) return

        val user: User = Warrior.userCache[e.whoClicked.uniqueId]
        if (user.session !is LobbySession) return

        e.isCancelled = true
        e.whoClicked.closeInventory()
    }
}