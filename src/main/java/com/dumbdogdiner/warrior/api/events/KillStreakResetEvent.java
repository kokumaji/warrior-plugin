package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.Session;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class KillStreakResetEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;

    @Getter
    private final Session context;

    public KillStreakResetEvent(ArenaSession arenaSession) {
        this.player = Bukkit.getPlayer(arenaSession.getUserId());
        this.context = arenaSession;
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