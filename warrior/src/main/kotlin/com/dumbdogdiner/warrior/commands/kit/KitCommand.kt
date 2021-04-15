package com.dumbdogdiner.warrior.commands.kit

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.AsyncCommand
import com.dumbdogdiner.warrior.api.command.CommandType
import com.dumbdogdiner.warrior.api.command.ExitStatus
import com.dumbdogdiner.warrior.api.command.SubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.ArrayList
import java.util.Comparator
import java.util.stream.Collectors

class KitCommand : AsyncCommand("kit", Warrior.instance, CommandType.PLAYER_ONLY), TabCompleter {
    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<String>): ExitStatus {
        return ExitStatus.EXECUTE_SUCCESS
    }

    override fun onPermissionError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onSyntaxError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        var options: MutableList<String> = ArrayList()
        if (args.size == 1) {
            val cmds =
                subCommands.values.stream().map { obj: SubCommand -> obj.alias }.sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList())
            if (args[0] != "") {
                for (s in cmds) {
                    if (s.toLowerCase().startsWith(args[0].toLowerCase())) options.add(s)
                }
            } else {
                options = cmds
            }
        }
        if (args.size > 1) {
            val subCmd = subCommands[args[0]]
            if (subCmd != null) {
                val cmds = subCmd.getArguments(sender, args) ?: return options
                if (args[1] != "") {
                    for (s in cmds) {
                        if (s.toLowerCase().startsWith(args[1].toLowerCase())) options.add(s)
                    }
                } else {
                    options = cmds
                }
            }
        }
        return options
    }

    init {
        tabCompleter = this
        permission = "warrior.command.kit"
    }
}