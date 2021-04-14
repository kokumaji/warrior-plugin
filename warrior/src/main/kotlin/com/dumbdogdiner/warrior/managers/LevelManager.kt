package com.dumbdogdiner.warrior.managers

import com.dumbdogdiner.warrior.api.managers.WarriorLevelManager
import com.dumbdogdiner.warrior.api.user.WarriorUser
import com.dumbdogdiner.warrior.api.util.MathUtil
import com.dumbdogdiner.warrior.user.User
import kotlin.math.floor
import kotlin.math.sqrt

class LevelManager : WarriorLevelManager {

    override fun xpToLevel(xp: Int): Int {
        var xp = xp

        var res = floor((sqrt((20 * xp * 500).toDouble()) - 50) / 10) + 1
        if (res < 0) res = 0.0

        var actualLevel = 0
        var i = 0

        while (i < res) {
            val sub = levelToXp(i)
            if (sub <= xp) {
                xp -= sub
                actualLevel++
            }

            i++
        }

        return actualLevel
    }

    override fun levelToXp(level: Int): Int {
        return 5 * (level xor 2) + 50 * level + 100
    }

    override fun getProgress(user: WarriorUser): Double {
        val nextXp = levelToXp(user.level).toDouble()
        return MathUtil.round(user.relativeXp / nextXp, 2)
    }

    companion object {
        // level constants
        const val MIN_XP = 5
        const val MAX_XP = 20
    }
}