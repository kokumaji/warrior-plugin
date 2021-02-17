package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

@Getter @Setter
public class ArenaSession {


    private WarriorUser sessionUser;
    private String arenaName;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private ItemStack[] inventoryCapture;

    private World world;

    private Location pos1;
    private Location pos2;
    private Location spawn;

    public ArenaSession(WarriorUser user, String arenaName) {
        this.sessionUser = user;
        this.arenaName = arenaName;
        this.world = user.getBukkitPlayer().getWorld();
        inventoryCapture = user.getBukkitPlayer().getInventory().getContents();
    }

    protected void restoreInventory() {
        Player player = sessionUser.getBukkitPlayer();
        player.getInventory().clear();
        for(int i = 0; i < inventoryCapture.length; i++) {
            if(inventoryCapture[i] == null) continue;
            player.getInventory().setItem(i, inventoryCapture[i]);
        }
    }

    public void startSession(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Warrior.getInstance());
        giveSessionItems(this.sessionUser.getBukkitPlayer());
    }

    private void giveSessionItems(Player player) {
        player.getInventory().clear();

        ItemStack locationWand = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta lwMeta = locationWand.getItemMeta();
        lwMeta.setDisplayName("§8» §3§lLocation Wand §8«");
        lwMeta.setLore(Arrays.asList("§fRight-Click §7to set Position 1", "§fLeft-Click §7to set Position 2"));
        locationWand.setItemMeta(lwMeta);

        player.getInventory().setItem(1, locationWand);

        ItemStack spawnSet = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta ssMeta = spawnSet.getItemMeta();
        ssMeta.setDisplayName("§8» §3§lSpawn Location §8«");
        ssMeta.setLore(Collections.singletonList("§fRight-Click §7to set Spawn"));
        spawnSet.setItemMeta(ssMeta);

        player.getInventory().setItem(4, spawnSet);

        ItemStack confirmOut = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta coMeta = confirmOut.getItemMeta();
        coMeta.setDisplayName("§8» §3§lConfirm §8«");
        coMeta.setLore(Collections.singletonList("§fRight-Click §7to confirm"));
        confirmOut.setItemMeta(coMeta);

        player.getInventory().setItem(7, confirmOut);

    }
}
