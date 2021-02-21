package com.dumbdogdiner.Warrior.commands;

import com.dumbdogdiner.Warrior.Warrior;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DebugCommand implements CommandExecutor, Listener {

    public DebugCommand() {
        Bukkit.getPluginManager().registerEvents(this, Warrior.getInstance());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @EventHandler
    public void onImpact(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Giant)) return;
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Giant ent = (Giant) e.getEntity();
            Location loc = ent.getLocation();

            loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 200, 5, 2, 5);
            ent.remove();

            loc.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_DEATH, 2f, 1f);

        }
    }
}
