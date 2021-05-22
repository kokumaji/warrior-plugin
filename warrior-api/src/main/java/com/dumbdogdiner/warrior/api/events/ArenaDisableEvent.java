package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.arena.Arena;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArenaDisableEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Arena arena;

    @Getter @Setter
    private boolean cancelled;

    public ArenaDisableEvent(Arena arena) {
        this.arena = arena;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
