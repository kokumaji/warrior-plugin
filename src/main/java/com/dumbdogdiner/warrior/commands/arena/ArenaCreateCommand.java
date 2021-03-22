package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.arena.ArenaBuilder;
import com.dumbdogdiner.warrior.api.arena.ArenaBuilderSession;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaCreateCommand implements SubCommand {
    @Override
    public String getAlias() {
        return "create";
    }

    @Override
    public String getSyntax() {
        return "/arena create <Name>";
    }

    @Override
    public String getPermission() {
        return "warrior.arena.create";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length < 2 || args[1].length() > 18) {
            return false;
        }

        Player player = (Player) sender;
        String name = TranslationUtil.capitalize(args[1]);
        ArenaBuilderSession session = new ArenaBuilderSession(new WarriorUser(player), name);

        Arena arena = ArenaManager.get(args[1]);

        if(arena != null) {
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_ALREADY_EXISTS, new HashMap<>() {
                {
                    put("ARENA", arena.getName());
                }
            });
            player.sendMessage(TranslationUtil.getPrefix() + msg);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.5f, 1f);
            return true;
        }

        ArenaBuilder.registerSession(player.getWorld(), session);
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }
}
