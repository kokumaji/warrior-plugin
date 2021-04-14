package com.dumbdogdiner.warrior.gui

import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.warrior.managers.KitManager
import com.dumbdogdiner.warrior.api.kit.BaseKit
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.sessions.GameState
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.Constants
import java.util.HashMap
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.Plugin

class KitGUI() : GUI(5, "Select a Kit", Warrior.instance) {

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()
        val blank = ItemBuilder(Material.GRAY_DYE).setName("§7Arena §3???").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 4, placeholder)
        }

        val exit = ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4)
        addSlot(exit)

        val kitCount: Int = Warrior.kitManager.kits.size
        var i = 0
        for (y in 1..3) {
            for (x in 1..7) {
                if (i >= kitCount) {
                    addSlot(x, y, blank)
                    continue
                }

                val k: BaseKit = Warrior.kitManager.kits[i]
                val kitItem = ItemBuilder(k.icon)
                    .setName("§7Kit §3" + k.name)
                    .setLore(*k.description)
                    .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .build()

                val cs = ClickableSlot(kitItem, x, y)
                addSlot(cs)
                i++
            }
        }
    }

    override fun onInventoryClick(event: InventoryClickEvent, tag: String?) {
        val item = event.currentItem ?: return

        val name = item.itemMeta.displayName

        if (name.contains("Close Menu")) {
            event.whoClicked.closeInventory()
        } else if (name.contains("Kit")) {
            val kitName = name.replace("§7Kit §3", "")
            val user: User = Warrior.userCache[event.whoClicked.uniqueId]
            val kit: BaseKit = Warrior.kitManager[kitName]

            kit.giveKit(user.bukkitPlayer)
            (user.session as ArenaSession?)!!.state = GameState.IN_GAME
            (user.session as ArenaSession?)!!.kit = kit

            val msg: String = Warrior.translator.translate(Constants.Lang.KIT_EQUIP, object : HashMap<String, String>() {
                init {
                    put("KIT", kit.name)
                }
            }, user)

            user.sendMessage(TranslationUtil.getPrefix() + msg)
            user.playSound(Sound.ITEM_ARMOR_EQUIP_CHAIN, 0.5f, 1f)
            user.bukkitPlayer.closeInventory()
        }
    }
}
