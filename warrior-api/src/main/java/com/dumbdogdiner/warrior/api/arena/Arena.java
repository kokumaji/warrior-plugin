package com.dumbdogdiner.warrior.api.arena;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.arena.gameflags.FlagContainer;
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.BlockBreakFlag;
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.BlockPlaceFlag;
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.MaxHealthFlag;
import com.dumbdogdiner.warrior.api.models.ArenaModel;
import com.dumbdogdiner.warrior.api.models.LocationModel;
import com.dumbdogdiner.warrior.api.models.RegionModel;
import com.dumbdogdiner.warrior.api.models.metadata.ArenaMetadata;
import com.dumbdogdiner.warrior.api.util.DataType;
import com.dumbdogdiner.warrior.api.util.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Arena {

    @Getter
    private String name;

    @Getter
    private Location spawn;

    @Getter
    private Region bounds;

    @Getter
    private boolean enabled;

    @Getter @Setter
    private ArenaMetadata metadata;

    @Getter
    private int id;

    @Getter
    private FlagContainer flags;

    public Arena(String arenaName, Region region, Location spawn, boolean enabled, int id) {
        if(WarriorAPI.getService().getArenaManager().get(arenaName) != null) throw new IllegalStateException("Duplicate Arena in ArenaManager");
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
            this.metadata = model.getMetadata();

            World world = Bukkit.getServer().getWorld(rgm.getWorld());
            if(world == null) {
                WarriorAPI.getService().getLogger().warn(String.format("Could not find world %s, attempting to load it.", rgm.getWorld()));
                Bukkit.createWorld(new WorldCreator(rgm.getWorld()));
                world = Bukkit.getWorld(rgm.getWorld());
            }
            WarriorAPI.getService().getLogger().debug(String.format("Loading Arena %s in World %2s", model.getName(), world.getName()));

            this.name = model.getName();
            this.spawn = new Location(world, sm.getX(), sm.getY(), sm.getZ(), sm.getYaw(), sm.getPitch());
            this.bounds = new Region(rgm);
            this.enabled = model.isEnabled();
            this.id = id;

            this.flags = new FlagContainer();
            flags.addFlag(BlockBreakFlag.BREAK_DENY);
            flags.addFlag(BlockPlaceFlag.PLACE_DENY);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        JSONUtil.saveArena(this);
    }

}
