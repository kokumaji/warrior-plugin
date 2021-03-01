package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.gameflags.FlagContainer;
import com.dumbdogdiner.Warrior.api.arena.gameflags.GameFlag;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import net.md_5.bungee.api.ChatColor;
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
        return "/arena flags <Arena>";
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

        List<String> values = new ArrayList<>();

        for(GameFlag<?, ?> flag : container.getFlags().values()) {
            String color = ChatColor.WHITE.toString();
            if(flag.getValue() instanceof Boolean) {
                if(((Boolean) flag.getValue())) color = ChatColor.GREEN.toString();
                else color = ChatColor.RED.toString();
            } else if(flag.getValue() instanceof Double) {
                color = ChatColor.GOLD.toString();
            }
            values.add(String.format("§7%s = %2s%3s§7", flag.getIdentifier(), color, flag.toString()));
        }

        String msg = String.format("%s §7Flags for arena §b%2s§7:\n(%3s)", TranslationUtil.getPrefix(), a.getName(), String.join(", ", values));
        sender.sendMessage(msg);
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }
}
