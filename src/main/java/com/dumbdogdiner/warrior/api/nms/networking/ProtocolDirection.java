package com.dumbdogdiner.warrior.api.nms.networking;

import com.dumbdogdiner.warrior.api.reflection.EnumUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;

public enum ProtocolDirection {

    /**
     * Packet is sent to server.
     */
    CLIENTBOUND,

    /**
     * Packet is sent to client.
     */
    SERVERBOUND;

    private static final Class<?> ENUMPROTOCOL_DIR_CLASS = NMSUtil.getNMSClass("EnumProtocolDirection");

    public static Object toNMS(ProtocolDirection direction) {
        return EnumUtil.asObject(ENUMPROTOCOL_DIR_CLASS, direction.name());
    }
}
