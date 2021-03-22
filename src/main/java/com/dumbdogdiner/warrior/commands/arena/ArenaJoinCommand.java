package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
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

        WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());
        Arena a = ArenaManager.get(args[1]);

        if(a == null) {
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_DOESNT_EXIST, new HashMap<>() {
                {
                    put("ARENA", args[1]);
                }
            });
            user.sendMessage(TranslationUtil.getPrefix() + msg);
            user.playSound(Sound.ENTITY_ITEM_BREAK, 0.5f, 1f);
            return true;
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                user.setSession(new ArenaSession(user.getUserId(), a));
                String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_TELEPORT, new HashMap<String, String>() {
                    {
                        put("ARENA", a.getName());
                    }
                });

                user.sendMessage(TranslationUtil.getPrefix() + msg);
                user.playSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1f);
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
