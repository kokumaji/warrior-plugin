package me.kokumaji.warrior.threads

import me.kokumaji.HibiscusAPI.HibiscusPlugin
import me.kokumaji.HibiscusAPI.threads.HibiscusThread
import me.kokumaji.HibiscusAPI.threads.ThreadOptions
import me.kokumaji.warrior.Constants
import me.kokumaji.warrior.WithWarriorPlugin
import me.kokumaji.warrior.user.User
import me.kokumaji.warrior.user.UserGroup
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable
import org.jetbrains.annotations.NotNull

class SqlUpdateThread(plugin: HibiscusPlugin?) : HibiscusThread("sqlSave", plugin, ThreadOptions.REPEAT_TASK), WithWarriorPlugin {

    override fun getInterval(): Long {
        return 20 * 600
    }

    override fun execute() {
        throw IllegalAccessError("testing this thing")
    }

    private fun notifyFailure(users: List<User>, errorMsg: String?) {
        val msg = Constants.LogMessages.TASK_FAILURE

        getLogger().error(msg, mapOf("TaskIdent" to "sqlUpdate", "Error" to errorMsg))

        for (user in users) {
            user.sendMessage(msg)
            user.playSound(Sound.ENTITY_ARROW_HIT_PLAYER)
        }
    }
}
