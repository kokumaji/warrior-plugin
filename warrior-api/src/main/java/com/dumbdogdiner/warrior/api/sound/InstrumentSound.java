package com.dumbdogdiner.warrior.api.sound;

import lombok.Getter;
import org.bukkit.Instrument;
import org.bukkit.Sound;

public enum InstrumentSound {

    PIANO(Sound.BLOCK_NOTE_BLOCK_HARP),

    BASS_DRUM(Sound.BLOCK_NOTE_BLOCK_BASEDRUM),

    SNARE_DRUM(Sound.BLOCK_NOTE_BLOCK_SNARE),

    STICKS(Sound.BLOCK_NOTE_BLOCK_HAT),

    BASS_GUITAR(Sound.BLOCK_NOTE_BLOCK_BASS),

    FLUTE(Sound.BLOCK_NOTE_BLOCK_FLUTE),

    BELL(Sound.BLOCK_NOTE_BLOCK_BELL),

    GUITAR(Sound.BLOCK_NOTE_BLOCK_GUITAR),

    CHIME(Sound.BLOCK_NOTE_BLOCK_CHIME),

    XYLOPHONE(Sound.BLOCK_NOTE_BLOCK_XYLOPHONE),

    IRON_XYLOPHONE(Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE),

    COW_BELL(Sound.BLOCK_NOTE_BLOCK_COW_BELL),

    DIDGERIDOO(Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO),

    BIT(Sound.BLOCK_NOTE_BLOCK_BIT),

    BANJO(Sound.BLOCK_NOTE_BLOCK_BANJO),

    PLING(Sound.BLOCK_NOTE_BLOCK_PLING);

    @Getter
    private final Sound sound;

    InstrumentSound(Sound sound) {
        this.sound = sound;
    }

    public static Sound fromInstrument(Instrument instrument) {
        return valueOf(instrument.toString()).getSound();
    }
}
