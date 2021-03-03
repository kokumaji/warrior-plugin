package com.dumbdogdiner.warrior.api.util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class GameBossBar {

    @Getter
    private final BossBar bossBar;

    public GameBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
        bossBar = Bukkit.createBossBar(title, color, style, flags);
    }

    public GameBossBar setTitle(String s) {
        this.bossBar.setTitle(s);

        return this;
    }

    public GameBossBar setStyle(BarStyle style) {
        this.bossBar.setStyle(style);

        return this;
    }

    public GameBossBar setColor(BarColor color) {
        this.bossBar.setColor(color);

        return this;
    }

    public GameBossBar showPlayer(Player player) {
        this.bossBar.addPlayer(player);

        return this;
    }

    public GameBossBar removePlayer(Player player) {
        this.bossBar.removePlayer(player);

        return this;
    }

    public GameBossBar setProgress(double progress) {
        this.bossBar.setProgress(progress);

        return this;
    }

}
