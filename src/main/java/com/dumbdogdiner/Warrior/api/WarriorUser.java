package com.dumbdogdiner.Warrior.api;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.events.SessionChangeEvent;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.sesssions.GameState;
import com.dumbdogdiner.Warrior.api.sesssions.Session;
import com.dumbdogdiner.Warrior.api.util.ReflectionUtil;
import com.dumbdogdiner.Warrior.api.util.NMSUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Objects;
import java.util.UUID;

import com.dumbdogdiner.Warrior.listeners.GameStateListener;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class WarriorUser {

    private static final Class<?> ENTITYPLAYER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("EntityPlayer"));

    private static final Class<?> NETWORKMANAGER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("NetworkManager"));

    @Getter
    private int kills;
    @Getter
    private int deaths;

    @Getter
    private UUID userId;

    @Getter
    private Object craftPlayer;

    @Getter
    private Object networkManager;

    @Getter
    private Object playerConnection;

    @Getter
    private Player bukkitPlayer;

    @Getter
    private Session session;

    @Getter
    private boolean spectating;

    public WarriorUser(Player player) {

        try {
            this.bukkitPlayer = player;
            this.userId = bukkitPlayer.getUniqueId();
            this.craftPlayer = player.getClass().getMethod("getHandle").invoke(player);

            Field conField = ENTITYPLAYER_CLASS.getField("playerConnection");
            this.playerConnection = conField.get(this.craftPlayer);

            Field nmField = this.playerConnection.getClass().getField("networkManager");
            this.networkManager = nmField.get(this.playerConnection);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

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

    public WarriorUser(UUID uuid) {
        this(Objects.requireNonNull(Bukkit.getPlayer(uuid)));
    }

    public String getName() {
        return this.bukkitPlayer.getName();
    }

    public void sendPacket(Object packet) {
        try {
            Class<?> nmsPacket = NMSUtil.NMS_PACKET_CLASS;
            if(!ReflectionUtil.isSuperclassRecursive(packet.getClass(), nmsPacket)) return;

            Object conn = getPlayerConnection();
            Method sendPacket = conn.getClass().getMethod("sendPacket", nmsPacket);
            sendPacket.invoke(conn, packet);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

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

    public int getPing() {
        try {
            Field pingField = ENTITYPLAYER_CLASS.getField("ping");
            return pingField.getInt(this.craftPlayer);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getEntityId() {
        return this.bukkitPlayer.getEntityId();
    }

    public void sendMessage(String s) {
        bukkitPlayer.sendMessage(s);
    }

    public void playSound(Sound sound, float volume, float pitch) {
        bukkitPlayer.playSound(bukkitPlayer.getLocation(), sound, volume, pitch);
    }

    public void setSpectating(boolean b) {
        this.spectating = b;
        new BukkitRunnable() {

            @Override
            public void run() {
                if(b) {
                    bukkitPlayer.setGameMode(GameMode.ADVENTURE);
                    Warrior.getSpecTeam().addEntry(bukkitPlayer.getName());
                    bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        if(Warrior.getSpecTeam().hasEntry(p.getName())) continue;
                        p.hidePlayer(Warrior.getInstance(), bukkitPlayer);
                    }

                    for(String s : Warrior.getSpecTeam().getEntries()) {
                        Player p = Bukkit.getPlayer(s);
                        bukkitPlayer.showPlayer(Warrior.getInstance(), p);
                    }
                } else {
                    Warrior.getSpecTeam().removeEntry(bukkitPlayer.getName());
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.showPlayer(Warrior.getInstance(), bukkitPlayer);
                    }

                    for(String s : Warrior.getSpecTeam().getEntries()) {
                        Player p = Bukkit.getPlayer(s);
                        bukkitPlayer.hidePlayer(Warrior.getInstance(), p);
                    }

                    for(PotionEffect effect : bukkitPlayer.getActivePotionEffects())
                        bukkitPlayer.removePotionEffect(effect.getType());
                }

                bukkitPlayer.setAllowFlight(b);
                bukkitPlayer.setFlying(b);

            }
        }.runTask(Warrior.getInstance());

    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public double getKDR() {
        return 0.0;
    }
}
