package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.*;
import com.dumbdogdiner.warrior.api.translation.ConsoleColor;
import com.dumbdogdiner.warrior.api.translation.Translator;
import com.dumbdogdiner.warrior.utils.DefaultMessages;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class ArenaCommand extends AsyncCommand implements TabCompleter {

    public ArenaCommand(String commmandName, Plugin plugin) {
        super(commmandName, plugin, CommandType.PLAYER_ONLY);
        setDescription("Manage/Join an Arena.");
        setTabCompleter(this);
    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        return ExitStatus.ERROR_SYNTAX;
    }

    @Override
    public void onPermissionError(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            p.sendMessage(TranslationUtil.prettyMessage(DefaultMessages.COMMAND_PERM_ERROR));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);
        }
    }

    @Override
    public void onError(CommandSender sender, String label, String[] args) {
        sender.sendMessage(DefaultMessages.COMMAND_CLIENT_ONLY);
    }

    @Override
    public void onSyntaxError(CommandSender sender, String label, String[] args) {
        if(args.length > 1) return;
        Translator t = Warrior.getTranslator();
        String msg = t.applyPlaceholders(DefaultMessages.COMMAND_SYNTAX_ERROR, new HashMap<>() {
            {
                put("HELP_CMD", "/warrior help");
            }
        });

        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(TranslationUtil.prettyMessage(msg));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);
        } else {
            sender.sendMessage(TranslationUtil.translateColor(ConsoleColor.RED + TranslationUtil.translateColor(msg.replace("\n", " ")) + ConsoleColor.RESET));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> options = new ArrayList<>();

        if(args.length == 1) {
            List<String> cmds = getSubCommands().values().stream().map(SubCommand::getAlias).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            if(!args[0].equals("")) {
                for(String s : cmds) {
                    if(s.toLowerCase().startsWith(args[0].toLowerCase())) options.add(s);
                }
            } else {
                options = cmds;
            }
        }

        if(args.length > 1) {
            SubCommand subCmd = getSubCommands().get(args[0]);

            if(!(subCmd == null)) {
                List<String> cmds = subCmd.getArguments(sender, args);
                if(cmds == null) return options;
                if(!args[1].equals("")) {
                    for(String s : cmds) {
                        if(s.toLowerCase().startsWith(args[1].toLowerCase())) options.add(s);
                    }
                } else {
                    options = cmds;
                }
            }
        }

        return options;
    }

}
