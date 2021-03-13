package com.dumbdogdiner.warrior.listeners;


import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.nms.Packet;
import com.dumbdogdiner.warrior.api.nms.PacketListener;
import com.dumbdogdiner.warrior.api.nms.ServerPacket;
import com.dumbdogdiner.warrior.api.nms.networking.ProtocolDirection;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.AttributeKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.InvocationTargetException;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if (!PlayerManager.contains(uuid)) {
            WarriorUser user = PlayerManager.addUser(uuid);
            user.loadData();
            user.setSession(new LobbySession(user.getUserId()));


            NMSUtil.injectPlayer(new PacketListener() {
                @Override
                public void onReceive(ChannelHandlerContext ctx, Packet packet) {
                    // EnumProtocol enumprotocol = (EnumProtocol) channelhandlercontext.channel().attr(NetworkManager.c).get();
                    // Integer integer = enumprotocol.a(this.c, packet);
                    System.out.println(packet.getPacketId(ProtocolDirection.SERVERBOUND));

                }

                @Override
                public void onSend(ChannelHandlerContext ctx, ServerPacket packet, ChannelPromise promise) {

                }

            }, user, Warrior.getInstance());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }
}
