package com.dumbdogdiner.warrior.api.sound;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.util.MathUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class WarriorSound {

    public enum SoundEffectType {
        ASCENDING,
        DESCENDING,
        RANDOM,
        NORMAL;
    }

    private Sound sound;

    private int repeatTimes = 0;
    private long repeatDelay = 0L;

    private SoundEffectType effectType = SoundEffectType.NORMAL;

    private float volume;
    private Note note;

    public WarriorSound(Sound sound, float volume, Note note) {
        this.sound = sound;
        this.volume = volume;
        this.note = note;
    }

    public WarriorSound repeat(int times, long delay) {
        this.repeatTimes = times;
        this.repeatDelay = delay;

        return this;
    }

    public WarriorSound ascending() {
        this.effectType = SoundEffectType.ASCENDING;

        return this;
    }

    public WarriorSound descending() {
        this.effectType = SoundEffectType.DESCENDING;

        return this;
    }

    public WarriorSound randomPitch() {
        this.effectType = SoundEffectType.RANDOM;

        return this;
    }

    public WarriorSound setEffectType(SoundEffectType type) {
        this.effectType = type;
        return this;
    }

    public void play(Player... players) {
        for(Player p : players) {
            if(repeatTimes == 0) p.playSound(p.getLocation(), sound, volume, (float) note.getPitch());
            else {
                if(repeatDelay == 0L) repeatDelay = 1L;
                new BukkitRunnable() {

                    int current = 0;
                    final int baseNote = Arrays.asList(Note.values()).indexOf(note);
                    float pitch = (float) note.getPitch();

                    @Override
                    public void run() {
                        if(!p.isOnline()) cancel();
                        else {
                            if(current == repeatTimes - 1 || (baseNote + current) > Note.values().length || (baseNote - current) < 0) {
                                cancel();
                                return;
                            }

                            if(effectType == SoundEffectType.ASCENDING) pitch = (float) Note.values()[baseNote + current].getPitch();
                            if(effectType == SoundEffectType.DESCENDING) pitch = (float) Note.values()[(baseNote - current)].getPitch();
                            if(effectType == SoundEffectType.RANDOM) pitch = (float) MathUtil.randomElement(Note.values()).getPitch();

                            p.playSound(p.getLocation(), sound, volume, pitch);
                            current++;
                        }
                    }

                }.runTaskTimer(WarriorAPI.getService().getInstance(), 2L, repeatDelay);
            }
        }
    }

}
