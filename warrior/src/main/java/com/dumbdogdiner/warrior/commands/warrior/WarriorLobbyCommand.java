package com.dumbdogdiner.warrior.commands.warrior;

import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.managers.LobbyManager;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WarriorLobbyCommand implements SubCommand {
    @Override
    public String getAlias() {
        return "setlobby";
    }

    @Override
    public String getSyntax() {
        return "/warrior setlobby";
    }

    @Override
    public String getPermission() {
        return "warrior.command.setlobby";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        LobbyManager.updateLocation(((Player)sender).getLocation());
        sender.sendMessage("Set Lobby Spawn to " + TranslationUtil.readableLocation(((Player) sender).getLocation(), true, true));
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
