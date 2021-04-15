package com.dumbdogdiner.warrior.commands.warrior

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.AsyncCommand
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.util.DefaultMessages
import com.dumbdogdiner.warrior.api.translation.Placeholders
import java.util.HashMap
import com.dumbdogdiner.warrior.api.translation.ConsoleColor
import com.dumbdogdiner.warrior.api.command.ExitStatus
import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.translation.Translator
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.ArrayList
import java.util.Comparator
import java.util.stream.Collectors

class WarriorCommand : AsyncCommand("warrior", Warrior.instance), TabCompleter {
    override fun onPermissionError(sender: CommandSender, label: String, args: Array<String>) {
        if (sender is Player) {
            val p = sender
            p.sendMessage(TranslationUtil.prettyMessage(DefaultMessages.COMMAND_PERM_ERROR))
            p.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f)
        }
    }

    override fun onError(sender: CommandSender, label: String, args: Array<String>) {
        sender.sendMessage(DefaultMessages.COMMAND_GENERAL_ERROR)
    }

    override fun onSyntaxError(sender: CommandSender, label: String, args: Array<String>) {
        if (args.size > 1) return
        val t: Translator = Warrior.translator
        val msg =
            Placeholders.applyPlaceholders(DefaultMessages.COMMAND_SYNTAX_ERROR, object : HashMap<String?, String?>() {
                init {
                    put("HELP_CMD", "/warrior help")
                }
            })
        if (sender is Player) {
            val p = sender
            p.sendMessage(TranslationUtil.prettyMessage(msg))
            p.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f)
        } else {
            sender.sendMessage(
                TranslationUtil.translateColor(
                    ConsoleColor.RED + TranslationUtil.translateColor(
                        msg.replace(
                            "\n",
                            " "
                        )
                    ) + ConsoleColor.RESET
                )
            )
        }
    }

    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<String>): ExitStatus {
        val t: Translator = Warrior.translator
        val helpCmd = subCommands["help"]
        helpCmd?.execute(sender, commandLabel, args)
        return ExitStatus.EXECUTE_SUCCESS
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        if (args.size == 1) return subCommands.values.stream().map { obj: SubCommand -> obj.alias }
            .sorted(Comparator.naturalOrder()).collect(Collectors.toList())
        if (args.size > 1) {
            val subCmd = subCommands[args[0]]
            if (subCmd != null) {
                return subCmd.getArguments(sender, args)
            }
        }
        return ArrayList()
    }

    init {
        setDescription("Plugin Main Command")
        permission = "warrior.command.admin"
        tabCompleter = this
    }
}