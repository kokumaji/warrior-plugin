/*
 * Copyright (c) 2021 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information.
 */
package com.dumbdogdiner.warrior.api.arena;

import com.dumbdogdiner.warrior.models.LocationModel;
import com.dumbdogdiner.warrior.models.RegionModel;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

@Data
public class Region {

    private World world;

    private Vector loc1;
    private Vector loc2;

    private int minX;
    private int maxX;

    private int minY;
    private int maxY;

    private int minZ;
    private int maxZ;

    private Vector center;

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

        minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());

        int centerX = (minX + maxX) / 2;
        int centerY = (minY + maxY) / 2;
        int centerZ = (minZ + maxZ) / 2;

        this.center = new Location(world, centerX, centerY, centerZ).toVector();

    }

    public Region(RegionModel rgm) {
        this(rgm.getWorld(), rgm.getPos1(), rgm.getPos2());
    }

    public Region(String world, LocationModel pos1, LocationModel pos2) {
        this(Bukkit.getWorld(world),
                new Location(Bukkit.getWorld(world), pos1.getX(), pos1.getY(), pos1.getZ()).toVector(),
                new Location(Bukkit.getWorld(world), pos2.getX(), pos2.getY(), pos2.getZ()).toVector()
        );
    }

    public Vector center() {
        return center.clone();
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
