package com.dumbdogdiner.warrior.gui

import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.user.cosmetics.ParticleTrail
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.Arrays

class ParticleTrailGUI() : GUI(5, "Particle Trails", Warrior.instance) {

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()
        val blank = ItemBuilder(Material.GRAY_DYE).setName("§7Arena §3???").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 4, placeholder)
        }

        val exit = ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4)
        addSlot(exit)

        val particleCount = ParticleTrail.values().size
        val particles = ParticleTrail.values()
        var i = 0

        for (y in 1..3) {
            for (x in 1..7) {
                if (i >= particleCount) {
                    addSlot(x, y, blank)
                    continue
                }

                val particle = particles[i]
                val head = ItemBuilder(Material.PLAYER_HEAD)
                    .setName("&7Particle &3" + particle.getName())
                    .setTexture(particle.icon)
                    .build()

                val cs = ClickableSlot(head, x, y)
                addSlot(cs)

                i++
            }
        }
    }
}
