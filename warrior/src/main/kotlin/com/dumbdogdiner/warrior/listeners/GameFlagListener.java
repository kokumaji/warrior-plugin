package com.dumbdogdiner.warrior.listeners;

import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.arena.gameflags.FlagContainer;
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.BlockBreakFlag;
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.BlockPlaceFlag;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.user.UserCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class GameFlagListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        User user = UserCache.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        Arena a = ((ArenaSession)user.getSession()).getArena();

        FlagContainer container = a.getFlags();

        BlockBreakFlag breakFlag = container.getFlag(BlockBreakFlag.class);
        if(breakFlag == null) return;

        e.setCancelled(!breakFlag.getValue());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        User user = UserCache.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        Arena a = ((ArenaSession)user.getSession()).getArena();

        FlagContainer container = a.getFlags();

        BlockPlaceFlag breakFlag = container.getFlag(BlockPlaceFlag.class);
        if(breakFlag == null) return;

        e.setCancelled(!breakFlag.getValue());
    }


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
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
    public void onDurability(PlayerItemDamageEvent e) {
        User user = UserCache.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        e.setCancelled(true);
    }


}
