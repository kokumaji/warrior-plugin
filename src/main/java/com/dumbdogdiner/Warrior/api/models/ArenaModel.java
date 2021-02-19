package com.dumbdogdiner.Warrior.api.models;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.Region;
import lombok.Getter;
import org.bukkit.Location;

public class ArenaModel {

    @Getter
    private LocationModel spawn;

    @Getter
    private String name;

    @Getter
    private Region bounds;

    @Getter
    private boolean enabled;

    public ArenaModel(LocationModel spawn, String name, Region bounds, boolean enabled) {
        this.spawn = spawn;
        this.name = name;
        this.bounds = bounds;
        this.enabled = enabled;
    }


    public ArenaModel(Arena arena) {
        Location loc = arena.getSpawn();

        this.spawn = new LocationModel(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getYaw(), loc.getWorld().getName());
        this.name = arena.getName();
        this.bounds = arena.getBounds();
        this.enabled = arena.isEnabled();
    }
}
