package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.api.util.DataType;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.google.gson.Gson;

import lombok.Getter;
import org.bukkit.Location;

import java.io.File;

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
        //JsonReader reader = new JsonReader()
    }

    public void save() {
        JSONUtil.saveArena(this);
    }

}
