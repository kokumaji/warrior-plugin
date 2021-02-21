package com.dumbdogdiner.Warrior.api.sesssions;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import lombok.Getter;

import java.util.UUID;

public class ArenaSession implements Session {

    @Getter
    public UUID playerId;

    private long timestamp;

    @Getter
    private Arena arena;

    public ArenaSession(UUID uuid, Arena arena) {
        this.playerId = uuid;
        this.timestamp = System.currentTimeMillis();
        this.arena = arena;
    }

    @Override
    public SessionType getType() {
        return SessionType.GAME;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
