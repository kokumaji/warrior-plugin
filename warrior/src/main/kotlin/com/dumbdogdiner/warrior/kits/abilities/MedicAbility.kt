package com.dumbdogdiner.warrior.kits.abilities

import com.dumbdogdiner.warrior.api.kit.WarriorAbility
import com.dumbdogdiner.warrior.api.user.WarriorUser
import org.bukkit.Sound
import org.bukkit.attribute.Attribute

class MedicAbility : WarriorAbility {

    override fun getName(): String {
        return "Medic"
    }

    override fun run(user: WarriorUser<*>) {
        val player = user.toBukkit()
        val maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue

        if(this.shouldExecute(user)) {
            // this makes me cum tbh
            player.health = maxHealth
            player.playSound(player.location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f)
        }
    }

    override fun shouldExecute(user: WarriorUser<*>): Boolean {
        val maxHealth = user.toBukkit().getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
        return user.toBukkit().health < maxHealth
    }

}