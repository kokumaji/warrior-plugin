package com.dumbdogdiner.warrior.api.managers;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface PlayerManager {

    /**
     * Test whether the player manager contains the target player.
     * @param uuid The UUID of the player
     * @return True if the manager contains the target player.
     */
    boolean contains(UUID uuid);

    /**
     * Add the target player to the player manager.
     * @param userId The UUID of the player
     */
    void addUser(@NotNull UUID userId);

    /**
     * Remove the target player from the player manager.
     * @param user The target player
     */
    void remove(@NotNull WarriorUser user);

    /**
     * Remove the target player from the player manager.
     * @param userId The UUID of the target player
     */
    void remove(@NotNull UUID userId);

    /**
     * Get the player with the target UUID.
     * @param userId The UUID of the player
     * @return A {@link WarriorUser} for the target player, if one exists.
     */
    WarriorUser get(UUID userId);

    /**
     * Get a list of players known to this manager.
     * @return A {@link List} of players.
     */
    List<WarriorUser> getList();

    /**
     * Get a list of players known to this manager, filtered by the given predicate.
     * @param predicate The predicate to filter players by
     * @return A filtered {@link List} of players.
     */
    List<WarriorUser> getListOf(Predicate<WarriorUser> predicate);
}
