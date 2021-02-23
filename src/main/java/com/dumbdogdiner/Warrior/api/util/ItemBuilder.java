package com.dumbdogdiner.Warrior.api.util;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemBuilder {

    Material material;
    ItemStack item;
    ItemMeta meta;
    int amount;

    public ItemBuilder(Material material) {
        this.material = material;
        this.amount = 1;
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
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
        meta.setDisplayName(TranslationUtil.translateColor(name));

        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.stream(lore)
                .map(TranslationUtil::translateColor)
                .collect(Collectors.toList()));

        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
