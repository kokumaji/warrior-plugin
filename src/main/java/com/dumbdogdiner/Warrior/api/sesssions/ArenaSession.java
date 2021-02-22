package com.dumbdogdiner.Warrior.api.sesssions;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.events.ArenaJoinEvent;
import com.dumbdogdiner.Warrior.api.events.GameStateChangeEvent;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class ArenaSession implements Session {

    @Getter
    public UUID playerId;

    private final long startTime;

    @Getter
    private final Arena arena;

    @Getter
    private GameState state;

    public ArenaSession(UUID uuid, Arena arena) {
        this(uuid, arena, GameState.PRE_GAME);
    }

    public ArenaSession(UUID uuid, Arena arena, GameState state) {
        this.playerId = uuid;
        this.startTime = System.currentTimeMillis();
        this.arena = arena;

        setState(state);
        ArenaJoinEvent e = new ArenaJoinEvent(this, Objects.requireNonNull(Bukkit.getPlayer(uuid)));
        Bukkit.getPluginManager().callEvent(e);
    }

    @Override
    public SessionType getType() {
        return SessionType.GAME;
    }

    @Override
    public long getTimestamp() {
        return startTime;
    }

    public void setState(GameState newState) {
        GameState old = state;
        this.state = newState;
        GameStateChangeEvent e = new GameStateChangeEvent(this.state, newState, this, Objects.requireNonNull(Bukkit.getPlayer(playerId)));
        Bukkit.getPluginManager().callEvent(e);
        if(e.isCancelled())
            this.state = old;
    }
}
