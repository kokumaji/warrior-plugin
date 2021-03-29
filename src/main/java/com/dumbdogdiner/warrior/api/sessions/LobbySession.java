package com.dumbdogdiner.warrior.api.sessions;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class LobbySession extends Session {

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

    public LobbySession(UUID userId) {
        super(userId);
        this.setupInventory(Preconditions.checkNotNull(Bukkit.getPlayer(userId), "Player cannot be null!"));
    }

    @Override
    public void setupInventory(Player player) {
        player.getInventory().clear();

        player.getInventory().setItem(0, ARENA_ITEM);
        player.getInventory().setItem(1, SHOP_ITEM);
        player.getInventory().setItem(4, KITS_ITEM);
        player.getInventory().setItem(8, EXIT_ITEM);

        WarriorUser user = PlayerManager.get(player.getUniqueId());
        new BukkitRunnable() {

            @Override
            public void run() {
                user.removeEffects();
            }

        }.runTask(Warrior.getInstance());
    }

    @Override
    public SessionType getType() {
        return SessionType.LOBBY;
    }

}
