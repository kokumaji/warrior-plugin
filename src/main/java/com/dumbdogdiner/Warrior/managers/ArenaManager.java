package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.Session;
import com.dumbdogdiner.Warrior.api.sesssions.SessionType;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaManager {

    private static final HashMap<Integer, Arena> arenaMap = new HashMap<>();

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

    public static List<WarriorUser> getPlayers(Arena a) {
        return PlayerManager.getListOf(user -> {
            Session s = user.getSession();
            if(s.getType().equals(SessionType.GAME))
                return ((ArenaSession)s).getArena().equals(a);
            return false;
        });
    }

    public static boolean isPlaying(Player player) {
        WarriorUser user = PlayerManager.get(player.getUniqueId());
        if(user.getSession() == null) return false;
        return user.getSession().getType() == SessionType.GAME;
    }

    public static ArenaSession getSession(Player player) {
        if(isPlaying(player)) {
            WarriorUser user = PlayerManager.get(player.getUniqueId());
            return (ArenaSession) user.getSession();
        }

        return null;
    }
}
