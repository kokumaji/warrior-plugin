package com.dumbdogdiner.warrior.gui.settings;

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.util.HeadTexture;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LanguageGUI extends GUI {

    public LanguageGUI() {
        super(3, "Select Language", Warrior.getInstance());
    }

    @Override
    public void onInventoryOpen(@NotNull InventoryOpenEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ").build();

        for(int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 2, placeholder);
        }

        ItemStack unitedStates = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&b" + LanguageCode.EN_US.getFriendlyName())
                .setTexture(LanguageCode.EN_US.getHeadTexture())
                .build();
        ItemStack unitedKingdom = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&b" + LanguageCode.EN_GB.getFriendlyName())
                .setTexture(LanguageCode.EN_GB.getHeadTexture())
                .build();
        ItemStack germany = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&b" + LanguageCode.DE_DE.getFriendlyName())
                .setTexture(LanguageCode.DE_DE.getHeadTexture())
                .build();
        ItemStack back = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&bBack")
                .setTexture(HeadTexture.ARROW_RIGHT)
                .build();

        ClickableSlot backSlot = new ClickableSlot(back, 8, 1);
        ClickableSlot usSlot = new ClickableSlot(unitedStates, 2, 1);
        ClickableSlot ukSlot = new ClickableSlot(unitedKingdom, 3, 1);
        ClickableSlot germanySlot = new ClickableSlot(germany, 4, 1);

        String msg = TranslationUtil.getPrefix() +   Warrior.getTranslator().translate("settings-gui.language-changed");

        addSlot(usSlot, (evt, gui) -> {
            user.getSettings().setLanguage(LanguageCode.EN_US);
            evt.getWhoClicked().closeInventory();

            user.sendMessage(Warrior.getTranslator().applyPlaceholders(msg, new HashMap<>() {
                {
                    put("LANGUAGE", LanguageCode.EN_US.getFriendlyName());
                }
            }));

        });
        addSlot(ukSlot, (evt, gui) -> {
            user.getSettings().setLanguage(LanguageCode.EN_GB);
            evt.getWhoClicked().closeInventory();

            user.sendMessage(Warrior.getTranslator().applyPlaceholders(msg, new HashMap<>() {
                {
                    put("LANGUAGE", LanguageCode.EN_GB.getFriendlyName());
                }
            }));

        });
        addSlot(germanySlot, (evt, gui) -> {
            user.getSettings().setLanguage(LanguageCode.DE_DE);
            evt.getWhoClicked().closeInventory();

            user.sendMessage(Warrior.getTranslator().applyPlaceholders(msg, new HashMap<>() {
                {
                    put("LANGUAGE", LanguageCode.DE_DE.getFriendlyName());
                }
            }));

        });
        addSlot(backSlot, (evt, gui) -> {
            SettingsGUI langGUI = GUIManager.get(SettingsGUI.class);
            langGUI.open(user.getBukkitPlayer());
        });
    }
}
