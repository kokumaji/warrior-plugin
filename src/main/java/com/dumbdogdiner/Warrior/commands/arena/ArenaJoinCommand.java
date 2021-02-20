package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        if(args.length < 2 || args[1].length() > 18) {
            return false;
        }

        Player player = (Player) sender;
        Arena a = ArenaManager.get(args[1]);

        if(a == null) {
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_DOESNT_EXIST, new HashMap<>() {
                {
                    put("ARENA", args[1]);
                }
            });
            player.sendMessage(TranslationUtil.getPrefix() + msg);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.5f, 1f);
            return true;
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                ((Player)sender).teleport(a.getSpawn());
                String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_TELEPORT, new HashMap<String, String>() {
                    {
                        put("ARENA", args[1]);
                    }
                });
                player.sendMessage(TranslationUtil.getPrefix() + msg);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1f);
            }

        }.runTask(Warrior.getInstance());

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        List<String> strings = new ArrayList<>();

        if(arguments.length == 2) strings = ArenaManager.getArenas().stream().map(Arena::getName).collect(Collectors.toList());

        return strings;

    }
}
