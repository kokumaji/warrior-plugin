package com.dumbdogdiner.warrior.nms.objects;

import com.dumbdogdiner.warrior.api.util.NMSUtil;

public class MinecraftKey {
    public static final Class<?> MINECRAFTKEY_CLASS = NMSUtil.getNMSClass("MinecraftKey");

    private final Object key;

    public MinecraftKey(Object key) {
        this.key = key;
    }

    public String toString() {
        return this.key.toString();
    }
}
