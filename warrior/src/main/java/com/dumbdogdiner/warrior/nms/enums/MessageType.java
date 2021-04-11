package com.dumbdogdiner.warrior.nms.enums;

import com.dumbdogdiner.warrior.util.NMSUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public enum MessageType {
    CHAT,
    SYSTEM,
    GAME_INFO;

    public static Object toNMS(MessageType type) {
        Object nmsType = null;
        try {
            nmsType = NMSUtil.getNMSClass("ChatMessageType")
                    .getMethod("valueOf", String.class)
                    .invoke(null, type.name());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(nmsType);

    }

}
