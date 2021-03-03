package com.dumbdogdiner.warrior.api;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.events.SessionChangeEvent;
import com.dumbdogdiner.warrior.api.kit.effects.DeathSound;
import com.dumbdogdiner.warrior.api.kit.effects.DeathSounds;
import com.dumbdogdiner.warrior.api.sessions.Session;
import com.dumbdogdiner.warrior.api.util.NMSUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import com.dumbdogdiner.stickyapi.bukkit.nms.BukkitHandler;
import com.dumbdogdiner.warrior.api.sound.Note;
import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * Creates a WarriorUser instance to access
 * plugin specific functionalities.
 */

public class WarriorUser {

    /**
     * used for reflection
     */
    private static final Class<?> ENTITYPLAYER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("EntityPlayer"));

    /**
     * used for reflection
     */
    private static final Class<?> NETWORKMANAGER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("NetworkManager"));

    /**
     *  GETTERS & SETTERS FOR USER DATA
     */

    @Getter
    private int kills;

    @Getter
    private int deaths;

    @Getter
    private int coins;

    @Getter
    private UUID userId;

    @Getter
    private final long timeJoined;

    @Getter
    private Session session;

    @Getter
    private boolean spectating;

    @Getter @Setter
    private boolean abilityActive;

    @Getter @Setter
    private DeathSound activeSound = DeathSound.WATERSPLASH;

    @Getter
    private final Particle activeParticle = Particle.HEART;

    /**
     *  REFLECTION SPECIFIC THINGS
     */

    @Getter
    private Object craftPlayer;

    @Getter
    private Object networkManager;

    @Getter
    private Object playerConnection;

    @Getter
    private Player bukkitPlayer;


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
    @Getter
    private int deathSounds;

    /**
     * Compact data storage for DeathParticles.
     * Each bit represents one death particle and
     * its unlock state for this player.
     *
     * (0 = locked, 1 = unlocked)
     */
    @Getter
    private int deathParticles;

    /**
     * Creates a WarriorUser instance to access
     * plugin specific functionalities.
     *
     * @param player A {@link Player} instance
     */
    public WarriorUser(@NotNull Player player) {
        Preconditions.checkNotNull(player, "Player cannot be null!");

        // Resetting our player
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        player.setFireTicks(0);
        player.setLevel(0);

        if(player.getActivePotionEffects().size() > 0)
            removeEffects();

        // if our player doesn't have bypass perms, apply adventure mode
        // to possibly remove spectator mode side-effects.
        if(!player.hasPermission("warrior.lobby.bypass")) {
            player.setFlying(false);
            player.setGameMode(GameMode.ADVENTURE);
        }

        this.timeJoined = System.currentTimeMillis();

        try {
            // Let's create a few nms related objects!
            this.bukkitPlayer = player;
            this.userId = bukkitPlayer.getUniqueId();
            this.craftPlayer = player.getClass().getMethod("getHandle").invoke(player);

            Field conField = ENTITYPLAYER_CLASS.getField("playerConnection");
            this.playerConnection = conField.get(this.craftPlayer);

            Field nmField = this.playerConnection.getClass().getField("networkManager");
            this.networkManager = nmField.get(this.playerConnection);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            String msg = String.format("Oh no! Something went wrong while trying to create WarriorUser instance! %s", e.getMessage());
            Warrior.getPluginLogger().error(msg);

            if(Warrior.getInstance().getConfig().getBoolean("general-settings.debug-mode"))
                e.printStackTrace();
        }
    }

    /**
     * Creates a WarriorUser instance to access
     * plugin specific functionalities.
     *
     * @param uuid UUID of a valid {@link Player} instance
     */
    public WarriorUser(UUID uuid) {
        this(Objects.requireNonNull(Bukkit.getPlayer(uuid)));
    }

    /**
     * See {@link Player#getEntityId()}.
     */
    public int getEntityId() {
        return this.bukkitPlayer.getEntityId();
    }

    /**
     * See {@link Player#getName()}.
     */
    public String getName() {
        return this.bukkitPlayer.getName();
    }

    /**
     * See {@link Player#sendMessage(String)}.
     */
    public void sendMessage(String s) {
        bukkitPlayer.sendMessage(s);
    }

    /**
     * Sends an action bar message using reflection
     * @param msg the message that should be displayed.
     */
    public void sendActionBar(String msg) {
        try {
            Object icbc = BukkitHandler.getNMSClass("IChatBaseComponent")
                    .getDeclaredClasses()[0]
                    .getMethod("a", String.class)
                    .invoke(BukkitHandler.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0], "{\"text\": \"" + msg + "\"}");

            Object messageType = BukkitHandler.getNMSClass("ChatMessageType").getMethod("valueOf", String.class)
                    .invoke(null, "GAME_INFO");

            Object packetChat = BukkitHandler.getNMSClass("PacketPlayOutChat")
                    .getConstructor(BukkitHandler.getNMSClass("IChatBaseComponent"), messageType.getClass(), UUID.class)
                    .newInstance(icbc, messageType, UUID.randomUUID());

            sendPacket(packetChat);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes a collection of potion effects.
     * @param potionEffects collection of potion effects.
     */
    public void removeEffects(Collection<PotionEffect> potionEffects) {
        for(PotionEffect effect : potionEffects) {
            if(bukkitPlayer.hasPotionEffect(effect.getType()))
                bukkitPlayer.removePotionEffect(effect.getType());
        }
    }

    /**
     * Removes a collection of potion effects.
     * @param potionEffects array of potion effects
     */
    public void removeEffects(PotionEffect... potionEffects) {
        removeEffects(Arrays.asList(potionEffects));
    }

    /**
     * Removes all active potion effects from the player.
     */
    public void removeEffects() {
        removeEffects(bukkitPlayer.getActivePotionEffects());
    }

    /**
     * Changes the {@link Session} of a player.
     * Calls the {@link SessionChangeEvent} every time
     * this method is used.
     *
     * @param session instance of {@link Session}
     */
    public void setSession(Session session) {
        SessionChangeEvent e = new SessionChangeEvent(this.session, session, bukkitPlayer);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(e);
            }
        }.runTask(Warrior.getInstance());
        this.session = session;
    }

    /**
     * Plays a sound to the player at the player's location
     *
     * @param sound A {@link Sound} enum
     * @param volume the volume at which this sound should be played at
     * @param pitch the pitch at which this sound should be played at
     */
    public void playSound(Sound sound, float volume, float pitch) {
        bukkitPlayer.playSound(bukkitPlayer.getLocation(), sound, volume, pitch);
    }

    /**
     * Plays a sound to the player at the player's location
     *
     * @param sound A {@link Sound} enum
     * @param volume the volume at which this sound should be played at
     * @param note A {@link Note} enum
     */
    public void playSound(Sound sound, float volume, Note note) {
        bukkitPlayer.playSound(bukkitPlayer.getLocation(), sound, volume, (float) note.getPitch());
    }

    /**
     * Experimental method to unlock a sound for a player.
     * May be removed/replaced in future releases.
     *
     * @param sound The {@link DeathSound} that should be unlocked
     */
    public void unlockSound(DeathSound sound) {
        this.deathSounds = deathSounds | sound.getUnlockValue();
        playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.2f, 1f);
        bukkitPlayer.sendTitle("§3§lNew Unlock!", "§7You've unlocked DeathSound §f" + sound.friendlyName(), 10, 80, 10);
        bukkitPlayer.spawnParticle(Particle.HEART, bukkitPlayer.getLocation(), 30, 2, 2, 2);
    }

    /**
     * Plays the death sound of this player at their location.
     */
    public void playDeathSound() {
        Location loc = bukkitPlayer.getLocation();
        loc.getWorld().playSound(loc, getActiveSound().getSound(), 1f, 1f);
    }

    /**
     * Displays the death particles of this player at their location.
     */
    public void showDeathParticles() {
        Location loc = bukkitPlayer.getLocation();
        loc.getWorld().spawnParticle(getActiveParticle(), loc, 15, 0.35, 0.35, 0.35);
    }

    /**
     * Sends a Packet via reflection. This method is currently designed
     * to accept raw notchian packets. This may change in the future
     * with the implementation of an in-house packet layer.
     *
     * @param packet the packet.
     */
    public void sendPacket(Object packet) {
        try {
            Class<?> nmsPacket = NMSUtil.NMS_PACKET_CLASS;
            //if(!ReflectionUtil.isSuperclassRecursive(packet.getClass(), nmsPacket)) return;

            Object conn = getPlayerConnection();
            Method sendPacket = conn.getClass().getMethod("sendPacket", nmsPacket);
            sendPacket.invoke(conn, packet);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the {@link Channel} instance of the CraftBukkit player.
     *
     * @return See {@link Channel}
     */
    public Channel getChannel() {
        Channel ch = null;
        try {
            Field channel = NETWORKMANAGER_CLASS.getField("channel");
            ch = (Channel)channel.get(this.networkManager);
        } catch (IllegalAccessException|NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ch;
    }

    /**
     * Returns the players current ping.
     *
     * @return {@link Integer} representing the players ping
     */
    public int getPing() {
        try {
            Field pingField = ENTITYPLAYER_CLASS.getField("ping");
            return pingField.getInt(this.craftPlayer);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Sets the spectating state of this player.
     *
     * @param spectating whether the player should be in
     *                   spectator mode
     */
    public void setSpectating(boolean spectating) {
        this.spectating = spectating;
        new BukkitRunnable() {

            @Override
            public void run() {
                if(spectating) {
                    bukkitPlayer.setGameMode(GameMode.ADVENTURE);
                    Warrior.getSpecTeam().addEntry(bukkitPlayer.getName());
                    bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        if(Warrior.getSpecTeam().hasEntry(p.getName())) continue;
                        p.hidePlayer(Warrior.getInstance(), bukkitPlayer);
                    }

                    for(String s : Warrior.getSpecTeam().getEntries()) {
                        Player p = Bukkit.getPlayer(s);
                        if(p == null) return;
                        bukkitPlayer.showPlayer(Warrior.getInstance(), p);
                    }
                } else {
                    Warrior.getSpecTeam().removeEntry(bukkitPlayer.getName());
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.showPlayer(Warrior.getInstance(), bukkitPlayer);
                    }

                    for(String s : Warrior.getSpecTeam().getEntries()) {
                        Player p = Bukkit.getPlayer(s);
                        if(p == null) return;
                        bukkitPlayer.hidePlayer(Warrior.getInstance(), p);
                    }

                    for(PotionEffect effect : bukkitPlayer.getActivePotionEffects())
                        bukkitPlayer.removePotionEffect(effect.getType());
                }

                bukkitPlayer.setAllowFlight(spectating);
                bukkitPlayer.setFlying(spectating);

            }
        }.runTask(Warrior.getInstance());

    }

    /**
     * Increments the {@link WarriorUser#getKills()} value by one.
     */
    public void addKill() {
        kills++;
    }

    /**
     * Increments the {@link WarriorUser#getDeaths()} value by one.
     */
    public void addDeath() {
        deaths++;
    }

    /**
     * Returns the Kills/Deaths ratio. <b>May return
     * amount of kills if player doesnt have any deaths.</b>
     *
     * @return Kills/Deaths ratio as {@link Double}
     */
    public double getKDR() {
        return deaths < 1 ? kills : (double) kills / (double) deaths;
    }

    /**
     * Adds the specified amount of coins to the players balance.
     *
     * @param amount {@link Integer}
     */
    public void addCoins(int amount) {
        Preconditions.checkState(amount > 0, "Cannot add a negative amount of coins!");
        this.coins = coins + amount;
    }

    /**
     * Sets the player balance to the specified amount.
     *
     * @param amount {@link Integer}
     */
    public void setCoins(int amount) {
        Preconditions.checkState(amount > -1, "Cannot set coins to a negative value!");
        this.coins = amount;
    }

    /**
     * Removes the specified amount of coins to the players balance.
     *
     * @param amount {@link Integer}
     */
    public void removeCoins(int amount) {
        Preconditions.checkState(amount > 0, "Cannot remove a negative amount of coins!");
        this.coins = Math.max(0, coins - amount);
    }
}
