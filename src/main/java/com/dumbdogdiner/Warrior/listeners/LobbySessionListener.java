package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.gui.ArenaGUI;
import com.dumbdogdiner.Warrior.gui.DeathSoundGUI;
import com.dumbdogdiner.Warrior.managers.GUIManager;
import com.dumbdogdiner.Warrior.managers.LobbyManager;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.WeakHashMap;

public class LobbySessionListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(((Player)e.getEntity()).getGameMode().equals(GameMode.CREATIVE)) return;
        WarriorUser user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemDrop(PlayerDropItemEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemSwap(PlayerSwapHandItemsEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(((Player)e.getEntity()).getGameMode().equals(GameMode.CREATIVE)) return;
        WarriorUser user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getEntity().getGameMode().equals(GameMode.CREATIVE)) return;
        WarriorUser user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.getDrops().clear();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemClick(InventoryClickEvent e) {
        if(e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) return;
        WarriorUser user = PlayerManager.get(e.getWhoClicked().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
        e.getWhoClicked().closeInventory();
    }

    WeakHashMap<Player, Integer> clickTime = new WeakHashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {
        if(e.getItem() == null) return;
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        if(user.getBukkitPlayer().getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);

        // credit to spazzylemons for this solution uwu
        int currentTick = Bukkit.getCurrentTick();
        if(clickTime.get(user.getBukkitPlayer()) == null || clickTime.get(user.getBukkitPlayer()) != currentTick) {
            clickTime.put(user.getBukkitPlayer(), currentTick);

            if(user.getBukkitPlayer().getOpenInventory().getType() == InventoryType.CHEST) return;

            if(e.getItem().equals(SessionChangeListener.ARENA_ITEM)) {
                ArenaGUI gui = GUIManager.get(ArenaGUI.class);
                gui.open(e.getPlayer());
            } else if(e.getItem().equals(SessionChangeListener.SHOP_ITEM)) {
                DeathSoundGUI gui = GUIManager.get(DeathSoundGUI.class);
                gui.open(e.getPlayer());
            } else {
                user.getBukkitPlayer().sendActionBar("§4§lFeature Not Implemented!");
                user.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f);
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
