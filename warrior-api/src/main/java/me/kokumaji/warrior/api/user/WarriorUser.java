package me.kokumaji.warrior.api.user;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface WarriorUser<T extends WarriorUser> {

    UUID getUniqueId();

    Player toBukkit();

    boolean hasKit();

    default void sendMessage(String message) {
        this.toBukkit().sendMessage(message);
    }

    default void sendActionBar(String message) {
        this.toBukkit().sendActionBar(message);
    }

    default void playSound(Sound sound) {
        this.toBukkit().playSound(toBukkit().getLocation(), sound, 0.5f, 0.5f);
    }

}
