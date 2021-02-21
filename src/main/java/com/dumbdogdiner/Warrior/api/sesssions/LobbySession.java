package com.dumbdogdiner.Warrior.api.sesssions;

import java.util.UUID;

public class LobbySession implements Session {

    private final long timestamp;

    public LobbySession(UUID userId) {
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public SessionType getType() {
        return SessionType.LOBBY;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
