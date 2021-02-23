package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.GameState;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArenaSpectateCommand implements SubCommand {
    @Override
    public String getAlias() {
        return "spectate";
    }

    @Override
    public String getSyntax() {
        return "/arena spectate <arena>";
    }

    @Override
    public String getPermission() {
        return "warrior.arena.spectate";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Arena a = ArenaManager.get(args[1]);
        WarriorUser u = PlayerManager.get(((Player)sender).getUniqueId());

        if(a == null || u == null) return false;

        new BukkitRunnable() {

            @Override
            public void run() {
                u.setSession(new ArenaSession(u.getUserId(), a, GameState.SPECTATING));
                u.setSpectating(true);
            }

        }.runTask(Warrior.getInstance());

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        List<String> strings = new ArrayList<>();
        if(arguments.length == 2)
            strings = ArenaManager.getArenas().stream()
                        .map(Arena::getName)
                        .collect(Collectors.toList());

        return strings;
    }
}
