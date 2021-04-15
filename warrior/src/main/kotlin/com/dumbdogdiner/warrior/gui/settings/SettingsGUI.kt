package com.dumbdogdiner.warrior.gui.settings

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.warrior.api.user.cosmetics.WarriorTitle
import com.dumbdogdiner.warrior.api.util.HeadTexture
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemFlag

class SettingsGUI : GUI(5, "Settings", Warrior.instance) {

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        val user: User = Warrior.userCache[event.player.uniqueId]
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 4, placeholder)
        }

        val settings = user.settings
        if (user.settings == null) {
            val errorHead = HeadTexture.asItem(HeadTexture.MHF_QUESTION)
            addSlot(1, 4, errorHead)
            return
        }

        val walkType = if (settings.canFly()) "Flying" else "Walking"
        val visibilityType = if (settings.playerVisibility > 0) if (settings.playerVisibility < 2) "Semi-Transparent" else "Hidden" else "Visible"
        val notificationType = if (settings.receiveNotifications()) "Enabled" else "Disabled"
        val privacyType = TranslationUtil.privacyString(user)
        val titleContent = if (settings.title != WarriorTitle.EMPTY) settings.title.title else "None"

        val lang = settings.language

        val walkOption = ItemBuilder(Material.IRON_BOOTS)
            .setName("&3&lWalk Type")
            .setLore("&b$walkType", " ", "&7Switch between Walk/Fly Mode.", "&7This setting only works in the Lobby!")
            .addFlags(ItemFlag.HIDE_ATTRIBUTES)
            .build()

        val visibilityOption = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&3&lPlayer Visibility")
            .setLore(
                "&b$visibilityType",
                " ",
                "&7Toggle Player Visibility. Only affects",
                "&7Players in the Lobby. Staff members",
                "&7are always visible."
            )
            .setTexture(HeadTexture.VILLAGER_WITH_CAP)
            .build()

        val notificationsOption = ItemBuilder(Material.FILLED_MAP)
            .setName("&3&lNotifications")
            .setLore("&b$notificationType", " ", "&7Enable/Disable useful Tips ", "&7and Tricks in Chat.")
            .build()

        val privacyOption = ItemBuilder(Material.WRITABLE_BOOK)
            .setName("&3&lPrivacy Mode")
            .setLore(
                "&b$privacyType",
                " ",
                "&7Set your Privacy Level. This will ",
                "&7hide your 'Last Seen' status, and you",
                "&7won't appear on the Leaderboard."
            )
            .build()

        val titleOption = ItemBuilder(Material.OAK_SIGN)
            .setName("&3&lSet Title")
            .setLore("&b$titleContent", " ", "&7Set your Player Title", "&8&oOnly Visible in Chat")
            .build()

        val languageOption = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&3&lChange Language")
            .setLore("&b" + lang.friendlyName, " ", "&7Adjust your Language ", "&7and Chat formatting.")
            .setTexture(lang.headTexture)
            .build()

        val languageSlot = ClickableSlot(languageOption, 7, 1)
        val notificationSlot = ClickableSlot(notificationsOption, 4, 1)
        val privacySlot = ClickableSlot(privacyOption, 1, 1)

        addSlot(languageSlot) { evt: InventoryClickEvent, _: GUI? ->
            val parentGUI: LanguageGUI = Warrior.guiManager[LanguageGUI::class.java]

            evt.whoClicked.closeInventory()
            parentGUI.open(user.bukkitPlayer)
        }
        addSlot(notificationSlot) { evt: InventoryClickEvent, _: GUI? ->
            val parentGUI: NotificationsGUI = Warrior.guiManager[NotificationsGUI::class.java]

            evt.whoClicked.closeInventory()
            parentGUI.open(user.bukkitPlayer)
        }
        addSlot(privacySlot) { evt: InventoryClickEvent, _: GUI? ->
            val parentGUI: PrivacyGUI = Warrior.guiManager[PrivacyGUI::class.java]

            evt.whoClicked.closeInventory()
            parentGUI.open(user.bukkitPlayer)
        }

        addSlot(4, 3, walkOption)
        addSlot(1, 3, visibilityOption)
        addSlot(7, 3, titleOption)
        // TODO: Add other GUIs

    }

}