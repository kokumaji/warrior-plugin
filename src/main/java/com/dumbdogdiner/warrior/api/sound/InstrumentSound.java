package com.dumbdogdiner.warrior.api.sound;

import lombok.Getter;
import org.bukkit.Instrument;
import org.bukkit.Sound;

public enum InstrumentSound {
    PIANO(Sound.BLOCK_NOTE_BLOCK_HARP),
    /**
     * Bass drum is normally played when a note block is on top of a
     * stone-like block.
     */
    BASS_DRUM(Sound.BLOCK_NOTE_BLOCK_BASEDRUM),
    /**
     * Snare drum is normally played when a note block is on top of a sandy
     * block.
     */
    SNARE_DRUM(Sound.BLOCK_NOTE_BLOCK_SNARE),
    /**
     * Sticks are normally played when a note block is on top of a glass
     * block.
     */
    STICKS(Sound.BLOCK_NOTE_BLOCK_HAT),
    /**
     * Bass guitar is normally played when a note block is on top of a wooden
     * block.
     */
    BASS_GUITAR(Sound.BLOCK_NOTE_BLOCK_BASS),
    /**
     * Flute is normally played when a note block is on top of a clay block.
     */
    FLUTE(Sound.BLOCK_NOTE_BLOCK_FLUTE),
    /**
     * Bell is normally played when a note block is on top of a gold block.
     */
    BELL(Sound.BLOCK_NOTE_BLOCK_BELL),
    /**
     * Guitar is normally played when a note block is on top of a woolen block.
     */
    GUITAR(Sound.BLOCK_NOTE_BLOCK_GUITAR),
    /**
     * Chime is normally played when a note block is on top of a packed ice
     * block.
     */
    CHIME(Sound.BLOCK_NOTE_BLOCK_CHIME),
    /**
     * Xylophone is normally played when a note block is on top of a bone block.
     */
    XYLOPHONE(Sound.BLOCK_NOTE_BLOCK_XYLOPHONE),
    /**
     * Iron Xylophone is normally played when a note block is on top of a iron block.
     */
    IRON_XYLOPHONE(Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE),
    /**
     * Cow Bell is normally played when a note block is on top of a soul sand block.
     */
    COW_BELL(Sound.BLOCK_NOTE_BLOCK_COW_BELL),
    /**
     * Didgeridoo is normally played when a note block is on top of a pumpkin block.
     */
    DIDGERIDOO(Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO),
    /**
     * Bit is normally played when a note block is on top of a emerald block.
     */
    BIT(Sound.BLOCK_NOTE_BLOCK_BIT),
    /**
     * Banjo is normally played when a note block is on top of a hay block.
     */
    BANJO(Sound.BLOCK_NOTE_BLOCK_BANJO),
    /**
     * Pling is normally played when a note block is on top of a glowstone block.
     */
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
