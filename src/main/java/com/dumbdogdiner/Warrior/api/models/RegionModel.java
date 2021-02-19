package com.dumbdogdiner.Warrior.api.models;

import org.bukkit.util.Vector;

public class RegionModel {

    LocationModel pos1;
    LocationModel pos2;

    String world;

    public RegionModel(Vector pos1, Vector pos2, String world) {
        this.world = world;
        this.pos1 = new LocationModel(pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ());
        this.pos2 = new LocationModel(pos2.getBlockX(), pos2.getBlockY(), pos2.getBlockZ());
    }
}
