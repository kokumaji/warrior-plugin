package com.dumbdogdiner.warrior.commands.arena;

import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.models.metadata.ArenaMetadata;
import com.dumbdogdiner.warrior.api.translation.Symbols;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ArenaRateCommand implements SubCommand {

    @Override
    public String getAlias() {
        return "rate";
    }

    @Override
    public String getSyntax() {
        return "/arena rate <Arena> <Rating>";
    }

    @Override
    public String getPermission() {
        return "warrior.arena.rate";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        /*
        Arena a = ArenaManager.get(args[1]);
        if(a == null) return false;

        ArenaMetadata meta = a.getMetadata();
        if(meta == null) return true;

        String ratingString = args[2];

        if(!NumberUtil.isNumeric(ratingString) || !MathUtil.inRange(Integer.parseInt(ratingString), 0, 5))
            return false;


        meta.addRating(Integer.parseInt(args[2]));

        sender.sendMessage("Average Rating for this Arena: " + a.getMetadata().averageRating());*/

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }

    private String calcRating(int rating) {
        String stars = String.valueOf(Symbols.BLACK_STAR).repeat(rating);
        return stars + String.valueOf(Symbols.WHITE_STAR).repeat(5 - rating);
    }

}
