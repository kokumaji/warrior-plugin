package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.AsyncCommand
import com.dumbdogdiner.warrior.api.command.CommandType
import com.dumbdogdiner.warrior.api.command.ExitStatus
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.util.DefaultMessages
import java.util.HashMap
import com.dumbdogdiner.warrior.api.translation.ConsoleColor
import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.translation.Constants
import com.dumbdogdiner.warrior.api.translation.Translator
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.ArrayList
import java.util.Comparator
import java.util.stream.Collectors

class ArenaCommand : AsyncCommand("arena", Warrior.instance, CommandType.PLAYER_ONLY), TabCompleter {
    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<String>): ExitStatus {
        return ExitStatus.ERROR_SYNTAX
    }

    override fun onPermissionError(sender: CommandSender, label: String, args: Array<String>) {
        if (sender is Player) {
            val user: User = UserCache.get(sender.uniqueId)
            val msg: String = Warrior.getTranslator().translate(Constants.Lang.ERROR_PERM, user)
            user.sendMessage(TranslationUtil.prettyMessage(msg))
            user.playSound(Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f)
        }
    }

    override fun onError(sender: CommandSender, label: String, args: Array<String>) {
        sender.sendMessage(DefaultMessages.COMMAND_CLIENT_ONLY)
    }

    override fun onSyntaxError(sender: CommandSender, label: String, args: Array<String>) {
        if (args.size > 1) return
        val t: Translator = Warrior.getTranslator()
        var user: User? = null
        if (sender is Player) user = Warrior.userCache[sender.uniqueId]
        val msg = t.translate(Constants.Lang.ERROR_SYNTAX, object : HashMap<String?, String?>() {
            init {
                put("help_cmd", "/warrior help")
            }
        }, user)
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

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
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
        setDescription("Manage/Join an Arena.")
        tabCompleter = this
        permission = "warrior.command.arena"
    }
}