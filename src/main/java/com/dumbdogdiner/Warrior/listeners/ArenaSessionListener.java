package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.GameState;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArenaSessionListener implements Listener {

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
                if(meta.getDisplayName().equals("§8» §3§lSELECT KIT §8«")) {
                    ((ArenaSession) user.getSession()).setState(GameState.IN_GAME);
                } else if(meta.getDisplayName().equals("§4§l☓ §c§lQUIT §4§l☓")) {
                    user.setSession(new LobbySession(user.getUserId()));
                    if(user.isSpectating()) user.setSpectating(false);
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

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        WarriorUser user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        ArenaSession session = (ArenaSession) user.getSession();
        if(session.getState() == GameState.SPECTATING) ((ArenaSession) user.getSession()).setState(GameState.PRE_GAME);
        if(session.getState() == GameState.PRE_GAME) {
            ((ArenaSession) user.getSession()).setState(GameState.PRE_GAME);
            e.setCancelled(true);
            e.getDrops().clear();
        }
        if(session.getState() == GameState.IN_GAME) {
            e.setCancelled(true);
            ((ArenaSession) user.getSession()).setState(GameState.SPECTATING);
            user.setSpectating(true);
            user.getBukkitPlayer().setVelocity(user.getBukkitPlayer().getVelocity().add(new Vector(0, 1, 0)));
            long ticks = 60L;
            new BukkitRunnable() {

                int totalTime = (int) ticks / 20;

                @Override
                public void run() {
                    if(this.totalTime == 0) {
                        ((ArenaSession) user.getSession()).setState(GameState.PRE_GAME);
                        user.setSpectating(false);
                        cancel();
                    } else {
                        user.getBukkitPlayer().sendTitle("§4§lYOU DIED", "§7Respawning in " +totalTime+ " seconds", 0, 25, 0);
                        user.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 0.5f, 1f);
                    }

                    totalTime--;
                }
            }.runTaskTimer(Warrior.getInstance(), 2L, 20L);
        }
    }

}
