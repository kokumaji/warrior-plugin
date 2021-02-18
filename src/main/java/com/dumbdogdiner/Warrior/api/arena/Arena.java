package com.dumbdogdiner.Warrior.api.arena;

import lombok.Getter;
import org.bukkit.Location;

public class Arena {

    @Getter
    private String name;

    @Getter
    private Location spawn;

    @Getter
    private Region bounds;

    public Arena(String arenaName, Region region, Location spawn) {
        this.name = arenaName;
        this.spawn = spawn;
        this.bounds = region;
    }
}
