package com.dumbdogdiner.warrior.api.sound;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.sound.Note;
import lombok.Getter;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Song {

    @Getter
    private final Note[] notes;
    @Getter
    private final Instrument instrument;

    @Getter
    private final long speed;

    @Getter
    private final float volume;

    public Song(Instrument instrument, long speed, float volume, Note... notes) {
        this.notes = notes;
        this.instrument = instrument;
        this.speed = speed;
        this.volume = volume;
    }

    public void play(Player player) {
        new BukkitRunnable() {

            int pointer = 0;

            @Override
            public void run() {
                if(!player.isOnline()) cancel();
                else {
                    if(pointer > notes.length - 1) {
                        cancel();
                        return;
                    }
                    Location loc = player.getLocation();
                    String soundString = String.format("BLOCK_NOTE_BLOCK_%s", instrument.toString());
                    Sound sound = Sound.valueOf(soundString);
                    player.playSound(loc, sound, volume, (float) notes[pointer].getPitch());
                    pointer++;
                }
            }
        }.runTaskTimer(Warrior.getInstance(), 2L, speed);
    }



}
