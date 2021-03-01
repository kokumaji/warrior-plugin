package com.dumbdogdiner.Warrior.api.arena.gameflags.implementation;

import com.dumbdogdiner.Warrior.api.arena.gameflags.types.DoubleFlag;
import org.jetbrains.annotations.NotNull;

public class MaxHealthFlag extends DoubleFlag<MaxHealthFlag> {

    public static final MaxHealthFlag DEFAULT_HEALTH = new MaxHealthFlag(20D);

    public MaxHealthFlag(double value) {
        super("max-health", value, 0.5, 20, "Set the max health of a player.");
    }

    @Override
    public MaxHealthFlag flagOf(@NotNull Double value) {
        return new MaxHealthFlag(value);
    }
}
