package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.AsyncCommand;
import com.dumbdogdiner.Warrior.api.command.ExitStatus;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.api.translation.ConsoleColor;
import com.dumbdogdiner.Warrior.api.translation.Translator;
import com.dumbdogdiner.Warrior.utils.DefaultMessages;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
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
        super(commmandName, plugin);
        setDescription("Manage/Join an Arena.");
        setPermission("warrior.command.arena");
        setTabCompleter(this);
    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        Translator t = Warrior.getTranslator();

        if(!(sender instanceof Player))
            return ExitStatus.GENERAL_ERROR;

        if(!sender.hasPermission(Objects.requireNonNull(getPermission()))) return ExitStatus.PERMISSION_ERROR;

        if(args.length == 0) {
            return ExitStatus.SYNTAX_ERROR;
        } else {
            String arg = args[0].toLowerCase();
            if(getSubCommands().get(arg) != null) {
                SubCommand cmd = getSubCommands().get(arg);

                if(!sender.hasPermission(cmd.getPermission())) return ExitStatus.PERMISSION_ERROR;

                if(!cmd.execute(sender, commandLabel, args)) {
                    String msg = t.applyPlaceholders(DefaultMessages.SUBCMD_SYNTAX, new HashMap<>() {

                        {
                            put("SUB_SYNTAX", cmd.getSyntax());
                        }
                    });

                    Player p = (Player) sender;
                    p.sendMessage(TranslationUtil.prettyMessage(msg));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);

                    return ExitStatus.SYNTAX_ERROR;
                }

            } else {
                return ExitStatus.SYNTAX_ERROR;
            }
        }

        return ExitStatus.EXECUTE_SUCCESS;
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
