package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.arena.ArenaBuilder;
import com.dumbdogdiner.Warrior.api.arena.ArenaSession;
import com.dumbdogdiner.Warrior.api.command.ExitStatus;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        Player player = (Player) sender;
        String name = args[1];
        ArenaSession session = new ArenaSession(new WarriorUser(player), name);

        Arena arena = ArenaManager.get(args[1]);

        if(arena != null) {
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_ALREADY_EXISTS, new HashMap<String, String>() {
                {
                    put("ARENA", arena.getName());
                }
            });
            player.sendMessage(TranslationUtil.getPrefix() + msg);
            return true;
        }

        //start session
        ArenaBuilder.registerSession(player.getWorld(), session);
        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
