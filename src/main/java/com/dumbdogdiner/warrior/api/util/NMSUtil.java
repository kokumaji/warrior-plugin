package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.nms.PacketListener;
import com.dumbdogdiner.warrior.api.nms.networking.packets.ClientPacket;
import com.dumbdogdiner.warrior.api.nms.networking.packets.ServerPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class NMSUtil {
    public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static final String NMS_PACKAGE = "net.minecraft.server.%s.%2s";
    public static final String CRAFT_PACKAGE = "org.bukkit.craftbukkit.%s.%2s";

    public static final Class<?> NMS_PACKET_CLASS = getNMSClass("Packet");

    public static Class<?> getCraftClass(String classname) {
        if (NMS_VERSION == null)
            return null;
        String classpath = String.format(CRAFT_PACKAGE, NMS_VERSION, classname);
        try {
            return Class.forName(classpath);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static @NotNull Class<?> getNMSClass(String classname) {
        Class<?> clazz = null;
        if (NMS_VERSION == null)
            throw new IllegalStateException("Could not get server version");
        String classpath = String.format(NMS_PACKAGE, NMS_VERSION, classname);
        try {
            clazz = Class.forName(classpath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(clazz);
    }

    public static Object getNMSServer() {
        try {
            Class<?> cs = getCraftClass("CraftServer");
            Object csInstance = cs.cast(Bukkit.getServer());
            return csInstance.getClass().getMethod("getServer").invoke(csInstance);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object toVec3d(@NotNull Vector vector) {
        try {
            Class<?> vec3d = getNMSClass("Vec3D");
            Constructor<?> vec3dConst = vec3d.getDeclaredConstructor(Double.TYPE, Double.TYPE, Double.TYPE);

            return vec3dConst.newInstance(vector.getX(), vector.getY(), vector.getZ());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getWorldServer(World world) {
        try {
            return world.getClass()
                    .getMethod("getHandle")
                    .invoke(world);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void injectPlayer(final PacketListener service, WarriorUser player, Plugin pl) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
                service.onReceive(ctx, new ClientPacket(packet));
                super.channelRead(ctx, packet);
            }

            public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
                ServerPacket serverPacket = new ServerPacket(packet);

                service.onSend(ctx, serverPacket, promise);
                if(!serverPacket.isCancelled()) {
                    super.write(ctx, serverPacket.getHandle(), promise);
                }
            }
        };
        try {
            Channel channel = player.getChannel();
            if (channel.pipeline().get(pl.getName() + "-" + pl.getName()) == null) {
                channel.pipeline().addBefore("packet_handler", pl.getName() + "-" + pl.getName(), channelDuplexHandler);
            } else {
                throw new IllegalStateException("Channel '" + pl.getName() + "-" + player.getName() + "' already exists.");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static @NotNull Object makeChatComponent(String s) {
        Method toMutableComponent = null;
        Class<?> chatSerializer = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
        Object chatComponent = null;
        try {
            toMutableComponent = chatSerializer.getMethod("a", String.class);
            chatComponent = toMutableComponent.invoke(chatSerializer, "{\"text\": \"" + s + "\"}");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if(chatComponent == null)
            throw new IllegalStateException("Could not create IChatMutableComponent object.");

        return chatComponent;
    }

    public static void removeInjection(WarriorUser player, Plugin pl) {
        Channel ch = player.getChannel();
        if (ch.pipeline().get(pl.getName() + "-" + pl.getName()) != null)
            ch.pipeline().remove(player.getName());
    }
}
