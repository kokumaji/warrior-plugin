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
    VILLAGER_WITH_CAP("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThiOGM2YTQ2ZDg3Y2Y4NmE1NWRmMjE0Y2Y4NGJmNDVjY2EyNWVkYjlhNjc2ZTk2MzY0ZGQ2YTZlZWEyMzViMyJ9fX0="),

    RAINBOW_FLAG("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjFjOGM1ODZiNGI5NzYxZDhiNzhmMTZlYjU3MzU4MzM3NGU1MmYxYTI4NzM1NzY3NGUzMWZkMmE4MzgyIn19fQ=="),
    LESBIAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDUyMWNlMjRjMWE3OTdlYjkzNWFmYzNlYzVmMTk4MjhkZmY2NWNhNDYyMmY5NDYxZmQwOTVlOWFkOTVmZDdiNiJ9fX0="),
    BISEXUAL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxNzhiMmNhOWJhZjUxMDc3YzY4NTcyNGUyNTIwODAzYWUzNmM0NDg0N2Q3NWIxODE4MTJkYWU2OTc5YWJlMSJ9fX0="),
    TRANSGENDER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIxOTVlMjljNzUwNTcyYTUyZWY2NmY2OWNlYmE3MGQ3MWEzODZlNjllZjIwYzBjNmVhYjZhMWRjNmZkMDZlYSJ9fX0="),

    POLYSEXUAL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk0ZDRhMDNlNzI3YTBiNDRhMmYzZmM2N2Q5YmNkODlhNDRkZDg5Y2NlODVhZWIyNzVmMzZkZjExMjMwZTgyIn19fQ=="),
    PANSEXUAL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGQyMjkxYmI3YTcxYzhiZWQyMzBjMjRkYzM4YmRlODk1ZDAxYzY4NDJlMzFhODQxOWY3MWVhMjE3MDkxN2VlYiJ9fX0="),

    ASEXUAL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTk3MjcwNTZhMzU4MmQwMmVmZWE2ZjlmOTliZGVhMTY1YTdlN2ZiZjQyMjRiZDBmOWM5MWJiOTUzMGRjZWEzMiJ9fX0="),
    AROMANTIC("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzVjOTEyZGVhZjAzMzhlZmYyZjMxNzEyZjQwMzRjOTQ0MjAwZjY1OTE2ZTRmNGMzMjZlMGUxMGI3ZTFhZGQxYSJ9fX0="),
    AGENDER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGVmOWM4NGE1ZDA1MDJiMjcxNzNkMmJmNGFmNWUxZThjYjAzNzBmMjhkZDg2Zjg4NmM3NjFjYTRiODY4YzliZSJ9fX0="),

    NON_BINARY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTA2NjM0ZDZjOTBjNWQzMTZjNWVkZjRhZGY0N2FhNjNiMDVhYzU1YzQyMjQzNGI3NDlkNGQ1ODc0ZjYwNmU3ZCJ9fX0="),
    BIGENDER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFkMTI1NmExMjA5ZGM1ZTA1ODJmYzBhMjBmN2RhZjhjODg3OTJlN2FiODZiYmY5ODFjZDZmMDRjOGViNjE3ZiJ9fX0="),

    GENDER_QUEER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE2YjBlODljM2I0NTNmOTE1MDU5ZDg4YmI4ZTRlZGU1OTZkZWVjYmNmNTc1YjkzNzNkYzYxNmU0ZGU4NGQyOCJ9fX0="),
    GENDER_FLUID("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFhZDliNDA0ZDY2YWM1MzRiMWM1NTQ0MDkxOTg0ZjBjM2Q1NzUyYTk1NzhkMGE3Y2EyNzk4Mzg0YzdkZDVhMSJ9fX0="),

    DEMIGIRL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzE2ZDk2YjBjOTQ2NzFiNzExN2Y0ZjMzNDZjM2Q2ODVmNzcyNTc5NjBmN2Y5ZGFmNTM5NDg1NjNmYTMzYmQyMCJ9fX0="),
    DEMIBOY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzcyODljODJjZWY4M2Q2YmI3Y2I5YWU3ZTFjNGEyMDIyZjhiMmQ0ZGUwYWJiNjkxNmY4OGQwODYwOTdlZjBmIn19fQ=="),
    DEMIGENDER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTk5MTc4MDM1OTc2MTA1NjFjNTBjZjI3OGFiOWY0Y2I2M2ZmZjMyYjFlODE5YzI3Yjk5ZGVkNDcwMDRjMDhhMSJ9fX0="),
    DEMIANDROGYNE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmYyODY1ZjU5Y2Y5ODgxZjMxYzBjYzNhOWNhNjZmNDQyZTVhZjI2ZjA5NGVkNDNjOTE1Y2MwZTFkMTYyMWFmMSJ9fX0=");

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
