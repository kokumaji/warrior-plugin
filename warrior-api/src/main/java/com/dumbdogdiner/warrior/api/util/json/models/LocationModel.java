package com.dumbdogdiner.warrior.api.util.json.models;

import com.dumbdogdiner.warrior.api.util.json.JsonModel;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationModel implements JsonModel {

    @Getter
    private final int x;
    @Getter
    private final int y;
    @Getter
    private final int z;
    @Getter
    private float yaw;
    @Getter
    private float pitch;
    @Getter
    private String world;

    public LocationModel(int x, int y, int z, float yaw, float pitch, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public LocationModel(Location loc) {
        this.x = (int) loc.getX();
        this.y = (int) loc.getY();
        this.z = (int) loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
        this.world = loc.getWorld().getName();
    }

    public LocationModel(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LocationModel(Vector vector) {
        this.x = vector.getBlockX();
        this.y = vector.getBlockY();
        this.z = vector.getBlockZ();
    }

}
