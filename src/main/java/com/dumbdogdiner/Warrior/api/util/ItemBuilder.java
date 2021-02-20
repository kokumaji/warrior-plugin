package com.dumbdogdiner.Warrior.api.util;

import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    Material material;
    ItemStack item;
    int amount;

    public ItemBuilder(Material material) {
        this.material = material;
        this.amount = 1;
        this.item = new ItemStack(material);
        item.setAmount(amount);
    }

    public Item asEntity() {
        return (Item) item;
    }

    public ItemMeta getItemMeta() {
        return item.getItemMeta();
    }

    public ItemBuilder setItemMeta(ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int i) {
        this.amount = i;
        this.item.setAmount(i);

        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(TranslationUtil.translateColor(name));

        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(Arrays.asList(lore));

        return this;
    }

    public ItemStack build() {
        return item;
    }

}
