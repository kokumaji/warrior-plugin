package com.dumbdogdiner.warrior.effects

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.warrior.api.sound.Sounds
import com.dumbdogdiner.warrior.api.util.MathUtil
import com.dumbdogdiner.warrior.nms.PacketType
import com.dumbdogdiner.warrior.nms.entity.NMSEntity
import com.dumbdogdiner.warrior.nms.entity.NMSEntityType
import com.dumbdogdiner.warrior.nms.networking.packets.Packet
import com.dumbdogdiner.warrior.user.User
import com.dumbdogdiner.warrior.util.NMSUtil
import org.bukkit.*
import org.bukkit.block.data.BlockData
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.lang.reflect.InvocationTargetException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

object WarriorEffects {

    val CONFETTI = { user: User ->

        user.spawnParticle(Particle.TOTEM, user.location, Vector(0.25, 0.25, 0.25), 100, 0.85)
        user.spawnParticle(Particle.FIREWORKS_SPARK, user.location, Vector(3, 3, 3), 100, 0.0)

        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, user, 6L)
        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, user, 12L)
        Sounds.playSoundDelayed(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, user, 15L)

        try {
            TimeUnit.SECONDS.sleep(2)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        
    }

    val LEVELUP = { user: User ->
        user.spawnParticle(Particle.TOTEM, user.location, Vector(0.25, 0.25, 0.25), 100, 0.85)
        user.spawnParticle(Particle.FIREWORKS_SPARK, user.location, Vector(3, 3, 3), 100, 0.0)

        Sounds.playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, user, 0.35f, 1f)
        user.sendTitle("&3&lLevel Up!", "&7You've reached &fLevel " + user.level, 4)

        try {
            TimeUnit.SECONDS.sleep(2)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    
    val BLOOD = { user: User, location: Location ->
        user.bukkitPlayer.spawnParticle<BlockData>(
            Particle.BLOCK_CRACK, location, 8, Bukkit.createBlockData(Material.REDSTONE_BLOCK)
        )
    }

    // candygore easter egg uwu
    val BLOOD_EASTEREGG = { user: User, location: Location ->
        val offset = Vector(0.15, 0.5, 0.15)

        user.spawnParticle(
            Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.RED_WOOL)
        )
        user.spawnParticle(
            Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.LIME_CONCRETE)
        )
        user.spawnParticle(
            Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.YELLOW_WOOL)
        )
        user.spawnParticle(
            Particle.BLOCK_CRACK, location, offset, 2, Bukkit.createBlockData(Material.CYAN_CONCRETE)
        )
    }

    @JvmStatic
    fun getGoreEffect(goreLevel: Int): ((User, Location) -> Unit)? {
        return if (goreLevel == 0) null else if (goreLevel == 1) BLOOD else BLOOD_EASTEREGG
    }

    @JvmStatic
    fun spawnExplosion(user: User, items: List<Material>, loc: Location, amount: Int) {
        try {
            for (i in 0 until amount) {
                val randomItem = MathUtil.randomElement(items)
                val nmsItem: Any = ItemBuilder(randomItem)
                                    .setName(MathUtil.generateId(4))
                                    .asNMSCopy()

                val r = 0.35

                val theta = MathUtil.randomDouble(2 * Math.PI * -1, 2 * Math.PI)
                val phi = MathUtil.randomDouble(-Math.PI, Math.PI)

                val x = r * cos(theta) * sin(phi)
                val y = r * sin(theta) * sin(phi)
                val z = r * cos(phi)

                val spawnLoc = loc.clone().add(x, y, z)

                val entityItem = NMSEntity(loc, NMSUtil.getNMSClass("EntityItem"), NMSEntityType.ITEM)

                entityItem.setNoGravity(false)

                val itemClass: Class<*> = NMSUtil.getNMSClass("EntityItem")

                itemClass.getMethod("setItemStack", NMSUtil.getNMSClass("ItemStack"))
                    .invoke(entityItem.entity, nmsItem)
                itemClass.getMethod("setPickupDelay", Integer.TYPE).invoke(entityItem.entity, Int.MAX_VALUE)

                val velocityPacket = Packet(PacketType.Play.Server.ENTITY_VELOCITY)
                velocityPacket.setInteger(0, entityItem.id)

                val velocityX = (spawnLoc.clone().subtract(loc).x * 2000).toInt()
                val velocityY = (spawnLoc.clone().subtract(loc).y * 8000).toInt()
                val velocityZ = (spawnLoc.clone().subtract(loc).z * 2000).toInt()

                velocityPacket.setInteger(1, velocityX)
                velocityPacket.setInteger(2, velocityY)
                velocityPacket.setInteger(3, velocityZ)

                val spawnItem = constructSpawnPacket(entityItem, spawnLoc)
                val metadata = Packet(PacketType.Play.Server.ENTITY_METADATA)

                metadata.setInteger(0, entityItem.id)
                metadata.setDataWatcherItems(entityItem.dataWatcher)

                user.sendPacket(spawnItem)
                user.sendPacket(metadata)
                user.sendPacket(velocityPacket)

                object : BukkitRunnable() {

                    override fun run() {
                        val destroyArmorStand = Packet(PacketType.Play.Server.DESTROY_ENTITIES)
                        destroyArmorStand[IntArray::class.java] = intArrayOf(entityItem.id)
                        user.sendPacket(destroyArmorStand)
                    }

                }.runTaskLater(Warrior.instance, 40L)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
    }

    private fun constructSpawnPacket(entity: NMSEntity, loc: Location): Packet {
        val spawnPacket = Packet(PacketType.Play.Server.SPAWN_ENTITY)
        spawnPacket.setInteger(0, entity.id)
        spawnPacket.setUUID(UUID.randomUUID())

        // set the location
        spawnPacket.setDouble(0, loc.x)
        spawnPacket.setDouble(1, loc.y)
        spawnPacket.setDouble(2, loc.z)
        spawnPacket.setInteger(4, loc.pitch.toInt())
        spawnPacket.setInteger(5, loc.yaw.toInt())

        // setting entity velocity to zero
        // to make minecraft ignore this
        spawnPacket.setInteger(1, 0)
        spawnPacket.setInteger(2, 0)
        spawnPacket.setInteger(3, 0)

        // set the entity type
        spawnPacket.setDeclared("k", entity.type)
        // data
        spawnPacket.setInteger(6, 0)
        return spawnPacket

    }
}