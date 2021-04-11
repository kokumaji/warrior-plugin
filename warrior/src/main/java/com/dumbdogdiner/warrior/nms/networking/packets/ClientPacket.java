package com.dumbdogdiner.warrior.nms.networking.packets;

import com.dumbdogdiner.warrior.nms.networking.ProtocolDirection;

public class ClientPacket extends Packet {

    public ClientPacket(Object packet) {
        super(packet);
    }

    public PacketRaw getPacketRaw() {
        return new PacketRaw(this.getPacketId(ProtocolDirection.SERVERBOUND), PacketRaw.readPacket(this.getHandle()));
    }

    public Integer getId() {
        return this.getPacketId(ProtocolDirection.SERVERBOUND);
    }

}

