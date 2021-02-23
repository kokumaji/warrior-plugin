package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.events.KillStreakChangeEvent;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.GameState;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.gui.KitGUI;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.managers.GUIManager;
import com.dumbdogdiner.Warrior.managers.KitManager;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArenaSessionListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(EntityPickupItemEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        Player p = ((Player) e.getEntity()).getPlayer();
        if(ArenaManager.isPlaying(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if(ArenaManager.isPlaying(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getItem() == null) return;

        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            WarriorUser user = PlayerManager.get(p.getUniqueId());
            ItemMeta meta = e.getItem().getItemMeta();

            if(((ArenaSession)user.getSession()).getState() == GameState.PRE_GAME) {
                if(meta.getDisplayName().equals("§8» §3§lSELECT KIT §8«")) {
                    KitGUI gui = GUIManager.get(KitGUI.class);
                    gui.open(p);
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
            } else if(((ArenaSession)user.getSession()).getState() == GameState.IN_GAME) {
                if(meta.getDisplayName().equals("§8» §3§lSPECIAL ABILITY §8«")) {
                    ((ArenaSession)user.getSession()).getKit().executeSpecial(p);
                }
            } else if(((ArenaSession)user.getSession()).getState() == GameState.SPECTATING) {
                if(meta.getDisplayName().equals("§4§l☓ §c§lQUIT §4§l☓")) {
                    user.setSession(new LobbySession(user.getUserId()));
                    if(user.isSpectating()) user.setSpectating(false);
                } else {
                    p.sendActionBar("§4§lFeature Not Implemented!");
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f);
                }
            }
        }
    }

    @EventHandler
    public void onDurability(PlayerItemDamageEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        e.setCancelled(true);
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
            user.addDeath();

            new BukkitRunnable() {

                int totalTime = 3;

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

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        if(e.getEntity().getKiller() == null) return;
        Player killer = e.getEntity().getKiller();

        WarriorUser killerUser = PlayerManager.get(killer.getUniqueId());
        ((ArenaSession)killerUser.getSession()).addKill();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;

        WarriorUser victim = PlayerManager.get(e.getEntity().getUniqueId());
        WarriorUser attacker = PlayerManager.get(e.getDamager().getUniqueId());

        if(victim == null || attacker == null) return;

        if(((ArenaSession)victim.getSession()).getState() != GameState.IN_GAME) e.setCancelled(true);
        if(((ArenaSession)attacker.getSession()).getState() != GameState.IN_GAME) e.setCancelled(true);

    }

    @EventHandler
    public void onKillStreak(KillStreakChangeEvent e) {

    }

}
