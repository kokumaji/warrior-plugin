package com.dumbdogdiner.warrior.commands.misc;

import com.dumbdogdiner.stickyapi.bukkit.util.PlayerSelector;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.AsyncCommandLegacy;
import com.dumbdogdiner.warrior.api.command.ExitStatus;
import com.dumbdogdiner.warrior.api.user.UserData;
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
        String lastJoin = player.isOnline() ? "§aOnline" : playerSettings.getPrivacyLevel() == 0 ? "last seen " + TranslationUtil.timeAgo(data.getLastJoin(), false)
                : "last seen " + TranslationUtil.timeAgo(data.getLastJoin(), true);
        String totalTime = playerSettings.getPrivacyLevel() == 0 ? TranslationUtil.formatDuration(data.getTotalTime())
                                                                 : "(HIDDEN)";
        String firstJoin = playerSettings.getPrivacyLevel() < 2 ? TranslationUtil.toDate(data.getFirstJoin()) : "(HIDDEN)";

        String centeredMessage = TranslationUtil.prettyMessage( " ",
              "&7Statistics for " + player.getName(),
                    "&8" + lastJoin,
                    " ",
                    "&7Total Kills &8» &f&l" + data.getKills(),
                    "&7Total Deaths &8» &f&l" + data.getDeaths(),
                    "&7KDR &8» &f&l" + kdr,
                    " ",
                    "&7Time Played &8» &f&l" + totalTime,
                    "&7First Join &8» &f&l" + firstJoin,
                    " "
        );

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
