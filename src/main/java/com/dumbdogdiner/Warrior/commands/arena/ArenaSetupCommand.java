package com.dumbdogdiner.Warrior.commands.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.ArenaBuilder;
import com.dumbdogdiner.Warrior.api.arena.ArenaSession;
import com.dumbdogdiner.Warrior.api.command.SubCommand;

import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaSetupCommand implements SubCommand {

    @Override
    public String getAlias() {
        return "setup";
    }

    @Override
    public String getSyntax() {
        return "/arena setup <pos1|pos2|spawn|confirm|cancel>";
    }

    @Override
    public String getPermission() {
        return "warrior.arena.setup";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        WarriorUser user = new WarriorUser((Player)sender);
        ArenaSession session = ArenaBuilder.getSession(user);

        if(session == null) {
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_NO_SETUP_RUNNING, true);
            user.getBukkitPlayer().sendMessage(msg);
            return true;
        }
        if(args[1].equalsIgnoreCase("cancel")) {
            session.endSession(ArenaSession.SessionResult.CANCEL);
        } else if(args[1].equalsIgnoreCase("pos1")) {
            ArenaBuilder.setPosition(user.getBukkitPlayer().getLocation(), ArenaSession.PositionType.LOC1);
        } else if(args[1].equalsIgnoreCase("pos2")) {
            ArenaBuilder.setPosition(user.getBukkitPlayer().getLocation(), ArenaSession.PositionType.LOC2);
        } else if(args[1].equalsIgnoreCase("spawn")) {
            ArenaBuilder.setPosition(user.getBukkitPlayer().getLocation(), ArenaSession.PositionType.SPAWN);
        } else if(args[1].equalsIgnoreCase("confirm")) {
            session.endSession(ArenaSession.SessionResult.CONFIRM);
        }
        return true;
    }

    public List<String> getArguments(CommandSender sender, String[] args) {
        List<String> strings = new ArrayList<>();

        if(args.length == 2) {
            strings = List.of("cancel", "confirm", "pos1", "pos2", "spawn");
        }

        return strings;
    }
}
