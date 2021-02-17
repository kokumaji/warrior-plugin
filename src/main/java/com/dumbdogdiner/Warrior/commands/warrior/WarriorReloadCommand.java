package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.SubCommand;

import com.dumbdogdiner.Warrior.api.translation.Constants;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class WarriorReloadCommand implements SubCommand {

    @Override
    public String getAlias() {
        return "reload";
    }

    @Override
    public String getSyntax() {
        return "/warrior reload";
    }

    @Override
    public String getPermission() {
        return "warrior.command.reload";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Warrior.getInstance().reloadConfig();

        String msg = Warrior.getTranslator().translate(Constants.Lang.CMD_RELOAD_SUCCESS, true);
        sender.sendMessage(msg);
        return true;
    }


    public List<String> getArguments(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
