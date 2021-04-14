package com.dumbdogdiner.warrior.managers

import com.dumbdogdiner.warrior.api.managers.WarriorNotificationManager
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode
import com.dumbdogdiner.warrior.api.sound.Melody
import com.dumbdogdiner.warrior.api.sound.Note
import com.dumbdogdiner.warrior.api.user.WarriorUser
import com.dumbdogdiner.warrior.api.util.MathUtil
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import org.bukkit.Bukkit
import org.bukkit.Instrument
import org.bukkit.scheduler.BukkitRunnable
import java.util.HashMap

class NotificationManager : WarriorNotificationManager {
    private val msgPool: Array<String> = Warrior.translator.getStrings("notifications.messages", LanguageCode.EN_US)
    private val msgSound = Melody(Instrument.PIANO, 2L, 0.65f, Note.D2, Note.F1_SHARP, Note.F2_SHARP)
    private val interval: Int = Warrior.instance.config.getInt("general-settings.notification-interval")

    private var running = false
    private var taskId = 0

    /**
     * Start the notification runnable.
     */
    fun start() {
        if (running) {
            val msg = "Attempted to start NotificationManager task, but it's already running!"
            Warrior.pluginLogger.warn(msg)
            return
        }
        val runnable: BukkitRunnable = object : BukkitRunnable() {
            override fun run() {
                for (user in Warrior.userCache.list) {
                    sendNotification(user)
                }
            }
        }

        runnable.runTaskTimer(Warrior.instance, interval * 20L, interval * 20L)
        taskId = runnable.taskId
        running = true
    }

    /**
     * Stop the notification runnable.
     */
    fun stop() {
        if (running) {
            Bukkit.getScheduler().cancelTask(taskId)
            running = false
        }
    }

    /**
     * Send a notification to the target player. Used by the runnable to send notifications.
     * @param user The player to send a notification to
     */
    override fun sendNotification(user: WarriorUser) {
        val settings = user.settings
        if (settings.receiveNotifications()) {
            val rndMsg = MathUtil.randomElement(msgPool)
            val formatted = TranslationUtil.translateColor(
                Warrior.translator.translate("notifications.message-format", object : HashMap<String, String>() {
                    init {
                        put("msg", rndMsg)
                        put("prefix", TranslationUtil.getPrefix())
                    }
                })
            )
            user.sendMessage(formatted)
            msgSound.play(user.bukkitPlayer)
        }
    }
}