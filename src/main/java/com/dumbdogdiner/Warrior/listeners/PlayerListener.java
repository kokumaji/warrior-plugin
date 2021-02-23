package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.Region;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.GameState;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.managers.ArenaManager;

import com.dumbdogdiner.Warrior.managers.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Objects;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!PlayerManager.contains(e.getPlayer().getUniqueId())) {
            PlayerManager.addUser(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(PlayerManager.contains(e.getPlayer().getUniqueId())) {
            PlayerManager.remove(e.getPlayer().getUniqueId());
        }
    }


    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ() || e.getFrom().getBlockY() != e.getTo().getBlockY()) {
            if(ArenaManager.isPlaying(e.getPlayer())) {
                Player p = e.getPlayer();
                Arena a = Objects.requireNonNull(ArenaManager.getSession(p)).getArena();

                Region r = a.getBounds();

                if(!r.contains(e.getTo())) {
                    Vector playerVec = p.getLocation().toVector();
                    Vector center = r.center();

                    p.setVelocity(center.subtract(playerVec).normalize());
                    p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 0.35f, 1f);
                }

            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            WarriorUser user = PlayerManager.get(p.getUniqueId());
            if(((ArenaSession)user.getSession()).getState() == GameState.PRE_GAME) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if(ArenaManager.isPlaying(p)) {
                WarriorUser user = PlayerManager.get(p.getUniqueId());
                if(((ArenaSession)user.getSession()).getState() == GameState.PRE_GAME) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            WarriorUser user = PlayerManager.get(p.getUniqueId());
            if(((ArenaSession)user.getSession()).getState() == GameState.PRE_GAME) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getItem() == null) return;

        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            WarriorUser user = PlayerManager.get(p.getUniqueId());
            if(((ArenaSession)user.getSession()).getState() == GameState.PRE_GAME) {
                ItemMeta meta = e.getItem().getItemMeta();

                if(meta.getDisplayName().equals("§4§l☓ §c§lQUIT §4§l☓")) {
                    user.setSession(new LobbySession(user.getUserId()));
                    if(user.isSpectating()) user.setSpectating(false);
                    p.getInventory().clear();
                } else if(meta.getDisplayName().equals("§8» §3§lSPECTATE §8«")) {
                    ((ArenaSession) user.getSession()).setState(GameState.SPECTATING);
                    user.setSpectating(true);
                } else {
                    p.sendActionBar("§4§lFeature Not Implemented!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f);
                }

                e.setCancelled(true);
            }
        }
    }
}
