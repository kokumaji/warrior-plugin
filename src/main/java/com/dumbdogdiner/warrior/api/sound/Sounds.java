package com.dumbdogdiner.warrior.api.sound;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class Sounds {

    public static void playSoundDelayed(Sound sound, WarriorUser user, long delay) {
        playSoundDelayed(sound, user, 1f, 1f, delay);
    }

    public static void playSoundDelayed(Sound sound, WarriorUser user, float volume, float pitch, long delay) {
        playSoundDelayed(sound, user, user.getLocation(), volume, pitch, delay);
    }

    public static void playSoundDelayed(Sound sound, WarriorUser user, Location location, float volume, float pitch, long delay) {
        new BukkitRunnable() {

            @Override
            public void run() {
                playSound(sound, user, location, volume, pitch);
            }
        }.runTaskLaterAsynchronously(Warrior.getInstance(), delay);
    }

    public static void playSound(Sound sound, WarriorUser user) {
        playSound(sound, user, 1f, 1f);
    }

    public static void playSound(Sound sound, WarriorUser user, float volume, float pitch) {
        playSound(sound, user, user.getLocation(), volume, pitch);
    }

    public static void playSound(Sound sound, WarriorUser user, Location location, float volume, float pitch) {
        user.getBukkitPlayer().playSound(location, sound, volume, pitch);
    }

}
