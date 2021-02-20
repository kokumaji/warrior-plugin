package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ArenaLeaveCommand implements SubCommand {
    @Override
    public String getAlias() {
        return "leave";
    }

    @Override
    public String getSyntax() {
        return "/arena leave";
    }

    @Override
    public String getPermission() {
        return "warrior.arena.leave";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        ArenaManager.removeSession((Player)sender);
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
