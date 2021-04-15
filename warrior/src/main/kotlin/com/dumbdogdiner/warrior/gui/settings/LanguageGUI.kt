package com.dumbdogdiner.warrior.gui.settings

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.warrior.api.translation.Placeholders
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode
import com.dumbdogdiner.warrior.api.util.HeadTexture
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.*

class LanguageGUI : GUI(3, "Select Language", Warrior.instance) {

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        val user: User = Warrior.userCache[event.player.uniqueId]
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 2, placeholder)
        }

        val unitedStates = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&b" + LanguageCode.EN_US.friendlyName)
            .setTexture(LanguageCode.EN_US.headTexture)
            .build()

        val unitedKingdom = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&b" + LanguageCode.EN_GB.friendlyName)
            .setTexture(LanguageCode.EN_GB.headTexture)
            .build()

        val germany = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&b" + LanguageCode.DE_DE.friendlyName)
            .setTexture(LanguageCode.DE_DE.headTexture)
            .build()

        val back = ItemBuilder(Material.PLAYER_HEAD)
            .setName("&bBack")
            .setTexture(HeadTexture.ARROW_RIGHT)
            .build()

        val backSlot = ClickableSlot(back, 8, 1)
        val usSlot = ClickableSlot(unitedStates, 2, 1)
        val ukSlot = ClickableSlot(unitedKingdom, 3, 1)
        val germanySlot = ClickableSlot(germany, 4, 1)

        val msg = TranslationUtil.getPrefix() + Warrior.translator.translate("settings-gui.language-changed", user)
        addSlot(usSlot) { evt: InventoryClickEvent, _: GUI? ->
            user.settings.language = LanguageCode.EN_US
            evt.whoClicked.closeInventory()
            user.sendMessage(Placeholders.applyPlaceholders(msg, object : HashMap<String?, String?>() {
                init {
                    put("language", LanguageCode.EN_US.friendlyName)
                }
            }))
        }
        addSlot(ukSlot) { evt: InventoryClickEvent, _: GUI? ->
            user.settings.language = LanguageCode.EN_GB
            evt.whoClicked.closeInventory()
            user.sendMessage(Placeholders.applyPlaceholders(msg, object : HashMap<String?, String?>() {
                init {
                    put("language", LanguageCode.EN_GB.friendlyName)
                }
            }))
        }
        addSlot(germanySlot) { evt: InventoryClickEvent, _: GUI? ->
            user.settings.language = LanguageCode.DE_DE
            evt.whoClicked.closeInventory()
            user.sendMessage(Placeholders.applyPlaceholders(msg, object : HashMap<String?, String?>() {
                init {
                    put("language", LanguageCode.DE_DE.friendlyName)
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
