package com.dumbdogdiner.warrior.nms;

import com.dumbdogdiner.warrior.nms.networking.packets.ClientPacket;
import com.dumbdogdiner.warrior.nms.networking.packets.Packet;
import com.dumbdogdiner.warrior.nms.networking.packets.ServerPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface PacketListener {

    /**
     * Fires before a Packet is sent to the Client.
     * All Packets are <b>Clientbound</b>.
     *
     * @param context A {@link ChannelHandlerContext} object.
     * @param packet A {@link ServerPacket} representing the
     *               Minecraft packet.
     * @param promise A {@link ChannelPromise} object.
     */
    void onSend(ChannelHandlerContext context, ServerPacket packet, ChannelPromise promise);

    /**
     * Fires before a Packet is received by the Server.
     * All Packets are <b>Serverbound</b>.
     *
     * @param context A {@link ChannelHandlerContext} object.
     * @param packet A {@link Packet} representing the
     *               Minecraft packet.
     */
    void onReceive(ChannelHandlerContext context, ClientPacket packet);

}
