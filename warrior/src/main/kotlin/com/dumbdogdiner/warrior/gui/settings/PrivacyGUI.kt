package com.dumbdogdiner.warrior.gui.settings

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.warrior.api.translation.Placeholders
import com.dumbdogdiner.warrior.api.util.HeadTexture
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.*

class PrivacyGUI : GUI(3, "Change Privacy Level", Warrior.instance) {

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        val user: User = Warrior.userCache[event.player.uniqueId]
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 2, placeholder)
        }

        val allPublic = ItemBuilder(Material.WRITABLE_BOOK)
            .setName("&bAll Public!")
            .makeGlow(user.settings.privacyLevel == 0)
            .build()

        val hideLastJoin = ItemBuilder(Material.WRITABLE_BOOK)
            .makeGlow(user.settings.privacyLevel == 1)
            .setName("&bHide Last Join")
            .build()

        val hideJoin = ItemBuilder(Material.WRITABLE_BOOK)
            .makeGlow(user.settings.privacyLevel == 2)
            .setName("&bHide Last + First Join")
            .build()

        val hideAll = ItemBuilder(Material.WRITABLE_BOOK)
            .makeGlow(user.settings.privacyLevel == 3)
            .setName("&bFully Hidden!")
            .build()

        val back = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&bBack")
            .setTexture(HeadTexture.ARROW_RIGHT)
            .build()

        val backSlot = ClickableSlot(back, 8, 1)
        val publicSlot = ClickableSlot(allPublic, 2, 1)
        val hideLastSlot = ClickableSlot(hideLastJoin, 3, 1)
        val hideJoinSlot = ClickableSlot(hideJoin, 4, 1)
        val hideAllSlot = ClickableSlot(hideAll, 5, 1)

        val msg = TranslationUtil.getPrefix() + Warrior.translator.translate("settings-gui.privacy-changed", user)
        addSlot(publicSlot) { _: InventoryClickEvent?, _: GUI? ->
            user.settings.privacyLevel = 0
            user.sendMessage(Placeholders.applyPlaceholders(msg, object : HashMap<String?, String?>() {
                init {
                    put("state", TranslationUtil.privacyString(user))
                }
            }))
        }

        addSlot(hideLastSlot) { _: InventoryClickEvent?, _: GUI? ->
            user.settings.privacyLevel = 1
            user.sendMessage(Placeholders.applyPlaceholders(msg, object : HashMap<String?, String?>() {
                init {
                    put("state", TranslationUtil.privacyString(user))
                }
            }))
        }

        addSlot(hideJoinSlot) { _: InventoryClickEvent?, _: GUI? ->
            user.settings.privacyLevel = 2
            user.sendMessage(Placeholders.applyPlaceholders(msg, object : HashMap<String?, String?>() {
                init {
                    put("state", TranslationUtil.privacyString(user))
                }
            }))
        }

        addSlot(hideAllSlot) { _: InventoryClickEvent?, _: GUI? ->
            user.settings.privacyLevel = 3
            user.sendMessage(Placeholders.applyPlaceholders(msg, object : HashMap<String?, String?>() {
                init {
                    put("state", TranslationUtil.privacyString(user))
                }
            }))
        }

        addSlot(backSlot) { evt: InventoryClickEvent, _: GUI? ->
            val parentGUI: SettingsGUI = Warrior.guiManager[SettingsGUI::class.java]

            evt.whoClicked.closeInventory()
            parentGUI.open(user.bukkitPlayer)
        }
    }
}