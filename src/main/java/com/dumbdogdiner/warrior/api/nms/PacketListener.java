package com.dumbdogdiner.warrior.api.nms;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface PacketListener {

    void onSend(ChannelHandlerContext context, ServerPacket packet, ChannelPromise promise);

    void onReceive(ChannelHandlerContext context, Packet packet);

}
