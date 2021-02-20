package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.Region;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if(ArenaManager.getSessions().get(e.getPlayer().getUniqueId()) != null) {
            Player p = e.getPlayer();
            Arena a = ArenaManager.getSessionArena(p);

            Region r = a.getBounds();

            if(!r.contains(e.getFrom())) {
                p.sendMessage("can't leave region");
                Vector playerVec = p.getLocation().toVector();
                Vector center = r.center();

                //push the player back
                p.setVelocity(center.subtract(playerVec).normalize());
            }

        }
    }
}
