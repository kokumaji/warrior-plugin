package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.managers.WarriorNotificationManager;
import com.dumbdogdiner.warrior.api.sound.Melody;
import com.dumbdogdiner.warrior.api.sound.Note;
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings;
import com.dumbdogdiner.warrior.api.util.MathUtil;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class NotificationManager implements WarriorNotificationManager {

    private final String[] msgPool = Warrior.getTranslator().getStrings("notifications.messages", LanguageCode.EN_US);

    private final Melody msgSound = new Melody(Instrument.PIANO, 2L, 0.65f, Note.D2, Note.F1_SHARP, Note.F2_SHARP);

    private final int interval = Warrior.getInstance().getConfig().getInt("general-settings.notification-interval");

    @Getter
    private boolean running;
    private int taskId;

    /**
     * Start the notification runnable.
     */
    public void start() {
        if(running) {
            String msg = "Attempted to start NotificationManager task, but it's already running!";
            Warrior.getPluginLogger().warn(msg);
            return;
        }
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for(WarriorUser user : PlayerManager.getList()) {
                    sendNotification(user);
                }
            }
        };

        runnable.runTaskTimer(Warrior.getInstance(), interval * 20L, interval * 20L);
        taskId = runnable.getTaskId();
        running = true;
    }

    /**
     * Stop the notification runnable.
     */
    public void stop() {
        if(running) {
            Bukkit.getScheduler().cancelTask(taskId);
            running = false;
        }
    }

    /**
     * Send a notification to the target player. Used by the runnable to send notifications.
     * @param user The player to send a notification to
     */
    public void sendNotification(WarriorUser user) {
        GeneralSettings settings = user.getSettings();
        if(settings.receiveNotifications()) {
            String rndMsg = MathUtil.randomElement(msgPool);
            String formatted = TranslationUtil.translateColor(
                Warrior.getTranslator().translate("notifications.message-format", new HashMap<>() {
                    {
                        put("MESSAGE", rndMsg);
                        put("PREFIX", TranslationUtil.getPrefix());
                    }
                })
            );
            user.sendMessage(formatted);
            msgSound.play(user.getBukkitPlayer());
        }
    }

}
