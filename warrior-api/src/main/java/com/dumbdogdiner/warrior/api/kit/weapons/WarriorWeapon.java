package com.dumbdogdiner.warrior.api.kit.weapons;

import com.dumbdogdiner.warrior.api.translation.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Warrior Weapon Builder, targeted towards Kotlin
 */
public class WarriorWeapon {

    private ItemStack itemStack;
    private List<String> itemLore = new ArrayList<>();
    private double weaponDamage;

    public WarriorWeapon(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public WarriorWeapon itemName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Color.translateColor(name));
        itemStack.setItemMeta(meta);

        return this;
    }

    public WarriorWeapon damageOverride(double num) {
        // dummy method for now, daddy koda needs to do some nms magic here
        this.weaponDamage = num;
        return this;
    }

    private void applyLore(String... strings) {
        List<String> finalLore = Arrays.stream(strings).collect(Collectors.toList());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(finalLore);
        itemStack.setItemMeta(meta);
    }

    public WarriorWeapon description(String... strings) {
        itemLore = new ArrayList<>();

        itemLore.add(" ");
        for(String s : strings) {
            // cutting strings at 32nd char to avoid ugly tooltips
            itemLore.add(Color.translateColor("&7" + s.substring(0, 32)));
        }

        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(itemLore);
        itemStack.setItemMeta(meta);

        return this;
    }

    // TODO: Add "SweepAttack" stuff here

    public ItemStack build() {
        if(itemLore.isEmpty()) itemLore.add(" ");
        String formattedDamage = String.format("&a+%f Damage", this.weaponDamage);
        itemLore.add(Color.translateColor(formattedDamage));

        applyLore(itemLore.toArray(String[]::new));

        return itemStack;

    }

}
