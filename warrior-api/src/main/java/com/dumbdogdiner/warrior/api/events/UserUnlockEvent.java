package com.dumbdogdiner.warrior.api.events;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.user.cosmetics.Unlockeable;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UserUnlockEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final WarriorUser<?> user;

    @Getter
    private final Unlockeable unlocked;

    public UserUnlockEvent(WarriorUser<?> user, Unlockeable unlocked) {
        this.user = user;
        this.unlocked = unlocked; // TODO: make cosmetics use this interface
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
