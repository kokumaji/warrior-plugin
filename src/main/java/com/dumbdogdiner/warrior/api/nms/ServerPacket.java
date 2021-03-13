package com.dumbdogdiner.warrior.api.nms;

import com.dumbdogdiner.warrior.api.util.NMSUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

public class ServerPacket extends Packet {

    @Getter @Setter
    private boolean cancelled;

    public ServerPacket(Object packet) {
        super(packet);
    }

    public ByteBuf readBytes() {
        ByteBuf buf = Unpooled.buffer(0);
        try {
            Class<?> packetSerializerClass = NMSUtil.getNMSClass("PacketDataSerializer");
            Object packetSerializer = packetSerializerClass.getDeclaredConstructor(ByteBuf.class)
                                        .newInstance(Unpooled.buffer(0));
            this.getHandle().getClass().getMethod("b", packetSerializerClass).invoke(this.getHandle(), packetSerializer);
            buf = (ByteBuf) packetSerializerClass.getMethod("copy").invoke(packetSerializer);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return buf;
    }

}
