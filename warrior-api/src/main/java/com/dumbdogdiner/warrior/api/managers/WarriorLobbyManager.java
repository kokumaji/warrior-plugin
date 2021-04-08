package com.dumbdogdiner.warrior.api.managers;

import org.bukkit.Location;

/**
 * Manages the location of lobby spawns.
 */
public interface WarriorLobbyManager {
    /**
     * Update the spawning location of lobbies.
     * @param location The location to update it to
     */
    void updateLocation(Location location);
}
