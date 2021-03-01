package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.gameflags.FlagContainer;
import com.dumbdogdiner.Warrior.api.arena.gameflags.implementation.MaxHealthFlag;
import com.dumbdogdiner.Warrior.api.events.ArenaJoinEvent;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {

    @EventHandler
    public void onArenaJoin(ArenaJoinEvent e) {
        Arena a = e.getArena();
        FlagContainer flags = a.getFlags();
        MaxHealthFlag maxHealth = flags.getFlag(MaxHealthFlag.class);

        if(maxHealth != null) e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth.getValue());
    }
}
