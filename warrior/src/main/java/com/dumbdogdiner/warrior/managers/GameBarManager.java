package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.api.managers.WarriorGameBarManager;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.api.builders.GameBossBar;
import org.bukkit.boss.BarColor;

import java.util.TreeMap;

public class GameBarManager implements WarriorGameBarManager {

    private final TreeMap<User, GameBossBar> bossBars = new TreeMap<>();

    public void addPlayer(User user, GameBossBar bossBar) {
        if(bossBars.get(user) != null)
            bossBars.remove(user);

        bossBars.put(user, bossBar);
        bossBar.showPlayer(user.getBukkitPlayer());
    }

    public void removePlayer(User user) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.removePlayer(user.getBukkitPlayer());
            bossBars.remove(user);
        }
    }

    public GameBossBar getBossBar(User user) {
        return bossBars.get(user);
    }

    public void updateTitle(User user, String title) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setTitle(title);
        }
    }

    public void updateColor(User user, BarColor color) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setColor(color);
        }
    }

    public void updateProgress(User user, double val) {
        if(bossBars.get(user) != null) {
            GameBossBar bossBar = bossBars.get(user);
            bossBar.setProgress(val);
        }
    }

}
