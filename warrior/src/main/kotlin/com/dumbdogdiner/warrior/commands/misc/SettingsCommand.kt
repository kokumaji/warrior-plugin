package com.dumbdogdiner.warrior.commands.misc

import com.dumbdogdiner.warrior.api.command.AsyncCommandLegacy
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.ExitStatus
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.gui.settings.SettingsGUI
import com.dumbdogdiner.warrior.managers.GUIManager
import com.dumbdogdiner.warrior.user.User
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class SettingsCommand : AsyncCommandLegacy("settings", Warrior.instance), TabCompleter {

    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<String>): ExitStatus {
        val user: User = UserCache.get((sender as Player).uniqueId)
        val langGUI: SettingsGUI = GUIManager.get<SettingsGUI>(SettingsGUI::class.java)
        object : BukkitRunnable() {
            override fun run() {
                langGUI.open(user.bukkitPlayer)
            }
        }.runTask(Warrior.getInstance())
        return ExitStatus.EXECUTE_SUCCESS
    }

    override fun onPermissionError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onSyntaxError(sender: CommandSender, label: String, args: Array<String>) {}

    init {
        permission = "warrior.command.settings"
    }
}