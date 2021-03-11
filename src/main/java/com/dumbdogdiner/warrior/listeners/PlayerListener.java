package com.dumbdogdiner.warrior.listeners;


import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.nms.objects.SoundEffect;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import com.dumbdogdiner.warrior.api.nms.Packet;
import com.dumbdogdiner.warrior.api.nms.PacketListener;
import com.dumbdogdiner.warrior.api.nms.ServerPacket;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.List;
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
                    // nothing for now...
                }

                @Override
                public void onSend(ChannelHandlerContext ctx, ServerPacket packet, ChannelPromise promise) {
                    if(packet.getName().equalsIgnoreCase("PacketPlayOutNamedSoundEffect")) {
                        Sound sound = packet.getSound().toBukkit();

                        // before we're about to send a ENTITY_PLAYER_HURT sound,
                        // replace it with something else! doesn't change for the
                        // "source" of that sound since ENTITY_PLAYER_HURT is also
                        // played client-side (workarounds???)
                        if(sound.equals(Sound.ENTITY_PLAYER_HURT)) {
                            packet.setSound(Sound.ENTITY_FOX_HURT);
                        }
                    }
                }

            }, user, Warrior.getInstance());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }
}
