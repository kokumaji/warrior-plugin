package com.dumbdogdiner.warrior;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.Session;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

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
                    Duration d = Duration.ofMillis((System.currentTimeMillis() - user.getTimeJoined()));
                    return d.toString()
                            .substring(2)
                            .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                            .toLowerCase();
                case "id":
                case "userid":
                case "uuid":
                    return user.getUserId().toString();
                case "ping":
                    return user.getPing() + "ms";
                case "deathsound":
                    return user.getActiveSound().friendlyName();
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
                    if(!(session instanceof ArenaSession)) return "(None)";
                    return ((ArenaSession)session).getArena().getName();
            }
        }

        return identifier;
    }
}
