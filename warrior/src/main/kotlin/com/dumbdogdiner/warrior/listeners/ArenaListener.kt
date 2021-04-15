package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.api.events.ArenaJoinEvent
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.MaxHealthFlag
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*

class ArenaListener : Listener {

    @EventHandler
    fun onArenaJoin(e: ArenaJoinEvent) {
        val arena = e.arena
        val flags = arena.flags
        val maxHealth = flags.getFlag(MaxHealthFlag::class.java)

        if (maxHealth != null)
            e.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = maxHealth.value
    }
}