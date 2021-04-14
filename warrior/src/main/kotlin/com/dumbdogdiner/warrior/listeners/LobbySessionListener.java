package com.dumbdogdiner.warrior.listeners;

import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class LobbySessionListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(((Player)e.getEntity()).getGameMode().equals(GameMode.CREATIVE)) return;
        User user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemDrop(PlayerDropItemEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        User user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemSwap(PlayerSwapHandItemsEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        User user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(((Player)e.getEntity()).getGameMode().equals(GameMode.CREATIVE)) return;
        User user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getEntity().getGameMode().equals(GameMode.CREATIVE)) return;
        User user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.getDrops().clear();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemClick(InventoryClickEvent e) {
        if(e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) return;
        User user = PlayerManager.get(e.getWhoClicked().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof LobbySession)) return;
        e.setCancelled(true);
        e.getWhoClicked().closeInventory();
    }

}
