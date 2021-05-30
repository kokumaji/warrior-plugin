package com.dumbdogdiner.warrior.api.kit;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum SlotEnum {

    PRIMARY(0),
    SECONDARY(1),

    // These are usually blank but can be used in some cases
    SLOT_3(2),
    SLOT_4(3),
    SLOT_5(4),
    SLOT_6(5),
    SLOT_7(6),

    AMMO(7),

    // should only be used for special abilities
    ABILITY(8),

    HELMET(39),
    CHEST(38),
    LEGGINGS(37),
    BOOTS(36);

    @Getter
    private int slotIndex;

    SlotEnum(int num) {
        this.slotIndex = num;
    }

    public static void setSlot(@NotNull Player player, @NotNull ItemStack item, @NotNull SlotEnum slot) {
        Inventory inv = player.getInventory();

        inv.setItem(slot.getSlotIndex(), item);
    }

}
