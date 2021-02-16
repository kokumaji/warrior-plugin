package com.dumbdogdiner.Warrior.api.arena;

import lombok.Getter;
import org.bukkit.Location;

public class Arena {

    @Getter
    private String name;

    public Arena(String arenaName, Region region, Location spawn) {
        this.name = arenaName;
    }
}
