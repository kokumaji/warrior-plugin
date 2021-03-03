package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import com.dumbdogdiner.stickyapi.bukkit.nms.BukkitHandler;
import lombok.SneakyThrows;

import net.minecraft.server.v1_16_R3.Vec3D;
import org.bukkit.Location;

import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class HologramBuilder {

    private Object nmsItem;

    private String[] text = {
        "Sample Text",
        "Sample Text"
    };

    private double HOLOGRAM_OFFSET = 0.25D;

    private Location location;
    private int deleteAfter = 20;

    public HologramBuilder(Location loc) {
        this.location = loc;
    }

    public HologramBuilder setText(String... lines) {
        this.text = Arrays.stream(lines)
                    .map(TranslationUtil::translateColor)
                    .toArray(String[]::new);
        return this;
    }

    public HologramBuilder removeAfter(int seconds) {
        this.deleteAfter = seconds * 20; // -> 20 ticks = 1 second

        return this;
    }

    public HologramBuilder overrideOffset(double offset) {
        this.HOLOGRAM_OFFSET = offset;

        return this;
    }

    public HologramBuilder withItem(Object item) {
        this.nmsItem = item;

        return this;
    }

    public HologramBuilder sendTo(WarriorUser... users) {
        for(WarriorUser user : users) {
            try {

                if(nmsItem != null) {
                    Object nmsWorld = location.getWorld().getClass()
                            .getMethod("getHandle")
                            .invoke(location.getWorld());

                    Object entityItem = BukkitHandler.getNMSClass("EntityItem")
                            .getDeclaredConstructor(BukkitHandler.getNMSClass("World"), Double.TYPE, Double.TYPE, Double.TYPE, BukkitHandler.getNMSClass("ItemStack"))
                            .newInstance(nmsWorld, location.getX(), location.getY() + 1.5, location.getZ(), nmsItem);

                    entityItem.getClass().getMethod("setNoGravity", Boolean.TYPE).invoke(entityItem, true);

                    Object velocityPacket = BukkitHandler.getNMSClass("PacketPlayOutEntityVelocity")
                            .getDeclaredConstructor(Integer.TYPE, BukkitHandler.getNMSClass("Vec3D"))
                            .newInstance((int)entityItem.getClass().getMethod("getId")
                            .invoke(entityItem), BukkitHandler.getNMSClass("Vec3D").getDeclaredConstructor(Double.TYPE, Double.TYPE, Double.TYPE)
                            .newInstance(0, 0, 0));

                    Class<?> entity = BukkitHandler.getNMSClass("Entity");
                    Object entitySpawnPacket = BukkitHandler.getNMSClass("PacketPlayOutSpawnEntity")
                            .getDeclaredConstructor(entity).newInstance(entityItem);

                    Object metadataPacket = BukkitHandler.getNMSClass("PacketPlayOutEntityMetadata")
                            .getDeclaredConstructor(Integer.TYPE, BukkitHandler.getNMSClass("DataWatcher"), Boolean.TYPE)
                            .newInstance((int)entityItem.getClass().getMethod("getId").invoke(entityItem),
                                    entityItem.getClass().getMethod("getDataWatcher")
                                    .invoke(entityItem), false);

                    user.sendPacket(entitySpawnPacket);
                    user.sendPacket(velocityPacket);
                    user.sendPacket(metadataPacket);

                    new BukkitRunnable() {

                        @SneakyThrows
                        @Override
                        public void run() {
                            Object entityDestroyPacket = BukkitHandler.getNMSClass("PacketPlayOutEntityDestroy")
                                    .getDeclaredConstructor(int[].class)
                                    .newInstance(new int[]{(int)entityItem.getClass().getMethod("getId").invoke(entityItem)});

                            user.sendPacket(entityDestroyPacket);
                        }
                    }.runTaskLater(Warrior.getInstance(), deleteAfter);

                }

                for(String line : text) {

                    Object nmsWorld = location.getWorld().getClass()
                            .getMethod("getHandle")
                            .invoke(location.getWorld());

                    Object entityArmorStand = BukkitHandler.getNMSClass("EntityArmorStand")
                            .getDeclaredConstructor(BukkitHandler.getNMSClass("World"), Double.TYPE, Double.TYPE, Double.TYPE)
                            .newInstance(nmsWorld, location.getX(), location.getY(), location.getZ());
                    location.subtract(0, HOLOGRAM_OFFSET, 0);

                    Class<?> asClass = entityArmorStand.getClass();

                    Object chatComponentText = BukkitHandler.getNMSClass("ChatComponentText")
                            .getDeclaredConstructor(String.class)
                            .newInstance(line);

                    asClass.getMethod("setCustomName", BukkitHandler.getNMSClass("IChatBaseComponent")).invoke(entityArmorStand, chatComponentText);
                    asClass.getMethod("setCustomNameVisible", Boolean.TYPE).invoke(entityArmorStand, true);
                    asClass.getMethod("setInvisible", Boolean.TYPE).invoke(entityArmorStand, true);
                    asClass.getMethod("setSmall", Boolean.TYPE).invoke(entityArmorStand, true);
                    asClass.getMethod("setBasePlate", Boolean.TYPE).invoke(entityArmorStand, false);
                    asClass.getMethod("setNoGravity", Boolean.TYPE).invoke(entityArmorStand, true);

                    Class<?> entity = BukkitHandler.getNMSClass("Entity");
                    Object entitySpawnPacket = BukkitHandler.getNMSClass("PacketPlayOutSpawnEntity").getDeclaredConstructor(entity).newInstance(entityArmorStand);

                    Object metadataPacket = BukkitHandler.getNMSClass("PacketPlayOutEntityMetadata")
                            .getDeclaredConstructor(Integer.TYPE, BukkitHandler.getNMSClass("DataWatcher"), Boolean.TYPE)
                            .newInstance((int)asClass.getMethod("getId").invoke(entityArmorStand), asClass.getMethod("getDataWatcher").invoke(entityArmorStand), false);

                    user.sendPacket(entitySpawnPacket);
                    user.sendPacket(metadataPacket);

                    new BukkitRunnable() {

                        @SneakyThrows
                        @Override
                        public void run() {
                            Object entityDestroyPacket = BukkitHandler.getNMSClass("PacketPlayOutEntityDestroy")
                                    .getDeclaredConstructor(int[].class)
                                    .newInstance(new int[]{(int)asClass.getMethod("getId").invoke(entityArmorStand)});

                            user.sendPacket(entityDestroyPacket);
                        }
                    }.runTaskLater(Warrior.getInstance(), deleteAfter);
                }

            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

}
