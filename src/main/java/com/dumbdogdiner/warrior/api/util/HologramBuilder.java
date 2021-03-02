package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import com.dumbdogdiner.stickyapi.bukkit.nms.BukkitHandler;
import lombok.SneakyThrows;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class HologramBuilder {

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

    public HologramBuilder sendTo(WarriorUser... users) {
        for(WarriorUser user : users) {
            try {
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
