package com.dumbdogdiner.warrior.api.nms;

import com.dumbdogdiner.warrior.api.nms.networking.Protocol;
import lombok.Getter;

public class PacketType {

    public static class Server extends PacketType {

        public Server(Protocol protocol, int packetId, String packetName) {
            super(protocol, packetId, "PacketPlayOut" + packetName);
        }

        public static final PacketType ENTITY_DESTROY = new PacketType.Server(Protocol.PLAY, 0x36, "EntityDestroy");
        public static final PacketType SPAWN_ENTITY = new PacketType.Server(Protocol.PLAY, 0x00, "SpawnEntity");
        public static final PacketType ENTITY_METADATA = new PacketType.Server(Protocol.PLAY, 0x44, "EntityMetadata");
        // ...
        public static final PacketType NAMED_SOUND = new PacketType.Server(Protocol.PLAY, 0x18, "NamedSoundEffect");

    }


    @Getter
    private final String name;

    @Getter
    private final int packetId;

    @Getter
    private final Protocol protocol;

    public PacketType(Protocol protocol, int packetId, String packetName) {
        this.name = packetName;
        this.packetId = packetId;
        this.protocol = protocol;
    }

}
