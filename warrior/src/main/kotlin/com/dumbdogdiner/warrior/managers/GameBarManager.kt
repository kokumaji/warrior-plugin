package com.dumbdogdiner.warrior.managers

import com.dumbdogdiner.warrior.api.managers.WarriorGameBarManager
import java.util.TreeMap
import com.dumbdogdiner.warrior.api.builders.GameBossBar
import com.dumbdogdiner.warrior.api.user.WarriorUser
import com.dumbdogdiner.warrior.user.User
import org.bukkit.boss.BarColor

class GameBarManager : WarriorGameBarManager {

    private val bossBars = TreeMap<WarriorUser, GameBossBar?>()

    override fun addPlayer(user: WarriorUser, bossBar: GameBossBar) {
        if (bossBars[user] != null) bossBars.remove(user)
        bossBars[user] = bossBar
        bossBar.showPlayer(user.bukkitPlayer)
    }

    override fun removePlayer(user: WarriorUser) {
        if (bossBars[user] != null) {
            val bossBar = bossBars[user]
            bossBar!!.removePlayer(user.bukkitPlayer)
            bossBars.remove(user)
        }
    }

    override fun getBossBar(user: WarriorUser): GameBossBar? {
        return bossBars[user]
    }

    override fun updateTitle(user: WarriorUser, title: String?) {
        if (bossBars[user] != null) {
            val bossBar = bossBars[user]
            bossBar!!.setTitle(title)
        }
    }

    override fun updateColor(user: WarriorUser, color: BarColor?) {
        if (bossBars[user] != null) {
            val bossBar = bossBars[user]
            bossBar!!.setColor(color)
        }
    }

    override fun updateProgress(user: WarriorUser, double: Double) {
        if (bossBars[user] != null) {
            val bossBar = bossBars[user]
            bossBar!!.setProgress(double)
        }
    }
}