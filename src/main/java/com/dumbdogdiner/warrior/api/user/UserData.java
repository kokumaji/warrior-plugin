package com.dumbdogdiner.warrior.api.user;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class UserData {

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
    public int totalXp;

    @Getter @Setter
    public int relativeXp;

    @Getter @Setter
    public boolean successful;

    public UserData(UUID uuid) {
        this.userId = uuid;
    }

    public UserData(WarriorUser user) {
        this.kills = user.getKills();
        this.deaths = user.getDeaths();
        this.coins = user.getCoins();
        this.deathSounds = user.getDeathSounds();
        this.deathParticles = user.getDeathParticles();
        this.titles = user.getTitles();

        this.userId = user.getUserId();

        this.firstJoin = user.getFirstJoin();
        this.lastJoin = user.getLastJoin();

        this.totalXp = user.getTotalXp();
        this.relativeXp = user.getRelativeXp();

        this.totalTime = user.getTotalTime() + (System.currentTimeMillis() - user.getLastJoin());
    }
}
