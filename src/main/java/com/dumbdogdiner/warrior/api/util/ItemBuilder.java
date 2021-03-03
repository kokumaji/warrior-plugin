package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.stickyapi.bukkit.nms.BukkitHandler;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemBuilder {

    Material material;
    ItemStack item;
    ItemMeta meta;
    int amount;

    boolean glow;

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

    public ItemBuilder setOwner(String owner) {
        if(item.getType().equals(Material.PLAYER_HEAD)) {
            ((SkullMeta)meta).setOwner(owner);
        }

        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.stream(lore)
                .map(TranslationUtil::translateColor)
                .collect(Collectors.toList()));

        return this;
    }

    public ItemBuilder makeGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        if(glow) {
            item.addUnsafeEnchantment(Enchantment.LUCK, 1);
            ItemMeta m = item.getItemMeta();
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(m);
        }
        return item;
    }

    public Object asNMSCopy() {
        Object nmsCopy = null;
        try {
            Class<?> craftItem = BukkitHandler.getCraftClass("inventory.CraftItemStack");
            ItemStack item = build();

            nmsCopy = craftItem.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return nmsCopy;
    }

}
