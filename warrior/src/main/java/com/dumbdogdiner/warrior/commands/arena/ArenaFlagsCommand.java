package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.arena.gameflags.FlagContainer;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ArenaFlagsCommand implements SubCommand {

    @Override
    public String getAlias() {
        return "flags";
    }

    @Override
    public String getSyntax() {
        return "/arena flags <arena>";
    }

    @Override
    public String getPermission() {
        return "warrior.flags.list";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Arena a = ArenaManager.get(args[1]);
        if(a == null) return false;

        FlagContainer container = a.getFlags();

        String msg = String.format("%s §7Flags for arena §b%2s§7:\n(%3s)",
                     TranslationUtil.getPrefix(), a.getName(), container.toString());

        sender.sendMessage(msg);
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }
}
