package com.dumbdogdiner.warrior.gui.settings

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.warrior.api.util.HeadTexture
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class NotificationsGUI : GUI(3, "Enable/Disable Notifications", Warrior.instance) {

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        val user: User = Warrior.userCache[event.player.uniqueId]
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 2, placeholder)
        }

        val enableItem = ItemBuilder(Material.LIME_TERRACOTTA)
            .makeGlow(user.settings.receiveNotifications())
            .setName("&aEnable")
            .build()

        val disableItem = ItemBuilder(Material.RED_TERRACOTTA)
            .makeGlow(!user.settings.receiveNotifications())
            .setName("&cDisable")
            .build()

        val back = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&bBack")
            .setTexture(HeadTexture.ARROW_RIGHT)
            .build()

        val backSlot = ClickableSlot(back, 8, 1)
        val enableSlot = ClickableSlot(enableItem, 3, 1)
        val disableSlot = ClickableSlot(disableItem, 5, 1)

        addSlot(enableSlot) { _: InventoryClickEvent?, _: GUI? ->
            user.settings.receiveNotifications(true)
            val msg = TranslationUtil.getPrefix() + Warrior.translator.translate("settings-gui.notifications-changed", user)
            user.sendMessage(msg)
        }

        addSlot(disableSlot) { _: InventoryClickEvent?, _: GUI? ->
            user.settings.receiveNotifications(false)
            val msg = TranslationUtil.getPrefix() + Warrior.translator.translate("settings-gui.notifications-changed", user)
            user.sendMessage(msg)
        }

        addSlot(backSlot) { evt: InventoryClickEvent, _: GUI? ->
            val parentGUI: SettingsGUI = Warrior.guiManager[SettingsGUI::class.java]

            evt.whoClicked.closeInventory()
            parentGUI.open(user.bukkitPlayer)
        }
    }
}
