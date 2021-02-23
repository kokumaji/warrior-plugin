package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.events.GameStateChangeEvent;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStateListener implements Listener {

    @EventHandler
    public void onStateChange(GameStateChangeEvent e) {
        if(e.getContext() instanceof ArenaSession) {
            Player p = e.getPlayer();
            switch(e.getToState()) {
                case PRE_GAME:
                    ((ArenaSession)e.getContext()).setInventory();
                    break;
                case SPECTATING:
                    p.getInventory().clear();
                    break;
                case IN_GAME:
                    // GIVE KIT HERE???
            }
        }
    }

}
