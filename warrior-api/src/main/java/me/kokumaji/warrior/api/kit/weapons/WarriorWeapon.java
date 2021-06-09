package me.kokumaji.warrior.api.kit.weapons;

<<<<<<< Updated upstream:warrior-api/src/main/java/com/dumbdogdiner/warrior/api/kit/weapons/WarriorWeapon.java
import com.dumbdogdiner.stickyapi.common.util.MathUtil;
import com.dumbdogdiner.warrior.api.kit.Kit;
import com.dumbdogdiner.warrior.api.translation.Color;
=======
import me.kokumaji.warrior.api.translation.Color;
>>>>>>> Stashed changes:warrior-api/src/main/java/me/kokumaji/warrior/api/kit/weapons/WarriorWeapon.java
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    public WarriorWeapon(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public WarriorWeapon itemName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Color.translateColor(name));
        itemStack.setItemMeta(meta);

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

        for(String s : strings) {
            // cutting strings at 32nd char to avoid ugly tooltips
            itemLore.add(Color.translateColor("&7" + s.substring(0, Math.min(s.length(), 32))));
        }

        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(itemLore);
        itemStack.setItemMeta(meta);

        return this;
    }

    public WarriorWeapon hideFlags() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);

        itemStack.setItemMeta(meta);

        return this;

    }

    // TODO: Add "SweepAttack" stuff here

    public ItemStack build() {
        if(itemLore.isEmpty()) itemLore.add(" ");
<<<<<<< Updated upstream:warrior-api/src/main/java/com/dumbdogdiner/warrior/api/kit/weapons/WarriorWeapon.java
        String formattedDamage = String.format("&2+%.2f Damage", weaponDamage);

        itemLore.add(" ");
        itemLore.add(Color.translateColor(formattedDamage));
=======
>>>>>>> Stashed changes:warrior-api/src/main/java/me/kokumaji/warrior/api/kit/weapons/WarriorWeapon.java

        applyLore(itemLore.toArray(String[]::new));

        return itemStack;

    }

}
