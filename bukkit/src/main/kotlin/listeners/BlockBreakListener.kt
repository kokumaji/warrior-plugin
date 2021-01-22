package listeners

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.objects.flags.BlockBreakFlag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.Plugin

class BlockBreakListener : Listener {

    val self: Plugin = Warrior().provider

    @EventHandler
    fun onBlockBreak(evt: BlockBreakEvent) {
        val flag: BlockBreakFlag = Warrior().worldFlags[0] as BlockBreakFlag

        if(!flag.value) evt.isCancelled = true

    }

}
