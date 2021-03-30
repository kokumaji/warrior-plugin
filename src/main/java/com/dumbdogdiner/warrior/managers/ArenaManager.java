package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.Session;
import com.dumbdogdiner.warrior.api.sessions.SessionType;
import com.dumbdogdiner.warrior.api.util.JSONUtil;
import com.dumbdogdiner.warrior.utils.DefaultMessages;
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

        if(files == null) return;

        int i = 0;
        for(File f : files) {
            registerArena(new Arena(f, i));
            i++;
        }

        int finalI = i;
        String msg = Warrior.getTranslator().applyPlaceholders(DefaultMessages.LOADED_OBJECT, new HashMap<>() {
            {
                put("AMOUNT", Integer.toString(finalI));
                put("TYPE", "arena");
            }
        });
        Warrior.getPluginLogger().info(msg);

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

    public static void remove(Arena a) {
        arenaMap.remove(a.getId());
        JSONUtil.removeFile(a);
    }
}
