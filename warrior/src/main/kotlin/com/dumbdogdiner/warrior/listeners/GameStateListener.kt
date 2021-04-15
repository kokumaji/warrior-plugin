package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.events.GameStateChangeEvent
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.sessions.GameState
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class GameStateListener : Listener {
    @EventHandler
    fun onStateChange(e: GameStateChangeEvent) {
        if (e.context is ArenaSession) {
            val p = e.player
            when (e.toState) {
                // When the player joins an arena
                GameState.PRE_GAME -> {

                    // TODO: make event use User objects and call User#reset() instead!
                    p.teleport((e.context as ArenaSession).arena.spawn)
                    p.fireTicks = 0
                    p.gameMode = GameMode.ADVENTURE

                    // only true if player is in creative mode
                    p.isFlying = p.gameMode == GameMode.CREATIVE
                    e.context.setupInventory(p)
                }
                GameState.SPECTATING -> {
                    e.context.setupInventory(p)
                    p.teleport((e.context as ArenaSession).arena.spawn)
                }
                GameState.IN_GAME -> {
                    // TODO: Add something here?
                }
                else -> Warrior.pluginLogger.warn("Illegal state for property GameState of ArenaSession: null")
            }
        }
    }
}