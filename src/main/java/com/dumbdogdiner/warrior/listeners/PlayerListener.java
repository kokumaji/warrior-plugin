package com.dumbdogdiner.warrior.listeners;


import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if(!PlayerManager.contains(uuid)) {
            WarriorUser user = PlayerManager.addUser(uuid);

            user.setSession(new LobbySession(user.getUserId()));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }
}
