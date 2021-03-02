package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.warrior.api.sesssions.Session;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class KillStreakChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Player player;

    @Getter
    private final Session context;

    @Getter
    private final int streak;

    public KillStreakChangeEvent(int killStreak, ArenaSession arenaSession) {
        this.player = Bukkit.getPlayer(arenaSession.getUserId());
        this.context = arenaSession;
        this.streak = killStreak;
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
