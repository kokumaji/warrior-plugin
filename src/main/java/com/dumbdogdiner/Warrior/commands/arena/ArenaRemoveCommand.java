package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ArenaRemoveCommand implements SubCommand {
    @Override
    public String getAlias() {
        return "remove";
    }

    @Override
    public String getSyntax() {
        return "/arena remove <Arena>";
    }

    @Override
    public String getPermission() {
        return "warrior.arena.remove";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length != 2) return false;
        Arena a = ArenaManager.get(args[1]);

        if(a != null) {
            sender.sendMessage("Removed Arena " + a.getName());
            ArenaManager.remove(a);
        }

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
