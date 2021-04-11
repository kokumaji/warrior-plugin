package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
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
            User user = PlayerManager.get(((Player)sender).getUniqueId());
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_REMOVE, new HashMap<>() {
                {
                    put("arena", args[1]);
                }
            }, user);
            sender.sendMessage(TranslationUtil.getPrefix() + msg);
            ArenaManager.remove(a);
        }

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
