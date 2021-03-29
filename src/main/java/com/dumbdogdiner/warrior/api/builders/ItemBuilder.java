package com.dumbdogdiner.warrior.api.builders;

import com.dumbdogdiner.warrior.api.util.HeadTexture;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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

    public ItemBuilder(ItemStack stack) {
        this.item = stack;
        this.material = stack.getType();
        this.amount = stack.getAmount();
        this.meta = stack.getItemMeta();
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


    public ItemBuilder appendLore(String... strings) {
        List<String> existing = meta.getLore();
        assert existing != null;

        existing.addAll(Arrays.asList(strings));

        meta.setLore(existing);
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

    public ItemBuilder setTexture(HeadTexture texture) {
        if(material.equals(Material.PLAYER_HEAD)) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);

            profile.getProperties().put("textures", new Property("textures", texture.getTexture()));

            try {
                Field profileField = ((SkullMeta)meta).getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set((SkullMeta)meta, profile);

            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
                error.printStackTrace();
            }

        }

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
            Class<?> craftItem = NMSUtil.getCraftClass("inventory.CraftItemStack");
            ItemStack item = build();

            nmsCopy = Objects.requireNonNull(craftItem).getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return nmsCopy;
    }

}
