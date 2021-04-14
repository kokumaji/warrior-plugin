package com.dumbdogdiner.warrior.user

import com.dumbdogdiner.warrior.WithWarriorPlugin
import com.dumbdogdiner.warrior.api.user.WarriorUserCache
import java.util.TreeMap
import java.util.UUID
import com.dumbdogdiner.warrior.Warrior
import com.google.common.base.Preconditions
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.ArrayList
import java.util.function.Predicate
import java.util.stream.Collectors

class UserCache : WithWarriorPlugin, WarriorUserCache<User> {

    private val users = TreeMap<UUID, User>()

    override fun contains(uuid: UUID): Boolean {
        return users.containsKey(uuid)
    }

    override fun addUser(userId: UUID): User {
        val p: Player = Bukkit.getPlayer(userId)!!

        return if (!users.containsKey(userId)) {
            val user = User(userId)
            Warrior.connection.insertUser(user)
            users[userId] = user

            user

        } else {
            getLogger().warn("Attempted to register WarriorUser ${p.name} (${p.uniqueId}) twice!")

            get(userId)

        }
    }

    override fun remove(user: User) {
        remove(user.userId)
    }

    override fun remove(userId: UUID) {
        if (users.containsKey(userId)) {
            val user = users[userId]!!
            user.saveData()

            users.remove(userId)

        } else {
            getLogger().warn("Failed to remove WarriorUser $userId from cache.")
        }
    }

    override fun get(userId: UUID): User {
        return users[userId]!!
    }

    override fun getList(): List<User> {
        return ArrayList(users.values)
    }

    override fun getListOf(predicate: Predicate<User>): List<User> {
        return users.values.stream().filter(predicate).collect(Collectors.toList())
    }
}