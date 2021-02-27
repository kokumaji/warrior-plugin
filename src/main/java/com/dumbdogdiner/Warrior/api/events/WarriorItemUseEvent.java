package com.dumbdogdiner.Warrior.api.events;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// This is the stupidest workaround to the
// "PlayerInteractEvent fires twice" issue... but it works!!!

@Data
public class WarriorItemUseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Player player;
    private ItemAction action;
    private ItemStack item;

    public WarriorItemUseEvent(Player player, ItemAction action, ItemStack item) {
        this.action = action;
        this.player = player;
        this.item = item;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
