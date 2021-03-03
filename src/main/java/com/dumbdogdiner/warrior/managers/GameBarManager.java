package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.util.GameBossBar;
import com.google.common.base.Preconditions;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

import java.util.HashMap;

public class GameBarManager {

    private static final HashMap<WarriorUser, GameBossBar> bossBars = new HashMap<>();

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
