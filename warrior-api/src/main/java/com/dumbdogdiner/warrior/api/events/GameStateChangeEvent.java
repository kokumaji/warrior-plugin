package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.sessions.GameState;
import com.dumbdogdiner.warrior.api.sessions.Session;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

// TODO: Alternatives to this should be considered...
@Deprecated(forRemoval = true)
public class GameStateChangeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final GameState fromState;

    @Getter
    private final GameState toState;

    @Getter
    private Session context;

    @Getter
    private final Player player;

    private boolean cancelled;

    public GameStateChangeEvent(GameState fromState, GameState toState, Session context, Player player) {
        this.fromState = fromState == null ? GameState.PRE_GAME : fromState;
        this.toState = toState;
        this.context = context;
        this.player = player;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
