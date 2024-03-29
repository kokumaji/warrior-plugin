package com.dumbdogdiner.warrior.api.arena;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.api.translation.Translator;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ArenaBuilder {

    @Getter
    private static HashMap<World, ArenaBuilderSession> sessions = new HashMap<>();

    private static final Translator TR = WarriorAPI.getService().getTranslator();

    public static void registerSession(World world, @NotNull ArenaBuilderSession session) {
        Player player = session.getSessionUser().getBukkitPlayer();
        if(sessions.containsKey(world)) {
            String msg = TR.translate(Constants.Lang.ARENA_SETUP_IN_PROGRESS);
            player.sendMessage(TranslationUtil.getPrefix() + msg);
            return;
        }

        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 0.5f, 1f);

        sessions.put(world, session);

        session.startSession(session.getSessionUser());
    }

    public static ArenaBuilderSession getSession(WarriorUser user) {
        for(ArenaBuilderSession session : sessions.values()) {
            if(session.getSessionUser().getBukkitPlayer().getUniqueId().equals(user.getBukkitPlayer().getUniqueId())) return session;
        }

        return null;
    }

    public static void setPosition(Location loc, ArenaBuilderSession.PositionType type) {
        if(sessions.get(loc.getWorld()) == null) return;
        sessions.get(loc.getWorld()).setPosition(type);
    }
}
