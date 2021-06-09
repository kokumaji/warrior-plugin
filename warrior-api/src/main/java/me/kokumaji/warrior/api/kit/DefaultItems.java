package com.dumbdogdiner.warrior.api.kit;

import com.dumbdogdiner.warrior.api.kit.weapons.WarriorWeapon;
import com.dumbdogdiner.warrior.api.translation.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DefaultItems {

    public static ItemStack getAbilityItem(WarriorAbility ability) {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = item.getItemMeta();
        String itemName = String.format("&8··· &7&lABILITY &f(%s) &8···", ability.getName());

        meta.setDisplayName(Color.translateColor(itemName));
        item.setItemMeta(meta);

        return item;
    }

}
