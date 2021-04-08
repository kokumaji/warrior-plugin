package com.dumbdogdiner.warrior.api.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.Session;
import com.dumbdogdiner.warrior.api.sessions.SessionType;
import com.dumbdogdiner.warrior.api.translation.Placeholders;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.util.JSONUtil;
import com.dumbdogdiner.warrior.utils.DefaultMessages;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ArenaManager {
    /**
     * Register the target arena with the arena manager.
     * @param arena
     */
    void registerArena(Arena arena);

    List<Arena> getArenas();

    /**
     * Get the arena with the target name.
     * @param arenaName The name of the arena
     * @return An {@link Arena}, if one with the target name exists.
     */
    Arena get(String arenaName);

    /**
     * Load all saved arenas.
     */
    void loadArenas();

    /**
     * Get a list of players in the target arena.
     * @param arena The target arena
     * @return A {@link List} containing every player in the target arena.
     */
    List<WarriorUser> getPlayers(Arena arena);

    /**
     * Test if the target player currently playing.
     * @param player The target player
     * @return True if the player is in a game.
     */
    Boolean isPlaying(Player player);

    /**
     * Return the session for the target player.
     * @param player The target player
     * @return An {@link ArenaSession}, if one exists.
     */
    ArenaSession getSession(Player player);

    /**
     * Remove the target arena from the manager.
     * @param arena The target arena.
     */
    void remove(Arena arena);
}
