package com.dumbdogdiner.warrior.api.builders;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.nms.PacketType;
import com.dumbdogdiner.warrior.nms.entity.NMSEntity;
import com.dumbdogdiner.warrior.nms.entity.NMSEntityType;
import com.dumbdogdiner.warrior.nms.networking.packets.Packet;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.UUID;

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
                    Location itemLoc = location.clone().add(new Vector(0, 1.5, 0));
                    NMSEntity entityItem = new NMSEntity(itemLoc, NMSUtil.getNMSClass("EntityItem"), NMSEntityType.ITEM);
                    entityItem.setNoGravity(true);

                    Class<?> itemClass = NMSUtil.getNMSClass("EntityItem");
                    itemClass.getMethod("setItemStack", NMSUtil.getNMSClass("ItemStack")).invoke(entityItem.getEntity(), nmsItem);

                    Packet velocity = new Packet(PacketType.Play.Server.ENTITY_VELOCITY);
                    velocity.setInteger(0, entityItem.getId());
                    velocity.setInteger(1, 0);
                    velocity.setInteger(2, 0);
                    velocity.setInteger(3, 0);

                    Packet spawnItem = _constructSpawnPacket(entityItem, itemLoc);

                    Packet metadata = new Packet(PacketType.Play.Server.ENTITY_METADATA);
                    metadata.setInteger(0, entityItem.getId());
                    metadata.setDataWatcherItems(entityItem.getDataWatcher());

                    user.sendPacket(spawnItem);
                    user.sendPacket(velocity);
                    user.sendPacket(metadata);


                    if(deleteAfter > 0) {
                        new BukkitRunnable() {

                            @SneakyThrows
                            @Override
                            public void run() {
                                Packet destroyItem = new Packet(PacketType.Play.Server.DESTROY_ENTITIES);
                                destroyItem.set(int[].class, new int[]{entityItem.getId()});

                                user.sendPacket(destroyItem);
                            }
                        }.runTaskLater(WarriorAPI.getService().getInstance(), deleteAfter);
                    }
                }

                for(String line : text) {

                    NMSEntity armorStand = new NMSEntity(location, NMSUtil.getNMSClass("EntityArmorStand"), NMSEntityType.ARMOR_STAND);
                    armorStand.setCustomName(line);
                    armorStand.setCustomNameVisible(true);
                    armorStand.setInvisible(true);
                    armorStand.setNoGravity(true);

                    Class<?> asClass = armorStand.getEntity().getClass();
                    asClass.getMethod("setSmall", Boolean.TYPE).invoke(armorStand.getEntity(), true);
                    asClass.getMethod("setBasePlate", Boolean.TYPE).invoke(armorStand.getEntity(), false);

                    Packet spawnEntity = _constructSpawnPacket(armorStand, location);
                    location.subtract(0, HOLOGRAM_OFFSET, 0);

                    Packet metadata = new Packet(PacketType.Play.Server.ENTITY_METADATA);
                    metadata.setInteger(0, armorStand.getId());
                    metadata.setDataWatcherItems(armorStand.getDataWatcher());

                    user.sendPacket(spawnEntity);
                    user.sendPacket(metadata);

                    if(deleteAfter > 0) {
                        new BukkitRunnable() {

                            @SneakyThrows
                            @Override
                            public void run() {
                                Packet destroyArmorStand = new Packet(PacketType.Play.Server.DESTROY_ENTITIES);
                                destroyArmorStand.set(int[].class, new int[]{armorStand.getId()});

                                user.sendPacket(destroyArmorStand);
                            }
                        }.runTaskLater(WarriorAPI.getService().getInstance(), deleteAfter);
                    }
                }

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    private Packet _constructSpawnPacket(NMSEntity entity, Location loc) {
        Packet spawnPacket = new Packet(PacketType.Play.Server.SPAWN_ENTITY);
        spawnPacket.setInteger(0, entity.getId());
        spawnPacket.setUUID(UUID.randomUUID());

        // set the location
        spawnPacket.setDouble(0, loc.getX());
        spawnPacket.setDouble(1, loc.getY());
        spawnPacket.setDouble(2, loc.getZ());
        spawnPacket.setInteger(4, (int) loc.getPitch());
        spawnPacket.setInteger(5, (int) loc.getYaw());

        // setting entity velocity to zero
        // to make minecraft ignore this
        spawnPacket.setInteger(1, 0);
        spawnPacket.setInteger(2, 0);
        spawnPacket.setInteger(3, 0);

        // set the entity type
        spawnPacket.setDeclared("k", entity.getType());
        // data
        spawnPacket.setInteger(6, 0);

        return spawnPacket;
    }

}
