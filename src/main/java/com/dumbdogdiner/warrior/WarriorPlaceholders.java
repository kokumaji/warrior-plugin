package com.dumbdogdiner.warrior;

import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.translation.TimeUtil;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.Session;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarriorPlaceholders extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "warrior";
    }

    @Override
    public @NotNull String getAuthor() {
        return Warrior.getInstance().getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return Warrior.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        String[] args = identifier.split("_");
        if(args.length < 1) return identifier;
        if(args[0].equalsIgnoreCase("player")) {
            WarriorUser user = PlayerManager.get(p.getUniqueId());
            if(user == null) return identifier;
            switch(args[1]) {
                case "name":
                    return user.getName();
                case "timeplayed":
                    long time = user.getTotalTime() + (System.currentTimeMillis() - user.getLastJoin());
                    return TimeUtil.formatDuration(time);
                case "firstjoin":
                    return TimeUtil.formatDate(user.getSettings().getLanguage(), user.getFirstJoin());
                case "id":
                case "userid":
                case "uuid":
                    return user.getUserId().toString();
                case "ping":
                    return user.getPing() + "ms";
                case "title":
                    return user.getSettings().getTitle().getTitle();
                case "deathsound":
                    return user.getGameplaySettings().getActiveSound().friendlyName();
                case "kills":
                    return Integer.toString(user.getKills());
                case "deaths":
                    return Integer.toString(user.getDeaths());
                case "kdr":
                    return Double.toString(user.getKDR());
                case "coins":
                    return Integer.toString(user.getCoins());
                case "arena":
                    Session session = user.getSession();
                    if(!(session instanceof ArenaSession)) return "(none)";
                    return ((ArenaSession)session).getArena().getName();
            }
        } else if(args[0].equalsIgnoreCase("arena")) {
            Arena arena = null;
            String argument = "";

            WarriorUser user = PlayerManager.get(p.getUniqueId());
            if(user == null) return identifier;

            // if no argument provided, get the arena at the players location
            if(args.length == 2) {
                for(Arena a : ArenaManager.getArenas()) {
                    if(a.getBounds().contains(user.getLocation())) {
                        arena = a;
                        break;
                    }
                }

                argument = args[1];
            } else if(args.length == 3) {
                if(ArenaManager.get(args[1]) == null) return identifier;
                arena = ArenaManager.get(args[1]);

                argument = args[2];
            }

            if(arena == null) return identifier;

            switch (argument) {
                case "name":
                    return arena.getName();
                case "id":
                    return String.valueOf(arena.getId());
                case "flags":
                    return arena.getFlags().toString();
                case "rating":
                    return String.valueOf(arena.getMetadata().averageRating());
                case "creator":
                    return arena.getMetadata().getCreator();
                case "timestamp":
                    return TimeUtil.formatDate(user.getSettings().getLanguage(), arena.getMetadata().getCreatedAt());
                case "description":
                    return String.join("\n", arena.getMetadata().getDesc());
            }
        }

        return identifier;
    }
}
