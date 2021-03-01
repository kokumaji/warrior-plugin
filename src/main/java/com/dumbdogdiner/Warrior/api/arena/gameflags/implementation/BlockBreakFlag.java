package com.dumbdogdiner.Warrior.api.arena.gameflags.implementation;

import com.dumbdogdiner.Warrior.api.arena.gameflags.types.BooleanFlag;
import org.jetbrains.annotations.NotNull;

public class BlockBreakFlag extends BooleanFlag<BlockBreakFlag> {

    public static final BlockBreakFlag BREAK_ALLOW = new BlockBreakFlag(true);
    public static final BlockBreakFlag BREAK_DENY = new BlockBreakFlag(false);

    private BlockBreakFlag(boolean value) {
        super("block-break", value, "Allow/Deny breaking blocks within the arena");
    }

    @Override
    protected BlockBreakFlag flagOf(@NotNull Boolean value) {
            return value ? BREAK_ALLOW : BREAK_DENY;
    }
}
