package com.dumbdogdiner.Warrior.api.models;

import lombok.Data;

import org.bukkit.inventory.ItemStack;

@Data
public class ItemStackModel {

    private String type;

    private int amount;

    private String name;

    private int slot;

    public ItemStackModel(ItemStack item, int slot) {
        this.type = item.getType().toString();
        this.amount = item.getAmount();
        this.name = item.getItemMeta().getDisplayName().length() > 0 ? item.getItemMeta().getDisplayName() : null;
        this.slot = slot;
    }
}
