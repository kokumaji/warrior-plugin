package com.dumbdogdiner.warrior.api.nms;

import lombok.Getter;

public class PacketType<T> {

    public static class Server extends PacketType<Server> {

        public Server(Protocol protocol, int packetId, String packetName) {
            super(protocol, packetId, "PacketPlayOut" + packetName);
        }

        public static final PacketType<Server> ENTITY_DESTROY = new PacketType.Server(Protocol.PLAY, 0x36, "EntityDestroy");
        // ...
        public static final PacketType<Server> NAMED_SOUND = new PacketType.Server(Protocol.PLAY, 0x18, "NamedSoundEffect");
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
