package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ArenaJoinCommand implements SubCommand {
    @Override
    public String getAlias() {
        return "join";
    }

    @Override
    public String getSyntax() {
        return "/arena join <Arena>";
    }

    @Override
    public String getPermission() {
        return "warrior.arena.join";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Arena a = ArenaManager.get(args[1]);
        ((Player)sender).teleport(a.getSpawn());
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
