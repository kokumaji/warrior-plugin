package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.events.SessionChangeEvent;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.dumbdogdiner.Warrior.managers.LobbyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class SessionChangeListener implements Listener {

    private static final ItemStack KITS_ITEM = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                                            .setName("&8» &3&lVIEW KITS &8«")
                                            .setLore("&7View Your Available Kits")
                                            .build();

    private static final ItemStack SHOP_ITEM = new ItemBuilder(Material.ENDER_CHEST)
                                            .setName("&8» &3&lSHOP &8«")
                                            .setLore("&7Browse & Unlock new Kits!")
                                            .build();

    private static final ItemStack ARENA_ITEM = new ItemBuilder(Material.MAP)
                                            .setName("&8» &3&lARENAS &8«")
                                            .setLore("&7Select an Arena to Join")
                                            .build();
    private static final ItemStack EXIT_ITEM = new ItemBuilder(Material.BARRIER)
                                            .setName("&4&l☓ &c&lQUIT &4&l☓")
                                            .setLore("&7Return to Lobby")
                                            .build();

    @EventHandler
    public void onSessionChange(SessionChangeEvent e) {
        if(e.getNext() instanceof LobbySession) {
            Player p = e.getPlayer();
            p.sendMessage("Returning to Lobby...");
            p.getInventory().clear();

            ItemStack stats = new ItemBuilder(Material.PLAYER_HEAD)
                    .setName("&8» &3&lSTATS &8«")
                    .setLore("&7View Your Warrior Stats")
                    .setOwner(p.getName())
                    .build();

            p.getInventory().setItem(0, ARENA_ITEM);
            p.getInventory().setItem(1, SHOP_ITEM);
            p.getInventory().setItem(4, KITS_ITEM);
            p.getInventory().setItem(7, stats);
            p.getInventory().setItem(8, EXIT_ITEM);

            p.teleport(LobbyManager.getLobbySpawn());
            p.getInventory().setHeldItemSlot(4);

        } else if(e.getNext() instanceof ArenaSession) {
            e.getPlayer().sendMessage("Teleporting to Arena...");
        }
    }

}
