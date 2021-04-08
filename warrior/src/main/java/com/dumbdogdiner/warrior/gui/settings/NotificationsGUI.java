package com.dumbdogdiner.warrior.gui.settings;

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.util.HeadTexture;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NotificationsGUI extends GUI {

    public NotificationsGUI() {
        super(3, "Enable/Disable Notifications", Warrior.getInstance());
    }

    @Override
    public void onInventoryOpen(@NotNull InventoryOpenEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ").build();

        for (int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 2, placeholder);
        }

        ItemStack enableItem = new ItemBuilder(Material.LIME_TERRACOTTA)
                .makeGlow(user.getSettings().receiveNotifications())
                .setName("&aEnable").build();

        ItemStack disableItem = new ItemBuilder(Material.RED_TERRACOTTA)
                .makeGlow(!user.getSettings().receiveNotifications())
                .setName("&cDisable").build();

        ItemStack back = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&bBack")
                .setTexture(HeadTexture.ARROW_RIGHT)
                .build();

        ClickableSlot backSlot = new ClickableSlot(back, 8, 1);
        ClickableSlot enableSlot = new ClickableSlot(enableItem, 3, 1);
        ClickableSlot disableSlot = new ClickableSlot(disableItem, 5, 1);

        addSlot(enableSlot, (evt, gui) -> {
            user.getSettings().receiveNotifications(true);
            String msg = TranslationUtil.getPrefix() + Warrior.getTranslator().translate("settings-gui.notifications-changed", user);
            user.sendMessage(msg);
        });

        addSlot(disableSlot, (evt, gui) -> {
            user.getSettings().receiveNotifications(false);
            String msg = TranslationUtil.getPrefix() + Warrior.getTranslator().translate("settings-gui.notifications-changed", user);
            user.sendMessage(msg);
        });

        addSlot(backSlot, (evt, gui) -> {
            SettingsGUI parentGUI = GUIManager.get(SettingsGUI.class);
            evt.getWhoClicked().closeInventory();
            parentGUI.open(user.getBukkitPlayer());
        });

    }
}
