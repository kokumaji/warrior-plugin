package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.api.arena.gameflags.FlagContainer;
import com.dumbdogdiner.Warrior.api.arena.gameflags.GameFlag;
import com.dumbdogdiner.Warrior.api.arena.gameflags.implementation.BlockBreakFlag;
import com.dumbdogdiner.Warrior.api.arena.gameflags.implementation.BlockPlaceFlag;
import com.dumbdogdiner.Warrior.api.arena.gameflags.implementation.MaxHealthFlag;
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
import java.util.HashMap;
import java.util.Map;

public class Arena {

    @Getter
    private String name;

    @Getter
    private Location spawn;

    @Getter
    private Region bounds;

    @Getter
    private boolean enabled;

    @Getter
    private int id;

    @Getter
    private FlagContainer flags;

    public Arena(String arenaName, Region region, Location spawn, boolean enabled, int id) {
        if(ArenaManager.get(arenaName) != null) throw new IllegalStateException("Duplicate Arena in ArenaManager");
        else if(JSONUtil.fileExists(DataType.ARENA, arenaName)) throw new IllegalStateException("Can't create Arena, JSON data already exists");
        this.name = arenaName;
        this.spawn = spawn;
        this.bounds = region;
        this.enabled = enabled;

        this.id = id;

        this.flags = new FlagContainer();
        flags.addFlag(BlockBreakFlag.BREAK_DENY);
        flags.addFlag(BlockPlaceFlag.PLACE_DENY);
        flags.addFlag(MaxHealthFlag.DEFAULT_HEALTH);

    }

    public Arena(File file, int id) {
        try(JsonReader reader = new JsonReader(new FileReader(file))) {
            ArenaModel model = new Gson().fromJson(reader, ArenaModel.class);
            LocationModel sm = model.getSpawn();
            RegionModel rgm = model.getBounds();

            this.name = model.getName();
            this.spawn = new Location(Bukkit.getWorld(sm.getWorld()), sm.getX(), sm.getY(), sm.getZ(), sm.getYaw(), sm.getPitch());
            this.bounds = new Region(rgm);
            this.enabled = model.isEnabled();
            this.id = id;

            this.flags = new FlagContainer();
            flags.addFlag(BlockBreakFlag.BREAK_DENY);
            flags.addFlag(BlockPlaceFlag.PLACE_DENY);
            flags.addFlag(new MaxHealthFlag(10D));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        JSONUtil.saveArena(this);
    }

}
