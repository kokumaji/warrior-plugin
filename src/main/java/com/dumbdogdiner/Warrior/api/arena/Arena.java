package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.api.models.ArenaModel;
import com.dumbdogdiner.Warrior.api.models.LocationModel;
import com.dumbdogdiner.Warrior.api.models.RegionModel;
import com.dumbdogdiner.Warrior.api.util.DataType;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.google.gson.Gson;

import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Arena {

    private static final Gson GSON = new Gson();

    @Getter
    private String name;

    @Getter
    private Location spawn;

    @Getter
    private Region bounds;

    @Getter
    private boolean enabled;

    @Getter
    private String id;

    public Arena(String arenaName, Region region, Location spawn, boolean enabled) {
        if(ArenaManager.get(arenaName) != null) throw new IllegalStateException("Duplicate Arena in ArenaManager");
        else if(JSONUtil.fileExists(DataType.ARENA, arenaName)) throw new IllegalStateException("Can't create Arena, JSON data already exists");
        this.name = arenaName;
        this.spawn = spawn;
        this.bounds = region;
        this.enabled = enabled;

    }

    public Arena(File file) {
        try(JsonReader reader = new JsonReader(new FileReader(file))) {
            ArenaModel model = new Gson().fromJson(reader, ArenaModel.class);
            LocationModel sm = model.getSpawn();
            RegionModel rgm = model.getBounds();

            this.name = model.getName();
            this.spawn = new Location(Bukkit.getWorld(sm.getWorld()), sm.getX(), sm.getY(), sm.getZ(), sm.getYaw(), sm.getPitch());
            this.bounds = new Region(rgm);
            this.enabled = model.isEnabled();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        JSONUtil.saveArena(this);
    }

}
