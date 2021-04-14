package com.dumbdogdiner.warrior.models;

import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.arena.metadata.ArenaMetadata;
import lombok.Getter;
import org.bukkit.Location;

public class ArenaModel {

    @Getter
    private String name;
    @Getter
    private boolean enabled;

    @Getter
    private LocationModel spawn;
    @Getter
    private RegionModel bounds;

    @Getter
    private ArenaMetadata metadata;

    public ArenaModel(LocationModel spawn, String name, RegionModel bounds, boolean enabled) {
        this.spawn = spawn;
        this.name = name;
        this.bounds = bounds;
        this.enabled = enabled;
        this.metadata = null;
    }

    public ArenaModel(LocationModel spawn, String name, RegionModel bounds, ArenaMetadata metadata, boolean enabled) {
        this.spawn = spawn;
        this.name = name;
        this.bounds = bounds;
        this.enabled = enabled;
        this.metadata = metadata;
    }

    public ArenaModel(Arena arena) {
        Location loc = arena.getSpawn();

        this.spawn = new LocationModel(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getPitch(), loc.getWorld().getName());
        this.name = arena.getName();
        this.bounds = new RegionModel(arena.getBounds().getLoc1(), arena.getBounds().getLoc2(), loc.getWorld().getName());
        this.enabled = arena.isEnabled();
        this.metadata = arena.getMetadata();
    }
}
