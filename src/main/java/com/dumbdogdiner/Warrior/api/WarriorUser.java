package com.dumbdogdiner.Warrior.api;

import com.dumbdogdiner.Warrior.api.util.ReflectionUtil;
import com.dumbdogdiner.Warrior.api.util.NMSUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Objects;
import java.util.UUID;

import io.netty.channel.Channel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WarriorUser {

    private static final Class<?> ENTITYPLAYER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("EntityPlayer"));

    private static final Class<?> NETWORKMANAGER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("NetworkManager"));

    @Getter
    private Object craftPlayer;

    @Getter
    private Object networkManager;

    @Getter
    private Object playerConnection;

    @Getter
    private Player bukkitPlayer;

    public WarriorUser(Player player) {
        try {
            this.bukkitPlayer = player;
            this.craftPlayer = player.getClass().getMethod("getHandle").invoke(player);

            Field conField = ENTITYPLAYER_CLASS.getField("playerConnection");
            this.playerConnection = conField.get(this.craftPlayer);

            Field nmField = this.playerConnection.getClass().getField("networkManager");
            this.networkManager = nmField.get(this.playerConnection);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public WarriorUser(UUID uuid) {
        this(Bukkit.getPlayer(uuid));
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

}
