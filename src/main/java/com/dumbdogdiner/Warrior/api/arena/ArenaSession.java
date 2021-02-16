package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;

@Getter @Setter
public class ArenaSession {


    private WarriorUser sessionUser;
    private String arenaName;

    private World world;

    private Location pos1;
    private Location pos2;
    private Location spawn;

    public ArenaSession(WarriorUser user, String arenaName) {
        this.sessionUser = user;
        this.arenaName = arenaName;
        this.world = user.getBukkitPlayer().getWorld();
    }

    public void startSession(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Warrior.getInstance());
    }
}
