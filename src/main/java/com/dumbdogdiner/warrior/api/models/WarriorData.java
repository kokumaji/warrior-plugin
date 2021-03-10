package com.dumbdogdiner.warrior.api.models;

import com.dumbdogdiner.warrior.api.WarriorUser;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class WarriorData {

    @Getter @Setter
    public int kills;

    @Getter @Setter
    public int deaths;

    @Getter @Setter
    public int coins;

    @Getter @Setter
    public int deathSounds;

    @Getter @Setter
    public int deathParticles;

    @Getter @Setter
    public int titles;

    @Getter
    public UUID userId;

    @Getter @Setter
    public long firstJoin;

    @Getter @Setter
    public long lastJoin;

    @Getter @Setter
    public long totalTime;

    @Getter @Setter
    public boolean successful;

    public WarriorData(UUID uuid) {
        this.userId = uuid;
    }

    public WarriorData(WarriorUser user) {
        this.kills = user.getKills();
        this.deaths = user.getDeaths();
        this.coins = user.getCoins();
        this.deathSounds = user.getDeathSounds();
        this.deathParticles = user.getDeathParticles();
        this.titles = user.getTitles();

        this.userId = user.getUserId();

        this.firstJoin = user.getFirstJoin();
        this.lastJoin = user.getLastJoin();

        this.totalTime = user.getTotalTime() + (System.currentTimeMillis() - user.getLastJoin());
    }
}
