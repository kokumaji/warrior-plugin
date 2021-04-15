package com.dumbdogdiner.warrior.commands.misc

import com.dumbdogdiner.warrior.api.command.AsyncCommandLegacy
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.command.ExitStatus
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.api.user.UserData
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings
import com.dumbdogdiner.warrior.api.translation.Symbols
import com.dumbdogdiner.warrior.api.translation.Placeholders
import com.dumbdogdiner.stickyapi.bukkit.util.PlayerSelector
import com.dumbdogdiner.warrior.api.translation.TimeUtil
import com.dumbdogdiner.warrior.api.util.MathUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.function.Supplier
import java.util.stream.Collectors

class StatisticsCommand : AsyncCommandLegacy("stats", Warrior.instance), TabCompleter {

    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<String>): ExitStatus {
        if (args.size > 1) return ExitStatus.ERROR_SYNTAX
        var player: OfflinePlayer? = null

        if (args.isEmpty()) player = sender as Player

        if (args.size == 1) {
            if (!TranslationUtil.validateUsername(args[0])) return ExitStatus.ERROR_GENERAL
            player = Bukkit.getOfflinePlayer(args[0])
        }

        if (player == null || !player.hasPlayedBefore()) {
            return ExitStatus.ERROR_GENERAL
        }

        val user: User = Warrior.userCache[(sender as Player).uniqueId]
        val data: UserData = (if (player.isOnline) UserData(Warrior.userCache[(player).uniqueId]) else Warrior.connection.getData(player.uniqueId))
            ?: return ExitStatus.ERROR_GENERAL
        val playerSettings: GeneralSettings =
            if (player.isOnline) Warrior.userCache[(player).uniqueId].settings else Warrior.connection.getUserSettings(player.uniqueId)
        if (playerSettings.privacyLevel == 3) {
            val msg = TranslationUtil.prettyMessage(
                " ",
                String.format(
                    "&4&l%s &cThis user's stats are hidden &4&l%s",
                    Symbols.HEAVY_BALLOT_X,
                    Symbols.HEAVY_BALLOT_X
                ),
                " "
            )
            sender.sendMessage(msg)
            return ExitStatus.EXECUTE_SUCCESS
        }
        val kdr = MathUtil.round(
            (if (data.getDeaths() < 1) data.getKills() else data.getKills()
                .toDouble() / data.getDeaths().toDouble()) as Double, 2
        )
        val lastJoin = if (player.isOnline) "§aOnline" else if (playerSettings.privacyLevel == 0) "last seen " + TimeUtil.timeAgo(
                data.getLastJoin(), false
            ) else "last seen " + TimeUtil.timeAgo(data.getLastJoin(), true)
        val totalTime = if (playerSettings.privacyLevel == 0) TimeUtil.formatDuration(data.getTotalTime()) else "(HIDDEN)"
        val firstJoin = if (playerSettings.privacyLevel < 2) TimeUtil.formatDate(user.settings.language, data.getFirstJoin()
        ) else "(HIDDEN)"

        val msg = Placeholders.applyPlaceholders(
            "&7{statistics.stats-for} %1s\n&8$lastJoin\n \n&7{statistics.total-kills} &8» &f&l%1d\n&7{statistics.total-deaths} &8» &f&l%2d\n&7{statistics.kdr} &8» &f&l%.2f\n \n&7{statistics.time-played} &8» &f&l%2s\n&7{statistics.first-join} &8» &f&l%3s\n ",
            user.settings.language
        )
        val formatted = String.format(msg, player.name, data.getKills(), data.getDeaths(), kdr, totalTime, firstJoin)
        val centeredMessage = TranslationUtil.prettyMessage(*formatted.split("\n").toTypedArray())
        sender.sendMessage(centeredMessage)
        return ExitStatus.EXECUTE_SUCCESS
    }

    override fun onPermissionError(sender: CommandSender, label: String, args: Array<String>) {}

    override fun onError(sender: CommandSender, label: String, args: Array<String>) {
        val player = sender as Player
        if (!TranslationUtil.validateUsername(args[0])) {
            val msg = TranslationUtil.prettyMessage(
                " ", String.format("&4&l%s &cInvalid Username! &4&l%s", Symbols.HEAVY_BALLOT_X, Symbols.HEAVY_BALLOT_X),
                " "
            )
            player.sendMessage(msg)
        } else {
            val msg = TranslationUtil.prettyMessage(
                " ",
                String.format(
                    "&4&l%s &cCould not get Statistics for Player &4%2s &4&l%s",
                    Symbols.HEAVY_BALLOT_X,
                    args[0],
                    Symbols.HEAVY_BALLOT_X
                ),
                " "
            )
            player.sendMessage(msg)
        }
        player.playSound(player.location, Sound.ENTITY_ARROW_HIT_PLAYER, 0.8f, 1f)
    }

    override fun onSyntaxError(sender: CommandSender, label: String, args: Array<String>) {}
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        var options: java.util.ArrayList<String>? = java.util.ArrayList()
        if (args.size == 1) {
            options = PlayerSelector.selectVisible(sender as Player).stream()
                .map { obj: Player -> obj.name }
                .filter { p: String -> p.startsWith(args[0]) }
                .collect(
                    Collectors.toCollection(
                        Supplier { ArrayList() })
                )
        }
        return options
    }

    init {
        tabCompleter = this
        permission = "warrior.command.statistics"
    }
}