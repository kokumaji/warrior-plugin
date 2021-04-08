package com.dumbdogdiner.warrior.api.effects;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.nms.PacketType;
import com.dumbdogdiner.warrior.api.nms.entity.NMSEntity;
import com.dumbdogdiner.warrior.api.nms.entity.NMSEntityType;
import com.dumbdogdiner.warrior.api.nms.networking.packets.Packet;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.sound.Sounds;
import com.dumbdogdiner.warrior.api.util.MathUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import lombok.SneakyThrows;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import java.util.function.Consumer;

public class WarriorEffects {

    public static final Consumer<WarriorUser> CONFETTI = (user) -> {
        user.spawnParticle(Particle.TOTEM, user.getLocation(), new Vector(0.25, 0.25, 0.25), 100, 0.85);
        user.spawnParticle(Particle.FIREWORKS_SPARK, user.getLocation(), new Vector( 3, 3, 3), 100, 0);

        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, user, 6L);
        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, user, 12L);
        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, user, 15L);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    public static final Consumer<WarriorUser> LEVELUP = (user) -> {
        user.spawnParticle(Particle.TOTEM, user.getLocation(), new Vector(0.25, 0.25, 0.25), 100, 0.85);
        user.spawnParticle(Particle.FIREWORKS_SPARK, user.getLocation(), new Vector( 3, 3, 3), 100, 0);

        Sounds.playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, user, 0.35f, 1f);

        user.sendTitle("&3&lLevel Up!", "&7You've reached &fLevel " + user.getLevel(), 4);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    public static final BiConsumer<WarriorUser, Location> BLOOD = (user, location) -> {
        user.getBukkitPlayer().spawnParticle(Particle.BLOCK_CRACK, location, 8, Bukkit.createBlockData(Material.REDSTONE_BLOCK));
    };

    // candygore easter egg uwu
    public static final BiConsumer<WarriorUser, Location> BLOOD_EASTEREGG = (user, location) -> {
        Vector offset = new Vector(0.15, 0.5, 0.15);
        user.spawnParticle(Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.RED_WOOL));
        user.spawnParticle(Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.LIME_CONCRETE));
        user.spawnParticle(Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.YELLOW_WOOL));
        user.spawnParticle(Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.CYAN_CONCRETE));
    };

    public static BiConsumer<WarriorUser, Location> getGoreEffect(int goreLevel) {
        if(goreLevel == 0) return null;
        else if(goreLevel == 1) return BLOOD;
        else return BLOOD_EASTEREGG;
    }

    public static void spawnExplosion(WarriorUser user, List<Material> items, Location loc, int amount) {
        try {
            for (int i = 0; i < amount; i++) {
                Material randomItem = MathUtil.randomElement(items);
                Object nmsItem = new ItemBuilder(randomItem)
                        .setName(MathUtil.generateId(4))
                        .asNMSCopy();

                double r = 0.35;
                double theta = MathUtil.randomDouble((2 * Math.PI) * -1, 2 * Math.PI);
                double phi = MathUtil.randomDouble(-Math.PI, Math.PI);

                double x = r * Math.cos(theta) * Math.sin(phi);
                double y = r * Math.sin(theta) * Math.sin(phi);
                double z = r * Math.cos(phi);

                Location spawnLoc = loc.clone().add(x, y, z);
                NMSEntity entityItem = new NMSEntity(loc, NMSUtil.getNMSClass("EntityItem"), NMSEntityType.ITEM);
                entityItem.setNoGravity(false);

                Class<?> itemClass = NMSUtil.getNMSClass("EntityItem");
                itemClass.getMethod("setItemStack", NMSUtil.getNMSClass("ItemStack")).invoke(entityItem.getEntity(), nmsItem);
                itemClass.getMethod("setPickupDelay", Integer.TYPE).invoke(entityItem.getEntity(), Integer.MAX_VALUE);

                Packet velocityPacket = new Packet(PacketType.Play.Server.ENTITY_VELOCITY);
                velocityPacket.setInteger(0, entityItem.getId());

                int velocityX = (int) (spawnLoc.clone().subtract(loc).getX() * 2000);
                int velocityY = (int) (spawnLoc.clone().subtract(loc).getY() * 8000);
                int velocityZ = (int) (spawnLoc.clone().subtract(loc).getZ() * 2000);

                velocityPacket.setInteger(1, velocityX);
                velocityPacket.setInteger(2, velocityY);
                velocityPacket.setInteger(3, velocityZ);

                Packet spawnItem = _constructSpawnPacket(entityItem, spawnLoc);

                Packet metadata = new Packet(PacketType.Play.Server.ENTITY_METADATA);
                metadata.setInteger(0, entityItem.getId());
                metadata.setDataWatcherItems(entityItem.getDataWatcher());


                user.sendPacket(spawnItem);
                user.sendPacket(metadata);
                user.sendPacket(velocityPacket);

                new BukkitRunnable() {

                    @SneakyThrows
                    @Override
                    public void run() {
                        Packet destroyArmorStand = new Packet(PacketType.Play.Server.DESTROY_ENTITIES);
                        destroyArmorStand.set(int[].class, new int[]{entityItem.getId()});

                        user.sendPacket(destroyArmorStand);
                    }
                }.runTaskLater(WarriorAPI.getService().getInstance(), 40L);

            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static Packet _constructSpawnPacket(NMSEntity entity, Location loc) {
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
