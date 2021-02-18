package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.api.arena.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaManager {

    private static HashMap<Integer, Arena> arenaMap = new HashMap<>();

    public static void registerArena(Arena arena) {
        arenaMap.put(arenaMap.size(), arena);
    }

    public static List<Arena> getArenas() {
        return new ArrayList<>(arenaMap.values());
    }

    public static Arena get(String arenaName) {
        for(Arena a : arenaMap.values()) {
            if(a.getName().equalsIgnoreCase(arenaName)) return a;
        }

        return null;
    }
}
