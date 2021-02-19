/*
 * Copyright (c) 2021 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information.
 */
package com.dumbdogdiner.Warrior.api.arena;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Region {

    @Getter
    private final transient World world;

    private transient Vector loc1;
    private transient Vector loc2;

    @Getter
    private int minX;
    @Getter
    private int maxX;
    @Getter
    private int minZ;
    @Getter
    private int maxZ;

    public Region(Location loc1, Location loc2) {
        this(loc1.getWorld(), loc1.toVector(), loc2.toVector());
    }

    public Region(World world, Vector pos1, Vector pos2) {
        this.world = world;

        this.loc1 = pos1;
        this.loc2 = pos2;

        minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

    }

    public Vector center() {
        return loc2.midpoint(loc1);
    }

    public boolean contains(Region region) {
        return region.getWorld().equals(world) &&
                region.getMinX() >= minX && region.getMaxX() <= maxX &&
                region.getMinZ() >= minZ && region.getMaxZ() <= maxZ;
    }

    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockZ());
    }

    public boolean contains(int x, int z) {
        return x >= minX && x <= maxX &&
                z >= minZ && z <= maxZ;
    }

    public boolean overlaps(Region region) {
        return region.getWorld().equals(world) &&
                !(region.getMinX() > maxX || region.getMinZ() > maxZ ||
                        minZ > region.getMaxX() || minZ > region.getMaxZ());
    }
}
