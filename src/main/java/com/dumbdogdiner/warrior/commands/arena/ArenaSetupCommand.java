package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.arena.ArenaBuilder;
import com.dumbdogdiner.warrior.api.arena.ArenaBuilderSession;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
        ArenaBuilderSession session = ArenaBuilder.getSession(user);

        if(session == null) {
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_NO_SETUP_RUNNING, user);
            user.getBukkitPlayer().sendMessage(TranslationUtil.getPrefix() + msg);
            return true;
        }
        if(args[1].equalsIgnoreCase("cancel")) {
            session.endSession(ArenaBuilderSession.SessionResult.CANCEL);
        } else if(args[1].equalsIgnoreCase("pos1")) {
            ArenaBuilder.setPosition(user.getBukkitPlayer().getLocation(), ArenaBuilderSession.PositionType.LOC1);
        } else if(args[1].equalsIgnoreCase("pos2")) {
            ArenaBuilder.setPosition(user.getBukkitPlayer().getLocation(), ArenaBuilderSession.PositionType.LOC2);
        } else if(args[1].equalsIgnoreCase("spawn")) {
            ArenaBuilder.setPosition(user.getBukkitPlayer().getLocation(), ArenaBuilderSession.PositionType.SPAWN);
        } else if(args[1].equalsIgnoreCase("confirm")) {
            session.endSession(ArenaBuilderSession.SessionResult.CONFIRM);
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
