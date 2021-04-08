package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.api.managers.WarriorGameBarManager;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.builders.GameBossBar;
import org.bukkit.boss.BarColor;

import java.util.TreeMap;

public class GameBarManager implements WarriorGameBarManager {

    private final TreeMap<WarriorUser, GameBossBar> bossBars = new TreeMap<>();

    public void addPlayer(WarriorUser user, GameBossBar bossBar) {
        if(bossBars.get(user) != null)
            bossBars.remove(user);

        bossBars.put(user, bossBar);
        bossBar.showPlayer(user.getBukkitPlayer());
    }

    public void removePlayer(WarriorUser user) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.removePlayer(user.getBukkitPlayer());
            bossBars.remove(user);
        }
    }

    public GameBossBar getBossBar(WarriorUser user) {
        return bossBars.get(user);
    }

    public void updateTitle(WarriorUser user, String title) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setTitle(title);
        }
    }

    public void updateColor(WarriorUser user, BarColor color) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setColor(color);
        }
    }

    public void updateProgress(WarriorUser user, double val) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setProgress(val);
        }
    }

}
