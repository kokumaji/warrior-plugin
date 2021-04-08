package com.dumbdogdiner.warrior.api.nms.networking.packets;

import com.dumbdogdiner.warrior.api.nms.networking.ProtocolDirection;
import lombok.Getter;
import lombok.Setter;

public class ServerPacket extends Packet {

    @Getter @Setter
    private boolean cancelled;

    public ServerPacket(Object packet) {
        super(packet);
    }

    public PacketRaw getPacketRaw() {
        return new PacketRaw(this.getPacketId(ProtocolDirection.CLIENTBOUND), PacketRaw.readPacket(this.getHandle()));
    }

    public Integer getId() {
        return this.getPacketId(ProtocolDirection.CLIENTBOUND);
    }

}
