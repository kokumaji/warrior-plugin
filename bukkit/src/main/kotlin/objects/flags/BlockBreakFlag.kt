package com.dumbdogdiner.warrior.objects.flags

import com.dumbdogdiner.warrior.objects.flags.types.BooleanFlag

class BlockBreakFlag(value: Boolean) : BooleanFlag<BlockBreakFlag>(value) {

    private val allowBreak = BlockBreakFlag(true)
    private val denyBreak = BlockBreakFlag(false)

    override fun flagOf(value: Boolean): BlockBreakFlag {
        return if (value) allowBreak else denyBreak
    }
}