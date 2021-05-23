package com.dumbdogdiner.warrior.user

import com.dumbdogdiner.warrior.api.user.WarriorUser
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class User(val uuid: UUID) : WarriorUser<User> {

    var activeKit: String = "none"

    val player: Player = Bukkit.getPlayer(uuid)!!

    override fun getUniqueId(): UUID {
        return uuid
    }

    override fun toBukkit(): Player {
        return player
    }

    override fun getKit(): String {
        return activeKit
    }

    override fun setKit(name: String) {
        this.activeKit = name
    }

}