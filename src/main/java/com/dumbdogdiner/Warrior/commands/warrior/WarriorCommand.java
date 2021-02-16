package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.AsyncCommand;
import com.dumbdogdiner.Warrior.api.command.ExitStatus;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class WarriorCommand extends AsyncCommand implements TabCompleter {

    public WarriorCommand(String commmandName, Plugin plugin) {
        super(commmandName, plugin);
        setDescription("Plugin Main Command");
        setPermission("warrior.command.admin");
        setTabCompleter(this);
    }

    @Override
    public void onPermissionError(CommandSender sender, String label, String[] args) {
        String[] permissionMessage = {
                Warrior.getInstance().COMMAND_HEADER,
                " ",
                "&cYou don't have the required permissions",
                "&cto execute this command!",
                " ",
                "&8" + TranslationUtil.HL,
                " "
        };

        if(sender instanceof Player) {
            Player p = (Player) sender;

            TranslationUtil.centerMessage(p, permissionMessage);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);
        }
    }

    @Override
    public void onError(CommandSender sender, String label, String[] args) {
        sender.sendMessage(TranslationUtil.translateColor("&cAn Error occurred while trying to execute this command. If this issue persists, please contact the plugin author!"));
    }

    @Override
    public void onSyntaxError(CommandSender sender, String label, String[] args) {
        if(args.length > 1) return;

        String[] syntaxMessage = {
                Warrior.getInstance().COMMAND_HEADER,
                " ",
                "&cWrong Command Syntax!",
                "&7Type /warrior help for commands!",
                " ",
                "&8" + TranslationUtil.HL,
                " "
        };

        if(sender instanceof Player) {
            Player p = (Player) sender;

            TranslationUtil.centerMessage(p, syntaxMessage);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);
        } else {
            sender.sendMessage(TranslationUtil.translateColor("&cWrong Command Syntax! &7Type /warrior help for commands!"));
        }

    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(!p.hasPermission(Objects.requireNonNull(getPermission()))) return ExitStatus.PERMISSION_ERROR;

            if(args.length == 0) {
                SubCommand helpCmd = getSubCommands().get("help");
                if(helpCmd != null) helpCmd.execute(sender, commandLabel, args);
            } else {
                String arg = args[0].toLowerCase();
                if(getSubCommands().get(arg) != null) {
                    SubCommand cmd = getSubCommands().get(arg);

                    if(!p.hasPermission(cmd.getPermission())) return ExitStatus.PERMISSION_ERROR;

                    if(!cmd.execute(sender, commandLabel, args)) {
                        String[] syntaxMessage = {
                                Warrior.getInstance().COMMAND_HEADER,
                                " ",
                                "&cWrong Command Syntax!",
                                "&7" + cmd.getSyntax(),
                                " ",
                                "&8" + TranslationUtil.HL,
                                " "
                        };

                        TranslationUtil.centerMessage(p, syntaxMessage);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);
                        return ExitStatus.SYNTAX_ERROR;
                    }

                    return ExitStatus.SYNTAX_ERROR;
                }
            }
        }

        return ExitStatus.GENERAL_ERROR;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1)
            return getSubCommands().values().stream().map(SubCommand::getAlias).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        return new ArrayList<>();
    }

}
