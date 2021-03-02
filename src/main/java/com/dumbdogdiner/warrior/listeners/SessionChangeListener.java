package com.dumbdogdiner.warrior.listeners;

import com.dumbdogdiner.warrior.api.events.SessionChangeEvent;
import com.dumbdogdiner.warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.warrior.managers.LobbyManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SessionChangeListener implements Listener {


    @EventHandler
    public void onSessionChange(SessionChangeEvent e) {
        // might replace all this with debug only stuff
        if(e.getNext() instanceof LobbySession) {
            Player p = e.getPlayer();
            p.teleport(LobbyManager.getLobbySpawn());
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
        } else if(e.getNext() instanceof ArenaSession) {
            //e.getPlayer().sendMessage("Teleporting to Arena...");
        }
    }

}
