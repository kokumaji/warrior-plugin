package com.dumbdogdiner.warrior.gui;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.kit.effects.DeathSound;
import com.dumbdogdiner.warrior.api.kit.effects.DeathSounds;
import com.dumbdogdiner.warrior.api.util.ItemBuilder;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DeathSoundGUI extends GUI {

    public DeathSoundGUI() {
        super(5, "Deathsounds", Warrior.getInstance());
    }

    @Override
    protected void onInventoryOpen(@NotNull InventoryOpenEvent e) {
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                                .setName(" ").build();

        ItemStack blank = new ItemBuilder(Material.GRAY_DYE)
                          .setName("§7Sound §3???").build();

        for(int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 4, placeholder);
        }

        ClickableSlot exit = new ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4);
        addSlot(exit);

        int soundCount = DeathSound.values().length;
        int i = 0;

        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());

        for(int y = 1; y < 4; y++) {
            for(int x = 1; x < 8; x++) {
                if(i >= soundCount) {
                    addSlot(x, y, blank);
                    continue;
                }

                DeathSound sound = DeathSound.values()[i];

                ItemStack soundItem = new ItemBuilder(Material.LIME_DYE)
                                    .setName("§7Sound §3" + sound.friendlyName())
                                    .build();

                if(DeathSounds.canUse(user, sound)) {
                    ClickableSlot cs = new ClickableSlot(soundItem, x, y);
                    addSlot(cs);
                } else addSlot(x, y, blank);

                i++;
            }
        }
    }
}
