package com.dumbdogdiner.warrior.api.arena.gameflags.implementation;

import com.dumbdogdiner.warrior.api.arena.gameflags.types.BooleanFlag;
import org.jetbrains.annotations.NotNull;

public class BlockPlaceFlag extends BooleanFlag<BlockPlaceFlag> {

    public static final BlockPlaceFlag PLACE_ALLOW = new BlockPlaceFlag(true);
    public static final BlockPlaceFlag PLACE_DENY = new BlockPlaceFlag(false);

    private BlockPlaceFlag(boolean value) {
        super("block-place", value, "Allow/Deny placing blocks within the arena");
    }

    @Override
    protected BlockPlaceFlag flagOf(@NotNull Boolean value) {
        return value ? PLACE_ALLOW : PLACE_DENY;
    }
}

