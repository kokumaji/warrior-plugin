package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.ArenaBuilder;
import com.dumbdogdiner.Warrior.api.arena.ArenaSession;
import com.dumbdogdiner.Warrior.api.command.AsyncCommand;
import com.dumbdogdiner.Warrior.api.command.ExitStatus;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class ArenaCommand extends AsyncCommand {

    public ArenaCommand(String commmandName, Plugin plugin) {
        super(commmandName, plugin);
        setDescription("Plugin Main Command");
        setPermission("warrior.command.admin");
        setTabCompleter(this);
    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;
        if(args[0].equalsIgnoreCase("create")) {
            String name = args[1];
            ArenaSession session = new ArenaSession(new WarriorUser(player), name);

            //start session
            ArenaBuilder.registerSession(player.getWorld(), session);

        } else if(args[0].equalsIgnoreCase("list")) {
            List<String> arenaNames = ArenaManager.getArenas().stream().map(Arena::getName).collect(Collectors.toList());
            player.sendMessage(String.join(",", arenaNames));
        }
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

}
