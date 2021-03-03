package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.warrior.api.WarriorUser;

import io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

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

    public static Class<?> getNMSClass(String classname) {
        if (NMS_VERSION == null)
            return null;
        String classpath = String.format(NMS_PACKAGE, NMS_VERSION, classname);
        try {
            return Class.forName(classpath);
        } catch (ClassNotFoundException e) {
            return null;
        }
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

    public static void injectPlayer(final InjectionService service, WarriorUser player, Plugin pl) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
                service.run(ctx, new Packet(packet));
                super.channelRead(ctx, packet);
            }

            public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
                service.run(ctx, new Packet(packet));
                super.write(ctx, packet, promise);
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

    public interface InjectionService {
        void run(ChannelHandlerContext ctx, Packet packet);
    }

    public static void removeInjection(WarriorUser player, Plugin pl) {
        Channel ch = player.getChannel();
        if (ch.pipeline().get(pl.getName() + "-" + pl.getName()) != null)
            ch.pipeline().remove(player.getName());
    }
}
