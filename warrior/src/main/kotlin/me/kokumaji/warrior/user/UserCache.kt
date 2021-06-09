package me.kokumaji.warrior.user

import me.kokumaji.warrior.WarriorPlugin
import me.kokumaji.warrior.WithWarriorPlugin
import me.kokumaji.warrior.api.user.IUserCache
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UserCache(warriorPlugin: WarriorPlugin) : IUserCache<User>, WithWarriorPlugin {

    private var userListener: Listener? = null
    var userMap: HashMap<UUID, User> = HashMap()
    private val owner: WarriorPlugin = warriorPlugin

    override fun getListener(): Listener? {
        return userListener
    }

    override fun setListener(listener: Listener) {
        if(userListener == null) {
            this.userListener = listener

<<<<<<< Updated upstream:warrior/src/main/kotlin/com/dumbdogdiner/warrior/user/UserCache.kt
            this.unregister()
            Bukkit.getPluginManager().registerEvents(this.userListener!!, owner)
        } else {
            getLogger().warn("Player Listener already registered... Skipping")
        }
=======
        this.unregister()
        Bukkit.getPluginManager().registerEvents(this.userListener!!, WarriorPlugin.instance)
>>>>>>> Stashed changes:warrior/src/main/kotlin/me/kokumaji/warrior/user/UserCache.kt
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

    override fun filter(predicate: Predicate<User>?): MutableList<User> {
        return this.list.stream().filter(predicate).collect(Collectors.toList())
    }

}