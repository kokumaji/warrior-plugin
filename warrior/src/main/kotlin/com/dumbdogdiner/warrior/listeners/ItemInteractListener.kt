package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.reflection.FieldUtil
import java.util.WeakHashMap
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.api.sessions.LobbySession
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.gui.ArenaGUI
import com.dumbdogdiner.warrior.managers.GUIManager
import com.dumbdogdiner.warrior.gui.DeathSoundGUI
import com.dumbdogdiner.warrior.gui.settings.SettingsGUI
import com.dumbdogdiner.warrior.gui.KitGUI
import com.dumbdogdiner.warrior.api.sessions.GameState
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class ItemInteractListener : Listener {

    var clickTime = WeakHashMap<Player, Int>()

    @EventHandler
    fun onItemUse(e: PlayerInteractEvent) {
        if (e.item == null) return
        val user: User = UserCache.get(e.player.uniqueId) ?: return

        if (user.session == null) return

        if (user.session is LobbySession || user.session is ArenaSession) {
            val lobbyItems = FieldUtil.getWithType(
                ItemStack::class.java, LobbySession::class.java
            )
            val arenaItems = FieldUtil.getWithType(
                ItemStack::class.java, ArenaSession::class.java
            )

            val item = e.item

            if (user.bukkitPlayer.inventory.type == InventoryType.CHEST) return

            val currentTick = Bukkit.getCurrentTick()
            if (clickTime[user.bukkitPlayer] == null || clickTime[user.bukkitPlayer] != currentTick) {
                clickTime[user.bukkitPlayer] = currentTick

                if (lobbyItems.contains(item)) {
                    e.isCancelled = true

                    var gui: GUI? = when(item) {
                        LobbySession.ARENA_ITEM -> Warrior.guiManager[ArenaGUI::class.java]
                        LobbySession.SHOP_ITEM -> Warrior.guiManager[DeathSoundGUI::class.java]
                        LobbySession.SETTINGS_ITEM -> Warrior.guiManager[SettingsGUI::class.java]
                        else -> null
                    }

                    if(gui == null) {
                        user.bukkitPlayer.sendActionBar("§4§lFeature Not Implemented!")
                        user.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f)
                    } else gui.open(e.player)

                } else if (arenaItems.contains(item)) {
                    e.isCancelled = true

                    if (item == ArenaSession.KIT_SELECTOR) {
                        val gui: KitGUI = Warrior.guiManager[KitGUI::class.java]
                        gui.open(e.player)
                    } else if (item == ArenaSession.EXIT_ARENA) {
                        user.session = LobbySession(user.userId)
                        if (user.isSpectating) user.isSpectating = false
                    } else if (item == ArenaSession.SPECTATE_GAME) {
                        (user.session as ArenaSession?)!!.state = GameState.SPECTATING
                        user.isSpectating = true
                    } else {
                        user.bukkitPlayer.sendActionBar("§4§lFeature Not Implemented!")
                        user.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f)
                    }
                }
            }
        }
    }

    // if above still fails, let's at least prevent users from
    // taking items out of the inventory
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.inventory.type == InventoryType.CHEST) {
            val user: User = Warrior.userCache[e.whoClicked.uniqueId]
            if (user.session !is LobbySession) return

            e.isCancelled = true
        }
    }
}