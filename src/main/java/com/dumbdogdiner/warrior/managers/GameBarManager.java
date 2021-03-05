package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.builders.GameBossBar;
import org.bukkit.boss.BarColor;

import java.util.TreeMap;

public class GameBarManager {

    private static final TreeMap<WarriorUser, GameBossBar> bossBars = new TreeMap<>();

    public static void addPlayer(WarriorUser user, GameBossBar bossBar) {
        if(bossBars.get(user) != null)
            bossBars.remove(user);

        bossBars.put(user, bossBar);
        bossBar.showPlayer(user.getBukkitPlayer());
    }

    public static void removePlayer(WarriorUser user) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.removePlayer(user.getBukkitPlayer());
            bossBars.remove(user);
        }
    }

    public static GameBossBar getBossBar(WarriorUser user) {
        return bossBars.get(user);
    }

    public static void updateTitle(WarriorUser user, String title) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setTitle(title);
        }
    }

    public static void updateColor(WarriorUser user, BarColor color) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setColor(color);
        }
    }

    public static void updateProgress(WarriorUser user, double val) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setProgress(val);
        }
    }

}
