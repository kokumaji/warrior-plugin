package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.WithWarriorPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class AbilityListener : Listener, WithWarriorPlugin {

    @EventHandler
    fun onItemUse(e: PlayerInteractEvent) {

        if(e.hand == EquipmentSlot.OFF_HAND) return

        if(e.action == Action.LEFT_CLICK_AIR || e.action == Action.LEFT_CLICK_BLOCK) {
            val user = getPlugin().userCache[e.player.uniqueId] ?: return
            val userKit = getPlugin().kitContainer[user.activeKit] ?: return

            // TODO: primitive implementation for debugging, final version should have a cooldown
            if(userKit.ability != null) {
                if(e.item == userKit.abilityItem) userKit.ability.run(user)
            }

        }
    }

}