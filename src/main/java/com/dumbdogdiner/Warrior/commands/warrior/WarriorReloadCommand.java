package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.SubCommand;

import org.bukkit.command.CommandSender;

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

        String msg = Warrior.getInstance().getTranslator().translate("command-messages.reload-success", true);
        sender.sendMessage(msg);
        return true;
    }
}
