package com.dumbdogdiner.warrior.nms.networking;

import com.dumbdogdiner.warrior.api.reflection.EnumUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;

public enum Protocol {
    HANDSHAKING,
    STATUS,
    LOGIN,
    PLAY;

    private static final Class<?> ENUMPROTOCOL_CLASS = NMSUtil.getNMSClass("EnumProtocol");

    public static Object toNMS(Protocol protocol) {
        return EnumUtil.asObject(ENUMPROTOCOL_CLASS, protocol.name());
    }
}
