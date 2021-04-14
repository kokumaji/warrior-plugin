package com.dumbdogdiner.warrior.gui

import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathSound
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathSounds
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryOpenEvent

class DeathSoundGUI : GUI(5, "Deathsounds", Warrior.instance) {

    override fun onInventoryOpen(e: InventoryOpenEvent) {
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()
        val blank = ItemBuilder(Material.GRAY_DYE).setName("§7Arena §3???").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 4, placeholder)
        }

        val exit = ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4)
        addSlot(exit)

        val user: User = Warrior.userCache[e.player.uniqueId]
        val soundCount = DeathSound.values().size
        var i = 0

        for (y in 1..3) {
            for (x in 1..7) {
                if (i >= soundCount) {
                    addSlot(x, y, blank)
                    continue
                }

                val sound = DeathSound.values()[i]
                val soundItem = ItemBuilder(Material.LIME_DYE)
                    .setName("§7Sound §3" + sound.friendlyName())
                    .build()

                if (DeathSounds.canUse(user, sound)) {
                    val cs = ClickableSlot(soundItem, x, y)
                    addSlot(cs)
                } else addSlot(x, y, blank)

                i++
            }
        }
    }

}