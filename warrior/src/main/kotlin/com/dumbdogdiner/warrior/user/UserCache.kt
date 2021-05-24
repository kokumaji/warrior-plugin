package com.dumbdogdiner.warrior.user

import com.dumbdogdiner.warrior.WarriorPlugin
import com.dumbdogdiner.warrior.WithWarriorPlugin
import com.dumbdogdiner.warrior.api.user.IUserCache
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UserCache: IUserCache<User>, WithWarriorPlugin {

    private var userListener: Listener? = null
    var userMap: HashMap<UUID, User> = HashMap()

    override fun getListener(): Listener? {
        return userListener
    }

    override fun setListener(listener: Listener) {
        if(userListener == null) {
            this.userListener = listener

            this.unregister()
            Bukkit.getPluginManager().registerEvents(this.userListener!!, WarriorPlugin.instance)
        } else {
            getLogger().warn("Player Listener already registered... Skipping")
        }
    }

    override fun contains(uuid: UUID): Boolean {
        return userMap[uuid] != null
    }

    override fun addUser(userId: UUID): User? {
        val p: Player = Bukkit.getPlayer(userId)!!

        if(!p.isOnline) {
            getLogger().warn("User ${p.name}(${p.uniqueId}) is not online!")
            return null
        }

        return if(!this.contains(userId)) {
            val user = User(userId)
            // Need to add SQL stuff here later
            userMap[userId] = user
            user
        } else {
            getLogger().warn("Attempted to register WarriorUser ${p.name}(${p.uniqueId}) twice!")
            get(userId)
        }
    }

    override fun remove(user: User) {
        this.remove(user.uniqueId)
    }

    override fun remove(userId: UUID) {
        if(this.contains(userId)) {
            userMap.remove(userId)
        }
    }

    override fun get(userId: UUID): User? {
        return userMap[userId]
    }

    override fun getList(): MutableList<User> {
        return ArrayList(userMap.values)
    }

    override fun getListOf(predicate: Predicate<User>?): MutableList<User> {
        return this.list.stream().filter(predicate).collect(Collectors.toList())
    }

}