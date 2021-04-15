package com.dumbdogdiner.warrior.commands.warrior

import com.dumbdogdiner.warrior.api.command.SubCommand
import org.bukkit.command.CommandSender
import java.util.ArrayList

class WarriorHologramCommand : SubCommand {
    override fun getAlias(): String {
        return "hologram"
    }

    override fun getSyntax(): String {
        return "/warrior hologram <stats|leaderboard>"
    }

    override fun getPermission(): String {
        return "warrior.command.hologram"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        // TODO: implement hologram spawner
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return ArrayList()
    }
}