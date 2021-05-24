package com.dumbdogdiner.warrior.api.user;

import com.dumbdogdiner.warrior.api.WarriorAPI;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface IUserCache<T extends WarriorUser> {

    /**
     * Test whether the player manager contains the target player.
     * @param uuid The UUID of the player
     * @return True if the manager contains the target player.
     */
    boolean contains(UUID uuid);

    /**
     * Add the target player to the player manager.
     * @param userId The UUID of the player
     * @return
     */
    T addUser(@NotNull UUID userId);

    /**
     * Remove the target player from the player manager.
     * @param user The target player
     */
    void remove(@NotNull T user);

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
    T get(UUID userId);

    /**
     * Get a list of players known to this manager.
     * @return A {@link List} of players.
     */
    List<T> getList();

    /**
     * Get a list of players known to this manager, filtered by the given predicate.
     * @param predicate The predicate to filter players by
     * @return A filtered {@link List} of players.
     */
    List<T> getListOf(Predicate<T> predicate);

    Listener getListener();

    void setListener(@NotNull Listener listener);

    default void unregister() {
        if(this.getListener() != null) {
            PlayerJoinEvent.getHandlerList().unregister(WarriorAPI.getService().getInstance());
            PlayerQuitEvent.getHandlerList().unregister(WarriorAPI.getService().getInstance());
        }
    }

    /**
     * Registers Join/Leave listeners to automate adding Players to cache
     */
    default void registerHandlers() {
        if(this.getListener() == null) { // dumbass me did != before...
            this.setListener(new Listener() {

                @EventHandler
                public void onJoin(PlayerJoinEvent e) {
                    var player = e.getPlayer();

                    if(WarriorAPI.getService().isDebugMode()) {
                        String msg = String.format("Adding User %s(%2s) to Cache.", player.getName(), player.getUniqueId());
                        WarriorAPI.getService().getLogger().debug(msg);
                    }

                    if(!contains(player.getUniqueId())) {
                        addUser(player.getUniqueId());
                    } else {
                        String msg = String.format("User %s(%2s) is already cached!", player.getName(), player.getUniqueId());
                        WarriorAPI.getService().getLogger().warn(msg);
                    }
                }

                @EventHandler
                public void onQuit(PlayerQuitEvent e) {
                    var player = e.getPlayer();

                    if(WarriorAPI.getService().isDebugMode()) {
                        String msg = String.format("Removing User %s(%2s) from Cache.", player.getName(), player.getUniqueId());
                        WarriorAPI.getService().getLogger().debug(msg);
                    }

                    if(!contains(player.getUniqueId())) {
                        remove(player.getUniqueId());
                    } else {
                        String msg = String.format("User %s(%2s) is not cached!", player.getName(), player.getUniqueId());
                        WarriorAPI.getService().getLogger().warn(msg);
                    }
                }

            });
        }
    }
}