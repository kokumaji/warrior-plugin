package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.GameState;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
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

        if(u.getSession() instanceof ArenaSession) {
            if(((ArenaSession)u.getSession()).getState() == GameState.IN_GAME) return false;
        }

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
