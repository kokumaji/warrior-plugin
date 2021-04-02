package com.dumbdogdiner.warrior.commands.misc;

import com.dumbdogdiner.stickyapi.bukkit.util.PlayerSelector;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.AsyncCommandLegacy;
import com.dumbdogdiner.warrior.api.command.ExitStatus;
import com.dumbdogdiner.warrior.api.translation.Placeholders;
import com.dumbdogdiner.warrior.api.translation.TimeUtil;
import com.dumbdogdiner.warrior.api.user.UserData;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings;
import com.dumbdogdiner.warrior.api.translation.Symbols;
import com.dumbdogdiner.warrior.api.util.MathUtil;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class StatisticsCommand extends AsyncCommandLegacy implements TabCompleter {

    public StatisticsCommand(String commmandName, Plugin plugin) {
        super(commmandName, plugin);
        setTabCompleter(this);
        setPermission("warrior.command.statistics");
    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        if(args.length > 1) return ExitStatus.ERROR_SYNTAX;
        OfflinePlayer player = null;

        if(args.length == 0) player = (Player) sender;

        if(args.length == 1) {
            if(!TranslationUtil.validateUsername(args[0])) return ExitStatus.ERROR_GENERAL;
            player = Bukkit.getOfflinePlayer(args[0]);
        }

        if(player == null || !player.hasPlayedBefore()) {
            return ExitStatus.ERROR_GENERAL;
        }

        WarriorUser user = PlayerManager.get(((Player) sender).getUniqueId());
        UserData data = player.isOnline() ? new UserData(PlayerManager.get(player.getUniqueId()))
                                                : Warrior.getConnection().getData(player.getUniqueId());

        if(data == null) return ExitStatus.ERROR_GENERAL;

        GeneralSettings playerSettings = player.isOnline() ? PlayerManager.get(player.getUniqueId()).getSettings()
                : Warrior.getConnection().getUserSettings(player.getUniqueId());

        if(playerSettings.getPrivacyLevel() == 3) {
            String msg = TranslationUtil.prettyMessage(" ", String.format("&4&l%s &cThis user's stats are hidden &4&l%s", Symbols.HEAVY_BALLOT_X, Symbols.HEAVY_BALLOT_X), " ");
            sender.sendMessage(msg);
            return ExitStatus.EXECUTE_SUCCESS;
        }

        double kdr = MathUtil.round(data.getDeaths() < 1 ? data.getKills() : (double) data.getKills() / (double) data.getDeaths(), 2);
        String lastJoin = player.isOnline() ? "§aOnline" : playerSettings.getPrivacyLevel() == 0 ? "last seen " + TimeUtil.timeAgo(data.getLastJoin(), false)
                : "last seen " + TimeUtil.timeAgo(data.getLastJoin(), true);
        String totalTime = playerSettings.getPrivacyLevel() == 0 ? TimeUtil.formatDuration(data.getTotalTime())
                                                                 : "(HIDDEN)";
        String firstJoin = playerSettings.getPrivacyLevel() < 2 ? TimeUtil.formatDate(user.getSettings().getLanguage(), data.getFirstJoin()) : "(HIDDEN)";

        String msg = Placeholders.applyPlaceholders("&7{statistics.stats-for} %1s\n&8" + lastJoin + "\n \n&7{statistics.total-kills} &8» &f&l%1d\n&7{statistics.total-deaths} &8» &f&l%2d\n&7{statistics.kdr} &8» &f&l%.2f\n \n&7{statistics.time-played} &8» &f&l%2s\n&7{statistics.first-join} &8» &f&l%3s\n ", user.getSettings().getLanguage());
        String formatted = String.format(msg, player.getName(), data.getKills(), data.getDeaths(), kdr, totalTime, firstJoin);
        String centeredMessage = TranslationUtil.prettyMessage(formatted.split("\n"));

        sender.sendMessage(centeredMessage);

        return ExitStatus.EXECUTE_SUCCESS;
    }

    @Override
    public void onPermissionError(CommandSender sender, String label, String[] args) {

    }

    @Override
    public void onError(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if(!TranslationUtil.validateUsername(args[0])) {
            String msg = TranslationUtil.prettyMessage(" ",
                    String.format("&4&l%s &cInvalid Username! &4&l%s", Symbols.HEAVY_BALLOT_X, Symbols.HEAVY_BALLOT_X),
            " ");
            player.sendMessage(msg);
        } else {
            String msg = TranslationUtil.prettyMessage(" ",
                    String.format("&4&l%s &cCould not get Statistics for Player &4%2s &4&l%s", Symbols.HEAVY_BALLOT_X, args[0], Symbols.HEAVY_BALLOT_X),
            " ");
            player.sendMessage(msg);
        }

        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.8f, 1f);

    }

    @Override
    public void onSyntaxError(CommandSender sender, String label, String[] args) {

    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> options = new ArrayList<>();
        if(args.length == 1) {
            options = PlayerSelector.selectVisible((Player) sender).stream()
                    .map(Player::getName)
                    .filter(p -> p.startsWith(args[0]))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return options;
    }
}
