package com.dumbdogdiner.warrior.gui.settings;

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.translation.Placeholders;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.api.util.HeadTexture;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PrivacyGUI extends GUI {

    public PrivacyGUI() {
        super(3, "Change Privacy Level", Warrior.getInstance());
    }

    @Override
    public void onInventoryOpen(@NotNull InventoryOpenEvent e) {
        User user = PlayerManager.get(e.getPlayer().getUniqueId());
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ").build();

        for (int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 2, placeholder);
        }

        ItemStack allPublic = new ItemBuilder(Material.WRITABLE_BOOK)
                .setName("&bAll Public!")
                .makeGlow(user.getSettings().getPrivacyLevel() == 0)
                .build();

        ItemStack hideLastJoin = new ItemBuilder(Material.WRITABLE_BOOK)
                .makeGlow(user.getSettings().getPrivacyLevel() == 1)
                .setName("&bHide Last Join").build();

        ItemStack hideJoin = new ItemBuilder(Material.WRITABLE_BOOK)
                .makeGlow(user.getSettings().getPrivacyLevel() == 2)
                .setName("&bHide Last + First Join").build();

        ItemStack hideAll = new ItemBuilder(Material.WRITABLE_BOOK)
                .makeGlow(user.getSettings().getPrivacyLevel() == 3)
                .setName("&bFully Hidden!").build();

        ItemStack back = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&bBack")
                .setTexture(HeadTexture.ARROW_RIGHT)
                .build();

        ClickableSlot backSlot = new ClickableSlot(back, 8, 1);
        ClickableSlot publicSlot = new ClickableSlot(allPublic, 2, 1);
        ClickableSlot hideLastSlot = new ClickableSlot(hideLastJoin, 3, 1);
        ClickableSlot hideJoinSlot = new ClickableSlot(hideJoin, 4, 1);
        ClickableSlot hideAllSlot = new ClickableSlot(hideAll, 5, 1);

        String msg = TranslationUtil.getPrefix() + Warrior.getTranslator().translate("settings-gui.privacy-changed", user);

        addSlot(publicSlot, (evt, gui) -> {
            user.getSettings().setPrivacyLevel(0);

            user.sendMessage(Placeholders.applyPlaceholders(msg, new HashMap<>() {
                {
                    put("STATE", TranslationUtil.privacyString(user));
                }
            }));
        });

        addSlot(hideLastSlot, (evt, gui) -> {
            user.getSettings().setPrivacyLevel(1);
            user.sendMessage(Placeholders.applyPlaceholders(msg, new HashMap<>() {
                {
                    put("STATE", TranslationUtil.privacyString(user));
                }
            }));
        });

        addSlot(hideJoinSlot, (evt, gui) -> {
            user.getSettings().setPrivacyLevel(2);
            user.sendMessage(Placeholders.applyPlaceholders(msg, new HashMap<>() {
                {
                    put("STATE", TranslationUtil.privacyString(user));
                }
            }));
        });

        addSlot(hideAllSlot, (evt, gui) -> {
            user.getSettings().setPrivacyLevel(3);
            user.sendMessage(Placeholders.applyPlaceholders(msg, new HashMap<>() {
                {
                    put("STATE", TranslationUtil.privacyString(user));
                }
            }));
        });

        addSlot(backSlot, (evt, gui) -> {
            SettingsGUI parentGUI = GUIManager.get(SettingsGUI.class);
            evt.getWhoClicked().closeInventory();
            parentGUI.open(user.getBukkitPlayer());
        });

    }

}
