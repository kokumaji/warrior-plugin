package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.arena.Arena
import com.dumbdogdiner.warrior.api.arena.gameflags.FlagContainer
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.BlockBreakFlag
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.BlockPlaceFlag
import com.dumbdogdiner.warrior.managers.ArenaManager
import com.dumbdogdiner.warrior.user.User
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerItemDamageEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class GameFlagListener : Listener {
    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val user: User = Warrior.userCache[e.player.uniqueId]
        if (user.session !is ArenaSession) return

        val arena = (user.session as ArenaSession?)!!.arena
        val container = arena.flags

        val breakFlag = container.getFlag(BlockBreakFlag::class.java) ?: return
        e.isCancelled = !breakFlag.value
    }

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val user: User = Warrior.userCache[e.player.uniqueId]
        if (user.session !is ArenaSession) return

        val arena = (user.session as ArenaSession?)!!.arena
        val container = arena.flags

        val breakFlag = container.getFlag(BlockPlaceFlag::class.java) ?: return
        e.isCancelled = !breakFlag.value
    }

    @EventHandler
    fun onItemDrop(e: PlayerDropItemEvent) {
        val p = e.player
        if (Warrior.arenaManager.isPlaying(p)) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onItemPickup(e: EntityPickupItemEvent) {
        if (e.entity !is Player) return
        val p = (e.entity as Player).player
        if (Warrior.arenaManager.isPlaying(p as Player)) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onItemClick(e: InventoryClickEvent) {
        if (e.whoClicked is Player) {
            val p = e.whoClicked as Player
            if (Warrior.arenaManager.isPlaying(p)) {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onSwapHand(e: PlayerSwapHandItemsEvent) {
        val p = e.player
        if (Warrior.arenaManager.isPlaying(p)) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onDurability(e: PlayerItemDamageEvent) {
        val user: User = Warrior.userCache[e.player.uniqueId]
        if (user.session !is ArenaSession) return
        e.isCancelled = true
    }
}