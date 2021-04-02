package com.dumbdogdiner.warrior.commands.misc;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.AsyncCommand;
import com.dumbdogdiner.warrior.api.command.CommandType;
import com.dumbdogdiner.warrior.api.command.ExitStatus;
import org.bukkit.command.CommandSender;

public class SymbolCommand extends AsyncCommand {

    public SymbolCommand() {
        super("symbols", Warrior.getInstance(), CommandType.ANY);
        setPermission("warrior.command.symbols");
    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        return null;
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
