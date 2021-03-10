package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Enum Class for internally used
 * Head Textures
 */
public enum HeadTexture {

    MHF_QUESTION("ewogICJ0aW1lc3RhbXAiIDogMTYxNTIxODA1MjI2MSwKICAicHJvZmlsZUlkIiA6ICI2MDZlMmZmMGVkNzc0ODQyOWQ2Y2UxZDMzMjFjNzgzOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfUXVlc3Rpb24iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM0ZTA2M2NhZmI0NjdhNWM4ZGU0M2VjNzg2MTkzOTlmMzY5ZjRhNTI0MzRkYTgwMTdhOTgzY2RkOTI1MTZhMCIKICAgIH0KICB9Cn0="),
    VILLAGER_WITH_CAP("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThiOGM2YTQ2ZDg3Y2Y4NmE1NWRmMjE0Y2Y4NGJmNDVjY2EyNWVkYjlhNjc2ZTk2MzY0ZGQ2YTZlZWEyMzViMyJ9fX0=");

    @Getter
    private final String texture;
    HeadTexture(String s) {
        this.texture = s;
    }

    /**
     * Returns an {@link ItemStack} with the given texture applied.
     * This is not compatible with {@link ItemBuilder#ItemBuilder(ItemStack)} use 
     * {@link ItemBuilder#setTexture(HeadTexture)} instead, if you wish to use
     * the ItemBuilder to modify other values as well.
     *
     * @param head A {@link HeadTexture} enum
     * @return ItemStack with given texture applied to it.
     */
    public static ItemStack asItem(HeadTexture head) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", head.texture));

        try {
            Field headTexture = headMeta.getClass().getDeclaredField("profile");
            headTexture.setAccessible(true);
            headTexture.set(headMeta, profile);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return item;

    }
}
