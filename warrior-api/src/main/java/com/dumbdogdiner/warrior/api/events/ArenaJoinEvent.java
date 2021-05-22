package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArenaJoinEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final WarriorUser<?> player;

    @Getter
    private final ArenaSession session;

    @Getter
    private final Arena arena;

    @Getter @Setter
    private boolean cancelled;

    public ArenaJoinEvent(ArenaSession arenaSession, WarriorUser<?> player) {
        this.player = player;
        this.session = arenaSession;
        this.arena = arenaSession.getArena();
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
