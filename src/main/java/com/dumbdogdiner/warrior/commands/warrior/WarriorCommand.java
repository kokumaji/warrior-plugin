package com.dumbdogdiner.warrior.commands.warrior;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.AsyncCommand;
import com.dumbdogdiner.warrior.api.command.ExitStatus;
import com.dumbdogdiner.warrior.api.command.SubCommand;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
        if(sender instanceof Player) {
            Player p = (Player) sender;

            p.sendMessage(TranslationUtil.prettyMessage(DefaultMessages.COMMAND_PERM_ERROR));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);
        }
    }

    @Override
    public void onError(CommandSender sender, String label, String[] args) {
        sender.sendMessage(DefaultMessages.COMMAND_GENERAL_ERROR);
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
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        Translator t = Warrior.getTranslator();

        SubCommand helpCmd = getSubCommands().get("help");
        if(helpCmd != null) helpCmd.execute(sender, commandLabel, args);

        return ExitStatus.EXECUTE_SUCCESS;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1)
            return getSubCommands().values().stream().map(SubCommand::getAlias).sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        if(args.length > 1) {
            SubCommand subCmd = getSubCommands().get(args[0]);

            if(!(subCmd == null)) {
                return subCmd.getArguments(sender, args);
            }
        }

        return new ArrayList<>();
    }

}
