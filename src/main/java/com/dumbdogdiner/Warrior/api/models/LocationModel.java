package com.dumbdogdiner.Warrior.api.models;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationModel {

    @Getter
    private int x;
    @Getter
    private int y;
    @Getter
    private int z;
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
