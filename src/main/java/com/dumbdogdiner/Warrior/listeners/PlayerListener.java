package com.dumbdogdiner.Warrior.listeners;


import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
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
        if(!PlayerManager.contains(p.getUniqueId())) {
            UUID uuid = p.getUniqueId();
            PlayerManager.addUser(uuid);
            PlayerManager.get(uuid).setSession(new LobbySession(uuid));
            p.setExp(0); // reset xp on join, will be replaced with Warrior™ level
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        if(PlayerManager.contains(e.getPlayer().getUniqueId())) {
            PlayerManager.remove(e.getPlayer().getUniqueId());
        }
    }
}
