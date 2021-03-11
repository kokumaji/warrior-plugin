package com.dumbdogdiner.warrior.api.nms;

import lombok.Getter;
import lombok.Setter;

public class ServerPacket extends Packet {

    @Getter @Setter
    private boolean cancelled;

    public ServerPacket(Object packet) {
        super(packet);
    }

}
