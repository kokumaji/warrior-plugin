/*
 * Copyright (c) 2021 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information.
 */
package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.api.models.LocationModel;
import com.dumbdogdiner.Warrior.api.models.RegionModel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Region {

    @Getter
    private transient World world;

    @Getter
    private transient Vector loc1;
    @Getter
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

    public Region(RegionModel rgm) {
        LocationModel pos1 = rgm.getPos1();
        LocationModel pos2 = rgm.getPos2();
        String worldName = rgm.getWorld();

        this.loc1 = new Location(Bukkit.getWorld(worldName), pos1.getX(), pos1.getY(), pos1.getZ()).toVector();
        this.loc2 = new Location(Bukkit.getWorld(worldName), pos2.getX(), pos2.getY(), pos2.getZ()).toVector();
        this.world = Bukkit.getWorld(worldName);

        minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
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
