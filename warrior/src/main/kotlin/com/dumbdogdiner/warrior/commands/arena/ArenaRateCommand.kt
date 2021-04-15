package com.dumbdogdiner.warrior.commands.arena

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.translation.Symbols
import org.bukkit.command.CommandSender

class ArenaRateCommand : SubCommand {
    override fun getAlias(): String {
        return "rate"
    }

    override fun getSyntax(): String {
        return "/arena rate <Arena> <Rating>"
    }

    override fun getPermission(): String {
        return "warrior.arena.rate"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        /* TODO: Improve Logic
        Arena a = ArenaManager.get(args[1]);
        if(a == null) return false;

        ArenaMetadata meta = a.getMetadata();
        if(meta == null) return true;

        String ratingString = args[2];

        if(!NumberUtil.isNumeric(ratingString) || !MathUtil.inRange(Integer.parseInt(ratingString), 0, 5))
            return false;


        meta.addRating(Integer.parseInt(args[2]));

        sender.sendMessage("Average Rating for this Arena: " + a.getMetadata().averageRating());*/
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return listOf()
    }

    private fun calcRating(rating: Int): String {
        val stars = Symbols.BLACK_STAR.toString().repeat(rating)
        return stars + Symbols.WHITE_STAR.toString().repeat(5 - rating)
    }
}