package com.dumbdogdiner.warrior.gui.settings;

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.user.cosmetics.WarriorTitle;
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings;
import com.dumbdogdiner.warrior.api.util.HeadTexture;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SettingsGUI extends GUI {

    public SettingsGUI() {
        super(5, "Settings", Warrior.getInstance());
    }

    @Override
    protected void onInventoryOpen(@NotNull InventoryOpenEvent event) {
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ").build();

        for(int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 4, placeholder);
        }

        WarriorUser user = PlayerManager.get(event.getPlayer().getUniqueId());
        if(user == null || user.getSettings() == null) {
            ItemStack errorHead = HeadTexture.asItem(HeadTexture.MHF_QUESTION);
            addSlot(1, 4, errorHead);
            return;
        }

        GeneralSettings settings = user.getSettings();

        String walkType = settings.canFly() ? "Flying" : "Walking";
        String visibilityType = settings.getPlayerVisibility() > 0 ? settings.getPlayerVisibility() < 2 ? "Semi-Transparent" : "Hidden" : "Visible";
        String notificationType = settings.receiveNotifications() ? "Enabled" : "Disabled";
        String privacyType = TranslationUtil.privacyString(user);
        String titleContent = settings.getTitle() != WarriorTitle.EMPTY ? settings.getTitle().getTitle() : "None";
        LanguageCode lang = settings.getLanguage();

        ItemStack walkOption = new ItemBuilder(Material.IRON_BOOTS)
                .setName("&3&lWalk Type")
                .setLore("&b" + walkType, " ", "&7Switch between Walk/Fly Mode.", "&7This setting only works in the Lobby!")
                .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        ItemStack visibilityOption = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&3&lPlayer Visibility")
                .setLore("&b" + visibilityType, " ", "&7Toggle Player Visibility. Only affects", "&7Players in the Lobby. Staff members", "&7are always visible.")
                .setTexture(HeadTexture.VILLAGER_WITH_CAP)
                .build();

        ItemStack notificationsOption = new ItemBuilder(Material.FILLED_MAP)
                .setName("&3&lNotifications")
                .setLore("&b" + notificationType, " ", "&7Enable/Disable useful Tips ", "&7and Tricks in Chat.")
                .build();

        ItemStack privacyOption = new ItemBuilder(Material.WRITABLE_BOOK)
                .setName("&3&lPrivacy Mode")
                .setLore("&b" + privacyType, " ", "&7Set your Privacy Level. This will ", "&7hide your 'Last Seen' status, and you", "&7won't appear on the Leaderboard.")
                .build();

        ItemStack titleOption = new ItemBuilder(Material.OAK_SIGN)
                .setName("&3&lSet Title")
                .setLore("&b" + titleContent, " ", "&7Set your Player Title", "&8&oOnly Visible in Chat")
                .build();

        ItemStack languageOption = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&3&lChange Language")
                .setLore("&b" + lang.getFriendlyName(), " ", "&7Adjust your Language ", "&7and Chat formatting.")
                .setTexture(lang.getHeadTexture())
                .build();

        ClickableSlot languageSlot = new ClickableSlot(languageOption, 7, 1 );
        ClickableSlot notificationSlot = new ClickableSlot(notificationsOption, 4, 1 );
        ClickableSlot privacySlot = new ClickableSlot(privacyOption, 1, 1 );

        addSlot(languageSlot, (evt, gui) -> {
            LanguageGUI langGUI = GUIManager.get(LanguageGUI.class);
            langGUI.open(user.getBukkitPlayer());
        });
        addSlot(notificationSlot, (evt, gui) -> {
            NotificationsGUI notifGUI = GUIManager.get(NotificationsGUI.class);
            notifGUI.open(user.getBukkitPlayer());
        });
        addSlot(privacySlot, (evt, gui) -> {
            PrivacyGUI privacyGUI = GUIManager.get(PrivacyGUI.class);
            privacyGUI.open(user.getBukkitPlayer());
        });

        addSlot(4, 3, walkOption);
        addSlot(1, 3, visibilityOption);
        addSlot(7, 3, titleOption);

    }
}
