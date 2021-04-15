package com.dumbdogdiner.warrior.commands.misc

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.AsyncCommand
import com.dumbdogdiner.warrior.api.command.CommandType
import com.dumbdogdiner.warrior.api.command.ExitStatus
import org.bukkit.command.CommandSender

class SymbolCommand : AsyncCommand("symbols", Warrior.instance, CommandType.ANY) {
    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<String>): ExitStatus {
        return ExitStatus.EXECUTE_SUCCESS
    }

    override fun onPermissionError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onSyntaxError(sender: CommandSender, label: String, args: Array<String>) {}

    init {
        permission = "warrior.command.symbols"
    }
}