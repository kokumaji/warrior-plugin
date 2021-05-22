package com.dumbdogdiner.warrior.api.kit;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Ability {

    public static String ACTIVE_ABILITY_STRING = "§8» §3§lSPECIAL ABILITY §8«";
    public static String DEACTIVATED_ABILITY_STRING = "§8» §7§lSPECIAL ABILITY §8«";

    @Getter
    private final String name;

    @Getter
    private final int cost;

    @Getter
    private final int minStreak;

    @Accessors(fluent = true) @Getter
    private final boolean availableOnStart;

    @Getter
    private final String[] description;

    public Ability(String name, int cost, int minStreak, boolean availableOnStart, String... desc) {
        this.name = name;
        this.cost = cost;
        this.minStreak = minStreak;
        this.availableOnStart = availableOnStart;
        this.description = desc;

    }

    public abstract Runnable run(WarriorUser user);

    public void canExecute(WarriorUser user, boolean value) {
        if(value) {
            ((ArenaSession)user.getSession()).canUseAbility(true);
            ItemStack item = user.getBukkitPlayer().getInventory().getItem(8);
            if(item == null) return;

            item.setType(Material.MAGMA_CREAM);

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ACTIVE_ABILITY_STRING);
            item.setItemMeta(meta);

            user.getBukkitPlayer().getInventory().setItem(8, item);

        } else {
            ((ArenaSession)user.getSession()).canUseAbility(false);
            ItemStack item = user.getBukkitPlayer().getInventory().getItem(8);
            if(item == null) return;

            item.setType(Material.FIREWORK_STAR);

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(DEACTIVATED_ABILITY_STRING);
            item.setItemMeta(meta);

            user.getBukkitPlayer().getInventory().setItem(8, item);
        }
    }

}
