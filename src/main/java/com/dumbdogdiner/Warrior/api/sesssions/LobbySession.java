package com.dumbdogdiner.Warrior.api.sesssions;

import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class LobbySession implements Session {

    public static final ItemStack ARENA_ITEM = new ItemBuilder(Material.MAP)
            .setName("&8» &3&lARENAS &8«")
            .setLore("&7Select an Arena to Join")
            .build();
    public static final ItemStack KITS_ITEM = new ItemBuilder(Material.LEATHER_CHESTPLATE)
            .setName("&8» &3&lVIEW KITS &8«")
            .setLore("&7View Your Available Kits")
            .build();

    public static final ItemStack SHOP_ITEM = new ItemBuilder(Material.ENDER_CHEST)
            .setName("&8» &3&lSHOP &8«")
            .setLore("&7Browse & Unlock new Kits!")
            .build();
    public static final ItemStack EXIT_ITEM = new ItemBuilder(Material.BARRIER)
            .setName("&4&l☓ &c&lQUIT &4&l☓")
            .setLore("&7Leave KitPvP")
            .build();

    private final long timestamp;

    public LobbySession(UUID userId) {
        this.timestamp = System.currentTimeMillis();
        this.setupInventory(Preconditions.checkNotNull(Bukkit.getPlayer(userId), "Player cannot be null!"));
    }

    @Override
    public void setupInventory(Player player) {
        player.getInventory().clear();

        player.getInventory().setItem(0, ARENA_ITEM);
        player.getInventory().setItem(1, SHOP_ITEM);
        player.getInventory().setItem(4, KITS_ITEM);
        player.getInventory().setItem(8, EXIT_ITEM);
    }

    @Override
    public SessionType getType() {
        return SessionType.LOBBY;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
