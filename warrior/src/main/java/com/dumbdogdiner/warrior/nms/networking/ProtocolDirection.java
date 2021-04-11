package com.dumbdogdiner.warrior.nms.networking;

import com.dumbdogdiner.warrior.api.reflection.EnumUtil;
import com.dumbdogdiner.warrior.util.NMSUtil;

public enum ProtocolDirection {

    /**
     * SERVER -> CLIENT
     */
    CLIENTBOUND,

    /**
     * CLIENT -> SERVER
     */
    SERVERBOUND;

    private static final Class<?> ENUMPROTOCOL_DIR_CLASS = NMSUtil.getNMSClass("EnumProtocolDirection");

    public static Object toNMS(ProtocolDirection direction) {
        return EnumUtil.asObject(ENUMPROTOCOL_DIR_CLASS, direction.name());
    }
}
