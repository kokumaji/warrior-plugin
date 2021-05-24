package com.dumbdogdiner.warrior.kits

import com.dumbdogdiner.warrior.api.kit.Kit
import com.dumbdogdiner.warrior.api.kit.SlotEnum
import com.dumbdogdiner.warrior.api.kit.WarriorAbility
import com.dumbdogdiner.warrior.api.kit.weapons.ItemRarity
import com.dumbdogdiner.warrior.api.kit.weapons.WarriorWeapon
import com.dumbdogdiner.warrior.kits.abilities.MedicAbility
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class WarriorKit : Kit {

    private val primaryWeapon = WarriorWeapon(Material.IRON_SWORD)
                                .itemName("&8··· &7THE SWORD &8···")
                                // :eyes: .sweepAttack(SweepAttack.SHARP_STING)
                                .description("&7Rarity ${ItemRarity.COMMON.formatted}", " ", "&7&oDescription goes here...")
                                .damageOverride(6.0)
                                .hideFlags()
                                .build()

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

    override fun getAbility(): WarriorAbility {
        return MedicAbility()
    }

    override fun getPrimary(): ItemStack {
        return this.primaryWeapon
    }

    override fun getItems(): Map<SlotEnum, ItemStack> {
        return mapOf(
                SlotEnum.PRIMARY to this.primary,
                SlotEnum.HELMET to ItemStack(Material.IRON_HELMET),
                SlotEnum.CHEST to ItemStack(Material.CHAINMAIL_CHESTPLATE),
                SlotEnum.LEGGINGS to ItemStack(Material.LEATHER_LEGGINGS),
                SlotEnum.BOOTS to ItemStack(Material.LEATHER_BOOTS)
        )
    }
}