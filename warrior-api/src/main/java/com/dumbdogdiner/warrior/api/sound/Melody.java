package com.dumbdogdiner.warrior.api.sound;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import lombok.Getter;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class Melody {

    @Getter
    private final Note[] notes;
    @Getter
    private final Instrument instrument;

    @Getter
    private final long speed;

    @Getter
    private final float volume;

    @Getter
    private final ArrayList<Melody> accompaniment = new ArrayList<>();

    @Getter
    private boolean visualizeNote;

    @Getter
    private Location visualizeLoc;

    public Melody(Instrument instrument, long speed, float volume, Note... notes) {
        this.notes = notes;
        this.instrument = instrument;
        this.speed = speed;
        this.volume = volume;
    }

    public Melody visualize(boolean bool, Location loc) {
        this.visualizeNote = bool;
        this.visualizeLoc = loc;

        return this;
    }

    public Melody addAccompaniment(Melody... melodies) {
        accompaniment.addAll(Arrays.asList(melodies));
        return this;
    }

    public void play(Player player) {

        for(Melody s : accompaniment) {
            s.play(player);
        }

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
                    Sound sound = InstrumentSound.fromInstrument(instrument);
                    if(notes[pointer] != null) {
                        player.playSound(loc, sound, volume, (float) notes[pointer].getPitch());
                        if(visualizeNote) {
                            Location vLoc = visualizeLoc;
                            if(vLoc == null) vLoc = loc.add(0, 2, 0);
                            player.spawnParticle(Particle.NOTE, vLoc, 0, notes[pointer].getColorDelta(), 0, 0);
                        }
                    }

                    pointer++;
                }
            }
        }.runTaskTimer(WarriorAPI.getService().getInstance(), 2L, speed);
    }
}
