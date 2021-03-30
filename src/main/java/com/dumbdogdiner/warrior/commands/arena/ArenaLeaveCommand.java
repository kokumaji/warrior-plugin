package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.api.sessions.SessionType;
import com.dumbdogdiner.warrior.managers.PlayerManager;
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
        WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());
        if(user.getSession().getType() == SessionType.GAME) {
            long time = user.getSession().getTimestamp();
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
