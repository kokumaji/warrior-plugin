package com.dumbdogdiner.warrior.api.builders;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.nms.PacketType;
import com.dumbdogdiner.warrior.api.nms.entity.NMSEntity;
import com.dumbdogdiner.warrior.api.nms.entity.NMSEntityType;
import com.dumbdogdiner.warrior.api.nms.networking.packets.Packet;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
                Object nmsWorld = NMSUtil.getWorldServer(location.getWorld());
                if(nmsItem != null) {
                    Object entityItem = NMSUtil.getNMSClass("EntityItem")
                            .getDeclaredConstructor(NMSUtil.getNMSClass("World"), Double.TYPE, Double.TYPE, Double.TYPE, NMSUtil.getNMSClass("ItemStack"))
                            .newInstance(nmsWorld, location.getX(), location.getY() + 1.5, location.getZ(), nmsItem);

                    entityItem.getClass().getMethod("setNoGravity", Boolean.TYPE).invoke(entityItem, true);

                    Object velocityPacket = NMSUtil.getNMSClass("PacketPlayOutEntityVelocity")
                            .getDeclaredConstructor(Integer.TYPE, NMSUtil.getNMSClass("Vec3D"))
                            .newInstance((int)entityItem.getClass().getMethod("getId")
                            .invoke(entityItem), NMSUtil.toVec3d(new Vector(0, 0, 0)));

                    Class<?> entity = NMSUtil.getNMSClass("Entity");
                    Object entitySpawnPacket = NMSUtil.getNMSClass("PacketPlayOutSpawnEntity")
                            .getDeclaredConstructor(entity).newInstance(entityItem);

                    Object metadataPacket = NMSUtil.getNMSClass("PacketPlayOutEntityMetadata")
                            .getDeclaredConstructor(Integer.TYPE, NMSUtil.getNMSClass("DataWatcher"), Boolean.TYPE)
                            .newInstance((int)entityItem.getClass().getMethod("getId").invoke(entityItem),
                                    entityItem.getClass().getMethod("getDataWatcher")
                                    .invoke(entityItem), false);

                    user.sendPacket(entitySpawnPacket);
                    user.sendPacket(velocityPacket);
                    user.sendPacket(metadataPacket);


                    if(deleteAfter > 0) {
                        new BukkitRunnable() {

                            @SneakyThrows
                            @Override
                            public void run() {
                                Object entityDestroyPacket = NMSUtil.getNMSClass("PacketPlayOutEntityDestroy")
                                        .getDeclaredConstructor(int[].class)
                                        .newInstance(new int[]{(int)entityItem.getClass().getMethod("getId").invoke(entityItem)});

                                user.sendPacket(entityDestroyPacket);
                            }
                        }.runTaskLater(Warrior.getInstance(), deleteAfter);
                    }
                }

                for(String line : text) {

                    NMSEntity armorStand = new NMSEntity(location, NMSUtil.getNMSClass("EntityArmorStand"), NMSEntityType.ARMOR_STAND);
                    armorStand.setCustomName(line);
                    armorStand.setCustomNameVisible(true);
                    armorStand.setInvisible(true);
                    armorStand.setNoGravity(true);

                    location.subtract(0, HOLOGRAM_OFFSET, 0);

                    Class<?> asClass = armorStand.getEntity().getClass();
                    asClass.getMethod("setSmall", Boolean.TYPE).invoke(armorStand.getEntity(), true);
                    asClass.getMethod("setBasePlate", Boolean.TYPE).invoke(armorStand.getEntity(), false);

                    Object entitySpawnPacket = NMSUtil.getNMSClass("PacketPlayOutSpawnEntity").getDeclaredConstructor(NMSEntity.ENTITY_CLASS).newInstance(armorStand.getEntity());

                    Object metadataPacket = NMSUtil.getNMSClass("PacketPlayOutEntityMetadata")
                            .getDeclaredConstructor(Integer.TYPE, NMSUtil.getNMSClass("DataWatcher"), Boolean.TYPE)
                            .newInstance(armorStand.getId(), armorStand.getDataWatcher(), false);

                    user.sendPacket(entitySpawnPacket);
                    user.sendPacket(metadataPacket);

                    if(deleteAfter > 0) {
                        new BukkitRunnable() {

                            @SneakyThrows
                            @Override
                            public void run() {
                                Packet destroyArmorStand = new Packet(PacketType.Server.ENTITY_DESTROY);
                                destroyArmorStand.set(int[].class, new int[]{armorStand.getId()});

                                user.sendPacket(destroyArmorStand.getHandle());
                            }
                        }.runTaskLater(Warrior.getInstance(), deleteAfter);
                    }
                }

            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

}
