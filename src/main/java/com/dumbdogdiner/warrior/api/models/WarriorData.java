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

    @Getter
    public UUID userId;

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
        this.userId = user.getUserId();
    }
}
