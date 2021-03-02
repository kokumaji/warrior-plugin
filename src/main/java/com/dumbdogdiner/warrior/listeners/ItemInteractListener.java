package com.dumbdogdiner.warrior.listeners;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.GameState;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.api.util.FieldUtil;
import com.dumbdogdiner.warrior.gui.ArenaGUI;
import com.dumbdogdiner.warrior.gui.DeathSoundGUI;
import com.dumbdogdiner.warrior.gui.KitGUI;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.WeakHashMap;

public class ItemInteractListener implements Listener {

    WeakHashMap<Player, Integer> clickTime = new WeakHashMap<>();

    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if(e.getItem() == null) return;

        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(user.getSession() == null) return;

        if(user.getSession() instanceof LobbySession || user.getSession() instanceof ArenaSession) {
            List<ItemStack> lobbyItems = FieldUtil.getWithType(ItemStack.class, LobbySession.class);
            List<ItemStack> arenaItems = FieldUtil.getWithType(ItemStack.class, ArenaSession.class);

            ItemStack item = e.getItem();

            if(user.getBukkitPlayer().getInventory().getType().equals(InventoryType.CHEST)) return;

            int currentTick = Bukkit.getCurrentTick();
            if(clickTime.get(user.getBukkitPlayer()) == null || clickTime.get(user.getBukkitPlayer()) != currentTick) {
                clickTime.put(user.getBukkitPlayer(), currentTick);

                if(lobbyItems.contains(item)) {
                    e.setCancelled(true);

                    if(item.equals(LobbySession.ARENA_ITEM)) {
                        ArenaGUI gui = GUIManager.get(ArenaGUI.class);
                        gui.open(e.getPlayer());
                    } else if(item.equals(LobbySession.SHOP_ITEM)) {
                        DeathSoundGUI gui = GUIManager.get(DeathSoundGUI.class);
                        gui.open(e.getPlayer());
                    } else {
                        user.getBukkitPlayer().sendActionBar("§4§lFeature Not Implemented!");
                        user.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f);
                    }

                } else if(arenaItems.contains(item)) {
                    e.setCancelled(true);

                    if(item.equals(ArenaSession.KIT_SELECTOR)) {
                        KitGUI gui = GUIManager.get(KitGUI.class);
                        gui.open(e.getPlayer());
                    } else if(item.equals(ArenaSession.EXIT_ARENA)) {
                        user.setSession(new LobbySession(user.getUserId()));
                        if(user.isSpectating()) user.setSpectating(false);
                    } else if(item.equals(ArenaSession.SPECTATE_GAME)) {
                        ((ArenaSession) user.getSession()).setState(GameState.SPECTATING);
                        user.setSpectating(true);
                    } else {
                        user.getBukkitPlayer().sendActionBar("§4§lFeature Not Implemented!");
                        user.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f);
                    }
                }
            }

        }
    }

    // if above still fails, let's at least prevent users from
    // taking items out of the inventory
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getType() == InventoryType.CHEST) {
            WarriorUser user = PlayerManager.get(e.getWhoClicked().getUniqueId());
            if(!(user.getSession() instanceof LobbySession)) return;

            e.setCancelled(true);
        }
    }

}
