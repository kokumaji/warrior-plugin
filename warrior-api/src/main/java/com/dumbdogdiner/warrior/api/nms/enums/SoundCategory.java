package com.dumbdogdiner.warrior.api.nms.enums;

import com.dumbdogdiner.warrior.api.util.NMSUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public enum SoundCategory {

    MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, HOSTILE, NEUTRAL, PLAYERS, AMBIENT, VOICE;

    public static Object toNMS(SoundCategory category) {
        Object nmsCategory = null;
        try {
            nmsCategory = NMSUtil.getNMSClass("SoundCategory")
                        .getMethod("valueOf", String.class)
                        .invoke(null, category.name());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(nmsCategory);

    }

}
