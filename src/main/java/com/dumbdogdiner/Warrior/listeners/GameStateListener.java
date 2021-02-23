package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.events.GameStateChangeEvent;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class GameStateListener implements Listener {

    @EventHandler
    public void onStateChange(GameStateChangeEvent e) {
        if(e.getContext() instanceof ArenaSession) {
            Player p = e.getPlayer();
            switch(e.getToState()) {
                case PRE_GAME:
                case SPECTATING:
                    ((ArenaSession)e.getContext()).setInventory();
                    p.teleport(((ArenaSession)e.getContext()).getArena().getSpawn());
                    break;
                case IN_GAME:
                    p.getInventory().clear();
                    p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                    p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                    p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                    p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                    p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
            }
        }
    }

}
