package com.dumbdogdiner.warrior.listeners;


import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

            /*NMSUtil.injectPlayer(new PacketListener() {

                // SERVER -> CLIENT
                @Override
                public void onSend(ChannelHandlerContext ctx, ServerPacket packet, ChannelPromise promise) {

                }

                // CLIENT -> SERVER
                @Override
                public void onReceive(ChannelHandlerContext ctx, ClientPacket packet) {

                }

            }, user, Warrior.getInstance());*/
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }
}
