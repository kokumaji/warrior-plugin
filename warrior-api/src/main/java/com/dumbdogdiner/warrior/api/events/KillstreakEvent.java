package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.Session;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class KillstreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final WarriorUser<?> player;

    @Getter
    private final Session context;

    @Getter
    private final int streak;

    public KillstreakEvent(int killStreak, ArenaSession arenaSession) {
        this.player = WarriorAPI.getService().getUserCache().get(arenaSession.getUserId());
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
