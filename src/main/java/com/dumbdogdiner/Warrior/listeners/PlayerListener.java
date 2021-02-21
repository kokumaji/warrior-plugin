package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.Region;
import com.dumbdogdiner.Warrior.managers.ArenaManager;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ() || e.getFrom().getBlockY() != e.getTo().getBlockY()) {
            if(ArenaManager.getSessions().get(e.getPlayer().getUniqueId()) != null) {
                Player p = e.getPlayer();
                Arena a = ArenaManager.getSessionArena(p);

                Region r = a.getBounds();

                if(!r.contains(e.getTo())) {
                    Vector playerVec = p.getLocation().toVector();
                    Vector center = r.center();

                    p.setVelocity(center.subtract(playerVec).normalize());
                    p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 0.35f, 1f);
                }

            }
        }

    }
}
