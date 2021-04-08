package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.sessions.Session;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SessionChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private Session previous;
    @Getter
    private Session next;
    @Getter
    private Player player;

    public SessionChangeEvent(Session previous, Session next, Player player) {
        this.previous = previous;
        this.next = next;
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
}
