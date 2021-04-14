package com.dumbdogdiner.warrior.gui

import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot
import com.dumbdogdiner.warrior.api.arena.Arena
import java.util.Arrays
import com.dumbdogdiner.warrior.api.translation.Symbols
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.Constants
import java.util.HashMap
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class ArenaGUI : GUI(5, "Select Arena", Warrior.instance) {

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        val placeholder = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()
        val blank = ItemBuilder(Material.GRAY_DYE).setName("§7Arena §3???").build()

        for (i in 0..8) {
            addSlot(i, 0, placeholder)
            addSlot(i, 4, placeholder)
        }

        val exit = ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4)
        addSlot(exit)

        val arenaCount: Int = Warrior.arenaManager.arenas.size
        var i = 0
        for (y in 1..3) {
            for (x in 1..7) {
                if (i >= arenaCount) {
                    addSlot(x, y, blank)
                    continue
                }

                val a: Arena = Warrior.arenaManager.arenas[i]
                val avgRating = a.metadata.averageRating().toInt()

                val colorDesc = Arrays.stream(a.metadata.desc)
                    .map { str: String -> "§7§o$str" }
                    .toArray<String> { length -> arrayOfNulls(length) }

                val arenaItem = ItemBuilder(Material.FILLED_MAP)
                    .setName("§7Arena §3" + a.name)
                    .setLore(" ", "&7Rating &8» &b" + calcRating(avgRating), " ")
                    .appendLore(*colorDesc)
                    .build()

                val cs = ClickableSlot(arenaItem, x, y)
                addSlot(cs)

                i++
            }
        }
    }

    private fun calcRating(rating: Int): String {
        val stars = Symbols.BLACK_STAR * rating
        return stars + Symbols.WHITE_STAR * (5 - rating)
    }

    private operator fun Char.times(amount: Int): String = this.toString().repeat(amount)

    override fun onInventoryClick(event: InventoryClickEvent, tag: String?) {
        val item = event.currentItem ?: return

        val name = item.itemMeta.displayName

        if (name.contains("Close Menu")) {
            event.whoClicked.closeInventory()
        } else if (name.contains("Arena")) {
            val arenaName = name.replace("§7Arena §3", "")
            val user: User = Warrior.userCache[event.whoClicked.uniqueId]
            val a: Arena = Warrior.arenaManager[arenaName]

            user.session = ArenaSession(user.userId, a)

            val msg: String = Warrior.translator.translate(Constants.Lang.ARENA_TELEPORT, object : HashMap<String, String>() {
                init {
                    put("ARENA", a.name)
                }
            }, user)

            user.sendMessage(TranslationUtil.getPrefix() + msg)
            user.playSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1f)
        }
    }
}
