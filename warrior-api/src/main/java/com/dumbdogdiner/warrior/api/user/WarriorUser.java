package com.dumbdogdiner.warrior.api.user;

import com.dumbdogdiner.warrior.api.events.SessionChangeEvent;
import com.dumbdogdiner.warrior.nms.networking.packets.Packet;
import com.dumbdogdiner.warrior.api.sessions.Session;
import com.dumbdogdiner.warrior.api.sound.Note;
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathParticle;
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathSound;
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathSounds;
import com.dumbdogdiner.warrior.api.user.cosmetics.WarriorTitle;
import com.dumbdogdiner.warrior.api.user.settings.GameplaySettings;
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings;
import com.dumbdogdiner.warrior.api.user.settings.VisualSettings;
import io.netty.channel.Channel;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Creates a WarriorUser instance to access
 * plugin specific functionalities.
 */

public interface WarriorUser extends Comparable<WarriorUser> {
	/**
	 * @return The number of kills this player has obtained.
	 */
	int getKills();

	/**
	 * @return The number of times this player has died.
	 */
	int getDeaths();

	/**
	 * @return The amount of coins this user has.
	 */
	int getCoins();

	/**
	 * @return The progress in XP being made towards the next level.
	 */
	int getRelativeXp();

	/**
	 * @return The total amount of XP this user has.
	 */
	int getTotalXp();

	/**
	 * @return The xp level of this user.
	 */
	int getLevel();

	/**
	 * @return The unique ID of this user.
	 */
	UUID getUniqueId();

	/**
	 * @return The timestamp at which this player first joined the server.
	 */
	long getFirstJoin();

	/**
	 * @return The timestamp at which the player last joined the server.
	 */
	long getLastJoin();

	/**
	 * @return The total time the player has been playing in milliseconds(?).
	 * TODO: Verify that this is in millis.
	 */
	long getTotalTime();

	/**
	 * @return The user's current session, if one exists.
	 */
	@Nullable Session getSession();

	/**
	 * @return True if this user is currently spectating.
	 */
	boolean isSpectating();

	/**
	 * @return True if this user is currently using their ability.
	 */
	boolean getAbilityActive();

	/**
	 * Enable or disable the user's current ability.
	 * @param isActive True if the ability is active, false otherwise.
	 */
	void setAbilityActive(boolean isActive);

	/**
	 * @return The user's active title.
	 */
	WarriorTitle getActiveTitle();

	/**
	 * Set the user's active title.
	 * @param title Their new title
	 */
	void setActiveTitle(WarriorTitle title);

	// REFLECTION STUFF - cringe nae nae :3c

	/**
	 * Get the CraftPlayer instance of this player.
	 */
	Object getCraftPlayer();

	/**
	 * Get the NetworkManager instance of this player.
	 */
	Object getNetworkManager();

	/**
	 * Get the current active connection of this player.
	 */
	Object getPlayerConnection();

	/**
	 * @return The {@link Player} instance of this player.
	 */
	Player getBukkitPlayer();

	/**
	 *
	 * START OF DATA STORAGE
	 *
	 * Compact data storage for {@link DeathSounds}.
	 * Each bit represents one death sound and
	 * its unlock state for this player.
	 *
	 * (0 = locked, 1 = unlocked)
	 */
	int getDeathSounds();

	/**
	 * Compact data storage for DeathParticles.
	 * Each bit represents one death particle and
	 * its unlock state for this player.
	 *
	 * (0 = locked, 1 = unlocked)
	 */
	int getDeathParticles();


	/**
	 * Compact data storage for Titles.
	 * Each bit represents one title and
	 * its unlock state for this player.
	 *
	 * (0 = locked, 1 = unlocked)
	 */
	int getTitles();

	/**
	 * Get the general user settings
	 * for this player.
	 */
	GeneralSettings getSettings();

	/**
	 * Get the gameplay specific settings
	 * for this player.
	 */
	GameplaySettings getGameplaySettings();


	/**
	 * Get the visuals specific settings
	 * for this player.
	 */
	VisualSettings getVisualSettings();


	/**
	 * See {@link Player#getEntityId()}.
	 */
	default int getEntityId() {
		return this.getBukkitPlayer().getEntityId();
	}

	/**
	 * See {@link Player#getName()}.
	 */
	default String getName() {
		return this.getBukkitPlayer().getName();
	}

	/**
	 * See {@link Player#sendMessage(String)}.
	 */
	default void sendMessage(String s) {
		this.getBukkitPlayer().sendMessage(s);
	}

	/**
	 * See {@link Player#getWorld()}.
	 * @return the world this user
	 *         is currently in.
	 */
	default World getWorld() {
		return this.getBukkitPlayer().getWorld();
	}

	/**
	 * See {@link Player#getLocation()}.
	 * @return the location of this player
	 */
	default Location getLocation() {
		return this.getBukkitPlayer().getLocation();
	}

	/**
	 * Sends an action bar message using reflection
	 * @param msg the message that should be displayed.
	 */
	void sendActionBar(String msg);

	/**
	 * Send this user a formatted title message.
	 * @param title The main title text
	 * @param subtitle The subtitle text
	 * @param stay The length of time the title should be displayed in seconds
	 */
	void sendTitle(String title, String subtitle, int stay);

