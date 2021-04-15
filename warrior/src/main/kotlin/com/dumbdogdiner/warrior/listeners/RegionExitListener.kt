package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.arena.Arena
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class RegionExitListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onMove(e: PlayerMoveEvent) {

        // ignore player rotation updates & y-coordinate changes, we don't need them
        if (e.from.blockX != e.to.blockX || e.from.blockZ != e.to.blockZ) {

            // if the user is in an arena
            if (Warrior.arenaManager.isPlaying(e.player)) {
                val player = e.player
                val arena: Arena = Warrior.arenaManager.getSession(player)!!.arena
                val region = arena.bounds

                // if the user is about to exit
                if (!region.contains(e.to)) {
                    val playerVec = player.location.toVector()
                    val center = region.center()

                    // push the player towards the center point of this arena
                    player.velocity = center.subtract(playerVec).normalize()
                    player.playSound(player.location, Sound.UI_TOAST_IN, 0.35f, 1f)
                }
            }
        }
    }
}