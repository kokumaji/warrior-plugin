package com.dumbdogdiner.warrior.gui;

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;

import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.user.cosmetics.ParticleTrail;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ParticleTrailGUI extends GUI {

    public ParticleTrailGUI(@NotNull Plugin plugin) {
        super(5, "Particle Trails", plugin);
    }

    @Override
    protected void onInventoryOpen(@NotNull InventoryOpenEvent event) {
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ").build();

        ItemStack blank = new ItemBuilder(Material.GRAY_DYE)
                .setName("§7Particle §3???").build();

        for(int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 4, placeholder);
        }

        ClickableSlot exit = new ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4);
        addSlot(exit);

        int particleCount = ParticleTrail.values().length;
        int i = 0;

        List<ParticleTrail> particles = Arrays.asList(ParticleTrail.values());

        for (int y = 1; y < 4; y++) {
            for (int x = 1; x < 8; x++) {
                if (i >= particleCount) {
                    addSlot(x, y, blank);
                    continue;
                }
                ParticleTrail particle = particles.get(i);
                ItemStack head = new ItemBuilder(Material.PLAYER_HEAD)
                                    .setName("&7Particle &3" + particle.getName())
                                    .setTexture(particle.getIcon())
                                    .build();
                ClickableSlot cs = new ClickableSlot(head, x, y);
                addSlot(cs);
                i++;
            }
        }
    }

}
