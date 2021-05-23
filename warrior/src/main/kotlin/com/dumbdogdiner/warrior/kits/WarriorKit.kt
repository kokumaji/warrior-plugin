package com.dumbdogdiner.warrior.kits

import com.dumbdogdiner.warrior.api.kit.Kit
import com.dumbdogdiner.warrior.api.kit.SlotEnum
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class WarriorKit : Kit {

    override fun getName(): String {
        return "Warrior"
    }

    override fun getDescription(): Array<String> {
        return arrayOf(
                "Lorem ipsum dolor sit amet, consectetur ",
                "adipiscing elit. Pellentesque eu."
        )
    }

    override fun getIcon(): Material {
        return Material.IRON_SWORD
    }

    override fun getItems(): Map<SlotEnum, ItemStack> {
        return mapOf(
                SlotEnum.PRIMARY to ItemStack(Material.IRON_SWORD),
                SlotEnum.HELMET to ItemStack(Material.IRON_HELMET),
                SlotEnum.CHEST to ItemStack(Material.CHAINMAIL_CHESTPLATE),
                SlotEnum.LEGGINGS to ItemStack(Material.LEATHER_LEGGINGS),
                SlotEnum.BOOTS to ItemStack(Material.LEATHER_BOOTS)
        )
    }
}