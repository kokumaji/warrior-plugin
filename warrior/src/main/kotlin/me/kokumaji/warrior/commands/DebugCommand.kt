package com.dumbdogdiner.warrior.commands

import com.dumbdogdiner.warrior.WithWarriorPlugin
import com.dumbdogdiner.warrior.kits.WarriorKit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DebugCommand : CommandExecutor, WithWarriorPlugin {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val kit = getPlugin().kitContainer[WarriorKit::class.java]
        val user = getPlugin().userCache[(sender as Player).uniqueId]

        kit.setup(user)
        return true
    }
}