package com.dumbdogdiner.warrior.api.managers;

import com.dumbdogdiner.warrior.api.user.WarriorUser;

/**
 * Manages the sending of notifications.
 */
public interface WarriorNotificationManager {
    /**
     * Send a notification to the target player.
     * @param user The player to send a notification to
     */
    void sendNotification(WarriorUser user);
}