	/**
	 * Send this user a formatted title message.
	 * @param title The main title text
	 * @param subtitle The subtitle text
	 * @param fadeIn The length of fade in time in seconds
	 * @param stay The length of time the title should be displayed in seconds
	 * @param fadeOut The length of fade out time in seconds
	 */
	void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut);

	/**
	 * TODO: I don't know what this does.
	 * @param func
	 */
	void spawnEffect(Consumer<WarriorUser> func);

	/**
	 * TODO: I don't know what this does.
	 */
	void debugEffects();

	/**
	 * Execute the given consumer on this user's thread pool.
	 * @param func The consumer to execute
	 */
	void executeAsync(Consumer<WarriorUser> func);

	/**
	 * Reset this user to their default state.
	 */
	void reset();

	/**
	 * Removes a collection of potion effects.
	 * @param potionEffects collection of potion effects.
	 */
	void removeEffects(Collection<PotionEffect> potionEffects);

	/**
	 * Removes a collection of potion effects.
	 * @param potionEffects array of potion effects
	 */
	void removeEffects(PotionEffect... potionEffects);

	/**
	 * Removes all active potion effects from the player.
	 */
	void removeEffects();

	/**
	 * Changes the {@link Session} of a player.
	 * Calls the {@link SessionChangeEvent} every time
	 * this method is used.
	 *
	 * @param session instance of {@link Session}
	 */
	void setSession(Session session);

	/**
	 * Plays a sound to the player at the player's location
	 *
	 * @param sound A {@link Sound} enum
	 * @param volume the volume at which this sound should be played at
	 * @param pitch the pitch at which this sound should be played at
	 */
	void playSound(Sound sound, float volume, float pitch);

	/**
	 * Plays a sound to the player at the player's location
	 *
	 * @param sound A {@link Sound} enum
	 * @param volume the volume at which this sound should be played at
	 * @param note A {@link Note} enum
	 */
	void playSound(Sound sound, float volume, Note note);

	/**
	 * Spawn a particle at the target location.
	 * TODO: I've seen the particle code the old class uses - but why do we need this?
	 * @param particle
	 * @param loc
	 * @param amount
	 */
	void spawnParticle(Particle particle, Location loc, int amount);

	void spawnParticle(Particle particle, Location loc, Vector offset, int amount, double extra);

	void spawnParticle(Particle particle, Location loc, Vector offset, int amount);

	<T> void spawnParticle(Particle particle, Location loc, Vector offset, int amount, T data);

	/**
	 * Set the active death sound for this user.
	 * @param sound The new death sound
	 */
	void setActiveSound(DeathSound sound);

	/**
	 * Set the active particle trail for this user.
	 * @param particle The new particle trail
	 */
	void setActiveParticle(DeathParticle particle);

	/**
	 * Experimental method to unlock a sound for a player.
	 * May be removed/replaced in future releases.
	 *
	 * @param sound The {@link DeathSound} that should be unlocked
	 */
	void unlockSound(DeathSound sound);

	/**
	 * Experimental method to unlock a sound for a player.
	 * May be removed/replaced in future releases.
	 *
	 * @param particle The {@link DeathParticle} that should be unlocked
	 */
	void unlockParticle(DeathParticle particle);

	/**
	 * Experimental method to unlock a sound for a player.
	 * May be removed/replaced in future releases.
	 *
	 * @param title The {@link WarriorTitle} that should be unlocked
	 */
	void unlockTitle(WarriorTitle title);
	/**
	 * Plays the death sound of this player at their location.
	 */
	void playDeathSound();

	/**
	 * Displays the death particles of this player at their location.
	 */
	void showDeathParticles();

	/**
	 * Sends a Packet via reflection. This method is currently designed
	 * to accept raw notchian packets. This may change in the future
	 * with the implementation of an in-house packet layer.
	 *
	 * @param packet the packet.
	 */
	void sendPacket(Packet packet);

	/**
	 * Gets the {@link Channel} instance of the CraftBukkit player.
	 *
	 * @return See {@link Channel}
	 */
	Channel getChannel();

	/**
	 * Returns the players current ping.
	 *
	 * @return {@link Integer} representing the players ping
	 */
	int getPing();

	/**
	 * Sets the spectating state of this player.
	 *
	 * @param spectating whether the player should be in
	 *                   spectator mode
	 */
	void setSpectating(boolean spectating);

	void addExperience(int exp);

	/**
	 * Increments the {@link WarriorUser#getKills()} value by one.
	 */
	void addKill();

	/**
	 * Increments the {@link WarriorUser#getDeaths()} value by one.
	 */
	void addDeath();

	/**
	 * Returns the Kills/Deaths ratio. <b>May return
	 * amount of kills if player doesnt have any deaths.</b>
	 *
	 * @return Kills/Deaths ratio as {@link Double}
	 */
	double getKDR();

	/**
	 * Adds the specified amount of coins to the players balance.
	 *
	 * @param amount {@link Integer}
	 */
	void addCoins(int amount);

	/**
	 * Sets the player balance to the specified amount.
	 *
	 * @param amount {@link Integer}
	 */
	void setCoins(int amount);

	/**
	 * Removes the specified amount of coins to the players balance.
	 *
	 * @param amount {@link Integer}
	 */
	void removeCoins(int amount);

	/**
	 * Compares the Entity ID of this User with the Entity ID
	 * of another user. <b>Currently only used</b> for more
	 * efficient caching of Players. Future releases may
	 * have comparators for all data values.
	 *
	 * @param user Other WarriorUser instance that
	 *             should be compared.
	 *
	 * @return A value greater than 0 if Entity ID of
	 *         this user > than Entity ID of compared
	 *         user, a value less than 0 if Entity ID
	 *         of this user < than Entity ID of compared
	 *         user, or 0 if Entity ID is identical.
	 */
	@Override
	default int compareTo(@NotNull WarriorUser user) {
		return Integer.compare(getEntityId(), user.getEntityId());
	}
}
