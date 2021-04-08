package com.dumbdogdiner.warrior.commands.kit;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.AsyncCommand;
import com.dumbdogdiner.warrior.api.command.CommandType;
import com.dumbdogdiner.warrior.api.command.ExitStatus;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KitCommand extends AsyncCommand implements TabCompleter {

    public KitCommand(String commmandName) {
        super(commmandName, Warrior.getInstance(), CommandType.PLAYER_ONLY);
        setTabCompleter(this);
        setPermission("warrior.command.kit");
    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {

        return ExitStatus.EXECUTE_SUCCESS;
    }

    @Override
    public void onPermissionError(CommandSender sender, String label, String[] args) {

    }

    @Override
    public void onError(CommandSender sender, String label, String[] args) {

    }

    @Override
    public void onSyntaxError(CommandSender sender, String label, String[] args) {

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
