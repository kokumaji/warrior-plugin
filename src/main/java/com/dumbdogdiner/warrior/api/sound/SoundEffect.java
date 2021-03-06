package com.dumbdogdiner.warrior.api.sound;

import com.dumbdogdiner.warrior.Warrior;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SoundEffect {

    public enum SoundEffectType {
        ASCENDING,
        DESCENDING,
        NORMAL;
    }

    private Sound sound;

    private int repeatTimes = 0;
    private long repeatDelay = 0L;

    private SoundEffectType effectType = SoundEffectType.NORMAL;

    private float volume;
    private Note note;

    public SoundEffect(Sound sound, float volume, Note note) {
        this.sound = sound;
        this.volume = volume;
        this.note = note;
    }

    public SoundEffect repeat(int times, long delay) {
        this.repeatTimes = times;
        this.repeatDelay = delay;

        return this;
    }

    public SoundEffect ascending() {
        this.effectType = SoundEffectType.ASCENDING;

        return this;
    }

    public SoundEffect descending() {
        this.effectType = SoundEffectType.DESCENDING;

        return this;
    }

    public void play(Player... players) {
        for(Player p : players) {
            if(repeatTimes == 0) p.playSound(p.getLocation(), sound, volume, (float) note.getPitch());
            else {
                if(repeatDelay == 0L) repeatDelay = 1L;
                new BukkitRunnable() {

                    int current = 0;

                    @Override
                    public void run() {
                        if(!p.isOnline()) cancel();
                        else {
                            if(current == repeatTimes - 1) {
                                cancel();
                                return;
                            }

                            if(effectType == SoundEffectType.ASCENDING) {
                                if(current > Note.values().length - 1) {
                                    cancel();
                                    return;
                                }
                            }

                            float pitch = (float) note.getPitch();
                            if(effectType == SoundEffectType.ASCENDING) pitch = (float) Note.values()[current].getPitch();
                            if(effectType == SoundEffectType.DESCENDING) pitch = (float) Note.values()[(Note.values().length - current) - 1].getPitch();

                            p.playSound(p.getLocation(), sound, volume, pitch);
                            current++;
                        }
                    }

                }.runTaskTimer(Warrior.getInstance(), 2L, repeatDelay);
            }
        }
    }

}
