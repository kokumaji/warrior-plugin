package com.dumbdogdiner.warrior.commands.warrior;

import com.dumbdogdiner.warrior.api.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class WarriorHologramCommand implements SubCommand {

    @Override
    public String getAlias() {
        return "hologram";
    }

    @Override
    public String getSyntax() {
        return "/warrior hologram <stats|leaderboard>";
    }

    @Override
    public String getPermission() {
        return "warrior.command.hologram";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }

}
