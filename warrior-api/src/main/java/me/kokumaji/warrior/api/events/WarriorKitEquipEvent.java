package me.kokumaji.warrior.api.events;

import me.kokumaji.warrior.api.kit.Kit;
import me.kokumaji.warrior.api.user.WarriorUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WarriorKitEquipEvent extends Event implements Cancellable {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Getter @Setter
    private boolean cancelled;

    @Getter
    private final WarriorUser<?> user;

    @Getter
    private final Kit kit;

    public WarriorKitEquipEvent(WarriorUser<?> user, Kit kit) {
        this.user = user;
        this.kit = kit;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
