package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.api.sessions.SessionType;
import com.dumbdogdiner.warrior.user.UserCache;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
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
        User user = UserCache.get(((Player)sender).getUniqueId());
        if(user.getSession().getType() == SessionType.GAME) {
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_LEFT, new HashMap<>() {
                {
                    put("arena", args[1]);
                }
            }, user);

            user.sendMessage(TranslationUtil.getPrefix() + msg);
            user.setSession(new LobbySession(user.getUserId()));
            if(user.isSpectating()) user.setSpectating(false);
        }
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
