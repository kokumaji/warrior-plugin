package com.dumbdogdiner.warrior.commands.warrior

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarriorLobbyCommand : SubCommand {
    override fun getAlias(): String {
        return "setlobby"
    }

    override fun getSyntax(): String {
        return "/warrior setlobby"
    }

    override fun getPermission(): String {
        return "warrior.command.setlobby"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        Warrior.lobbyManager.updateLocation((sender as Player).location)
        sender.sendMessage(
            "Set Lobby Spawn to " + TranslationUtil.readableLocation(
                sender.location,
                true,
                true
            )
        )
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return listOf()
    }
}