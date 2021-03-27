package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WarriorLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final WarriorUser user;

    @Getter
    private final int level;

    public WarriorLevelUpEvent(WarriorUser user) {
        this.user = user;
        this.level = user.getLevel();
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
