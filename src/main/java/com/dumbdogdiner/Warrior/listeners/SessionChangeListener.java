package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.events.SessionChangeEvent;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.dumbdogdiner.Warrior.managers.LobbyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class SessionChangeListener implements Listener {


    @EventHandler
    public void onSessionChange(SessionChangeEvent e) {
        // might replace all this with debug only stuff
        if(e.getNext() instanceof LobbySession) {
            Player p = e.getPlayer();
            p.teleport(LobbyManager.getLobbySpawn());
        } else if(e.getNext() instanceof ArenaSession) {
            //e.getPlayer().sendMessage("Teleporting to Arena...");
        }
    }

}
