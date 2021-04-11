package com.dumbdogdiner.warrior.nms.networking.packets;

import com.dumbdogdiner.warrior.nms.PacketType;
import com.dumbdogdiner.warrior.api.reflection.ReflectionUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

import java.lang.reflect.Method;

public class PacketRaw {

    @Getter private int id;
    @Getter private byte[] bytes;

    /**
     * Constructs a new PacketRaw object
     *
     * @param type type of the packet
     * @param data byte array of the packet
     */
    public PacketRaw(PacketType type, byte[] data) {
        this.id = type.getPacketId();
        this.bytes = data;
    }

    public String getHexId() {
        return String.format("0x%02X", id);
    }

    /**
     * Constructs a new PacketRaw object
     * @param id id of the packet
     * @param data byte array of the packet
     */
    public PacketRaw(int id, byte[] data) {
        this.id = id;
        this.bytes = data;
    }

    public int length() {
        return (int) Math.floor(Math.log10(id) + 1) + bytes.length;
    }

    public void writeId(ByteBuf output) {
        writeVarInt(output, id);
    }

    public void writeBytes(ByteBuf output) {
        output.writeBytes(bytes);
    }

    public void writeContents(ByteBuf output) {
        writeId(output);
        writeBytes(output);
    }

    public static byte[] getBytes(ByteBuf input) {
        byte[] bytes = new byte[input.readableBytes()];
        input.readBytes(bytes);

        return bytes;
    }

    public static byte[] readPacket(Object packet) {
        if(!ReflectionUtil.classImplements(packet.getClass(), NMSUtil.NMS_PACKET_CLASS))
            throw new IllegalArgumentException("packet must be a minecraft packet");

        ByteBuf buffer = Unpooled.buffer(0);
        Method writeData = Packet.getWriteMethod();

        try {
            Object packetSerializer = NMSUtil.getNMSClass("PacketDataSerializer")
                                        .getDeclaredConstructor(ByteBuf.class).newInstance(buffer);
            writeData.invoke(packet, packetSerializer);

            buffer = (ByteBuf) packetSerializer.getClass().getMethod("copy").invoke(packetSerializer);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[0];

        if(buffer.readableBytes() > 0)
            bytes = getBytes(buffer);

        return bytes;
    }

    public int readVarInt(ByteBuf input) {
        int i = 0;
        int j = 0;
        byte b0;
        do {
            b0 = input.readByte();
            i |= (b0 & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((b0 & 0x80) == 0x80);
        return i;
    }

    public void writeVarInt(ByteBuf output, int i) {

        while ((i & 0xFFFFFF80) != 0x0) {
            output.writeByte((i & 0x7F) | 0x80);
            i >>>= 7;
        }

        output.writeByte(i);
    }
}
