package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;

import java.io.File;
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

    public static void loadArenas() {
        File dataFolder = new File(JSONUtil.ARENA_DATA_PATH);
        File[] files = dataFolder.listFiles();

        int i = 0;
        for(File f : files) {
            registerArena(new Arena(f));
            i++;
        }

        Warrior.getInstance().getLogger().info("Loaded " + i + " arena(s) from disk!");
    }
}
