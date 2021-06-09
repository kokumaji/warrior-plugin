package me.kokumaji.warrior.user

import me.kokumaji.warrior.api.kit.WarriorKitEnums
import me.kokumaji.warrior.api.user.WarriorUser
import me.kokumaji.warrior.kits.KitEnum
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class User(val uuid: UUID) : WarriorUser<User> {

    var kit: WarriorKitEnums = KitEnum.NONE
    private val player: Player = Bukkit.getPlayer(uuid)!!

    val group: UserGroup = UserGroup.USER

    override fun getUniqueId(): UUID {
        return uuid
    }

    override fun toBukkit(): Player {
        return player
    }

    override fun hasKit(): Boolean {
        return kit != KitEnum.NONE
    }

}