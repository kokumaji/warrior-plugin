package com.dumbdogdiner.warrior.listeners;

import com.dumbdogdiner.warrior.api.events.GameStateChangeEvent;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;

import org.bukkit.GameMode;
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
                    p.teleport(((ArenaSession)e.getContext()).getArena().getSpawn());
                    p.setFireTicks(0);
                    p.setGameMode(GameMode.ADVENTURE);
                    p.setFlying(false);
                    // fallthrough idk why
                case SPECTATING:
                    e.getContext().setupInventory(p); // might not be necessary?
                    p.teleport(((ArenaSession)e.getContext()).getArena().getSpawn());
                    break;
                case IN_GAME:
                    // do something in-game related??
            }
        }
    }

}
