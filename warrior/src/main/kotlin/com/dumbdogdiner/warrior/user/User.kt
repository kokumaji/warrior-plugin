package com.dumbdogdiner.warrior.user

import com.dumbdogdiner.warrior.api.user.WarriorUser
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathParticle
import com.dumbdogdiner.warrior.api.user.cosmetics.WarriorTitle
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings
import com.dumbdogdiner.warrior.api.user.settings.GameplaySettings
import com.dumbdogdiner.warrior.api.user.settings.VisualSettings
import com.dumbdogdiner.warrior.nms.PacketType
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import java.lang.Runnable
import com.dumbdogdiner.warrior.effects.WarriorEffects
import com.dumbdogdiner.warrior.api.events.SessionChangeEvent
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathSound
import java.util.concurrent.TimeUnit
import java.lang.InterruptedException
import com.dumbdogdiner.warrior.util.NMSUtil
import java.lang.NoSuchMethodException
import java.lang.reflect.InvocationTargetException
import java.lang.IllegalAccessException
import java.lang.NoSuchFieldException
import com.dumbdogdiner.warrior.api.events.WarriorLevelUpEvent
import com.dumbdogdiner.warrior.api.sessions.Session
import com.dumbdogdiner.warrior.api.sound.Note
import com.dumbdogdiner.warrior.api.user.UserData
import com.dumbdogdiner.warrior.api.translation.Symbols
import com.dumbdogdiner.warrior.nms.enums.MessageType
import com.dumbdogdiner.warrior.nms.networking.packets.Packet
import com.google.common.base.Preconditions
import io.netty.channel.Channel
import lombok.Getter
import lombok.Setter
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Consumer

/**
 * Creates a WarriorUser instance to access
 * plugin specific functionalities.
 */
class User(player: Player) : WarriorUser<User> {

    // GETTERS & SETTERS FOR USER DATA

    private var kills = 0
    override fun getKills(): Int {
        return kills
    }

    private var deaths = 0
    override fun getDeaths(): Int {
        return deaths
    }

    private var coins = 0
    override fun getCoins(): Int {
        return coins
    }

    /**
     * Represents the progress on the current level.
     */
    private var relativeXp = 0
    override fun getRelativeXp(): Int {
        return relativeXp
    }

    /**
     * Represents the total amount of XP of this user.
     */
    private var totalXp = 0
    override fun getTotalXp(): Int {
        return totalXp
    }

    /**
     * Represents the xp level of this user
     */
    private var level = 0
    override fun getLevel(): Int {
        return level
    }

    @Getter
    private val userId: UUID

    private var firstJoin: Long = 0
    override fun getFirstJoin(): Long {
        return firstJoin
    }

    private var lastJoin: Long = 0
    override fun getLastJoin(): Long {
        return lastJoin
    }

    private var totalTime: Long = 0
    override fun getTotalTime(): Long {
        return totalTime
    }

    private var session: Session? = null
    override fun getSession(): Session? {
        return session
    }

    @Getter
    private var spectating = false

    @Getter
    @Setter
    private val abilityActive = false

    @Getter
    private val activeParticle = DeathParticle.HEART

    private var activeTitle: WarriorTitle? = null
    override fun getActiveTitle(): WarriorTitle {
        return activeTitle ?: WarriorTitle.EMPTY
    }

    override fun setActiveTitle(title: WarriorTitle?) {
        this.activeTitle = title
    }

    // REFLECTION STUFF

    /**
     * Get the CraftPlayer instance of this player.
     */
    @Getter
    private var craftPlayer: Any? = null

    /**
     * Get the NetworkManager instance of this player.
     */
    @Getter
    private var networkManager: Any? = null

    /**
     * Get the current active connection of this player.
     */
    @Getter
    private var playerConnection: Any? = null

    /**
     * Get the [Player] instance of this player.
     */
    private val bukkitPlayer: Player
    override fun getBukkitPlayer(): Player {
        return bukkitPlayer
    }

    /**
     *
     * START OF DATA STORAGE
     *
     * Compact data storage for [DeathSounds].
     * Each bit represents one death sound and
     * its unlock state for this player.
     *
     * (0 = locked, 1 = unlocked)
     */

    private var deathSounds = 0
    override fun getDeathSounds(): Int {
        return deathSounds
    }

    /**
     * Compact data storage for DeathParticles.
     * Each bit represents one death particle and
     * its unlock state for this player.
     *
     * (0 = locked, 1 = unlocked)
     */

    private var deathParticles = 0
    override fun getDeathParticles(): Int {
        return deathParticles
    }

    /**
     * Compact data storage for Titles.
     * Each bit represents one title and
     * its unlock state for this player.
     *
     * (0 = locked, 1 = unlocked)
     */
    private var titles = 0
    override fun getTitles(): Int {
        return titles
    }

    /**
     * Get the general user settings
     * for this player.
     */
    private var settings: GeneralSettings? = null
    override fun getGameplaySettings(): GameplaySettings {
        return settings
    }

    /**
     * Get the gameplay specific settings
     * for this player.
     */
    @Getter
    private var gameplaySettings: GameplaySettings? = null

    /**
     * Get the visuals specific settings
     * for this player.
     */
    @Getter
    private var visualSettings: VisualSettings? = null
    override fun getVisualSettings(): VisualSettings {
        return visualSettings
    }

    /**
     * Creates a WarriorUser instance to access
     * plugin specific functionalities.
     *
     * @param uuid UUID of a valid [Player] instance
     */
    constructor(uuid: UUID?) : this(Objects.requireNonNull(Bukkit.getPlayer(uuid!!))!!)

    override fun getUniqueId(): UUID {
        return bukkitPlayer.uniqueId
    }

    override fun getAbilityActive(): Boolean {
        return false
    }

    val generalSettings: GeneralSettings?
        get() = null

    /**
     * See [Player.getEntityId].
     */
    override fun getEntityId(): Int {
        return bukkitPlayer.entityId
    }

    /**
     * See [Player.getName].
     */
    override fun getName(): String {
        return bukkitPlayer.name
    }

    /**
     * See [Player.sendMessage].
     */
    override fun sendMessage(s: String) {
        bukkitPlayer.sendMessage(s)
    }

    /**
     * See [Player.getWorld].
     * @return the world this user
     * is currently in.
     */
    override fun getWorld(): World {
        return bukkitPlayer.world
    }

    /**
     * See [Player.getLocation].
     * @return the location of this player
     */
    override fun getLocation(): Location {
        return bukkitPlayer.location
    }

    /**
     * Sends an action bar message using reflection
     * @param msg the message that should be displayed.
     */
    override fun sendActionBar(msg: String) {
        val messageType = MessageType.toNMS(MessageType.GAME_INFO)
        val actionMessage = Packet(PacketType.Play.Server.CHAT_MESSAGE_CLIENTBOUND)

        actionMessage.setChatComponent(0, TranslationUtil.translateColor(msg))
        actionMessage[messageType.javaClass] = messageType
        actionMessage.setUUID(UUID(0, 0))

        sendPacket(actionMessage)
    }

    override fun sendTitle(title: String, subtitle: String, stay: Int) {
        var title: String? = title
        var subtitle: String? = subtitle

        title = TranslationUtil.translateColor(title)
        subtitle = TranslationUtil.translateColor(subtitle)

        bukkitPlayer.sendTitle(title, subtitle, 20, stay, 20)
    }

    override fun sendTitle(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        var title: String? = title
        var subtitle: String? = subtitle
        title = TranslationUtil.translateColor(title)
        subtitle = TranslationUtil.translateColor(subtitle)
        bukkitPlayer.sendTitle(title, subtitle, fadeIn, stay, fadeOut)
    }

    fun spawnEffect(func: (User) -> Unit) {
        executeAsync(func)
    }

    override fun executeAsync(func: Consumer<User>?) {
        userPool.submit { func!!.accept(this) }
    }

    override fun reset() {
        // instead of setting the users health to 20, use the max allowed health as reference
        bukkitPlayer.health = Objects.requireNonNull(bukkitPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH))!!.baseValue
        bukkitPlayer.fireTicks = 0
        bukkitPlayer.level = 0
        bukkitPlayer.exhaustion = 0f
    }

    /**
     * Removes a collection of potion effects.
     * @param potionEffects collection of potion effects.
     */
    override fun removeEffects(potionEffects: Collection<PotionEffect>) {
        for (effect in potionEffects) {
            if (bukkitPlayer.hasPotionEffect(effect.type)) bukkitPlayer.removePotionEffect(effect.type)
        }
    }

    /**
     * Removes a collection of potion effects.
     * @param potionEffects array of potion effects
     */
    override fun removeEffects(vararg potionEffects: PotionEffect) {
        removeEffects(listOf(*potionEffects))
    }

    /**
     * Removes all active potion effects from the player.
     */
    override fun removeEffects() {
        removeEffects(bukkitPlayer.activePotionEffects)
    }

    /**
     * Changes the [Session] of a player.
     * Calls the [SessionChangeEvent] every time
     * this method is used.
     *
     * @param session instance of [Session]
     */
    override fun setSession(session: Session) {
        val e = SessionChangeEvent(this.session, session, bukkitPlayer)
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getPluginManager().callEvent(e)
            }
        }.runTask(Warrior.instance)
        this.session = session
    }

    /**
     * Plays a sound to the player at the player's location
     *
     * @param sound A [Sound] enum
     * @param volume the volume at which this sound should be played at
     * @param pitch the pitch at which this sound should be played at
     */
    override fun playSound(sound: Sound, volume: Float, pitch: Float) {
        bukkitPlayer.playSound(bukkitPlayer.location, sound, volume, pitch)
    }

    /**
     * Plays a sound to the player at the player's location
     *
     * @param sound A [Sound] enum
     * @param volume the volume at which this sound should be played at
     * @param note A [Note] enum
     */
    override fun playSound(sound: Sound, volume: Float, note: Note) {
        bukkitPlayer.playSound(bukkitPlayer.location, sound, volume, note.pitch.toFloat())
    }

    override fun spawnParticle(particle: Particle, loc: Location, amount: Int) {
        this.spawnParticle<Any?>(particle, loc, Vector(0, 0, 0), amount, null)
    }

    override fun spawnParticle(particle: Particle, loc: Location, offset: Vector, amount: Int, extra: Double) {
        var amount = amount
        val particleLevel = visualSettings!!.particleMode
        if (particleLevel == 0) return  // 0 = disabled
        else if (particleLevel == 1 && amount > 10) {
            amount /= 2
        }

        bukkitPlayer.spawnParticle(particle, loc, amount, offset.x, offset.y, offset.z, extra)
    }

    override fun spawnParticle(particle: Particle, loc: Location, offset: Vector, amount: Int) {
        this.spawnParticle<Any?>(particle, loc, offset, amount, null)
    }

    override fun <T> spawnParticle(particle: Particle, loc: Location, offset: Vector, amount: Int, data: T) {
        var amount = amount
        val particleLevel = visualSettings!!.particleMode
        if (particleLevel == 0) return  // 0 = disabled
        else if (particleLevel == 1 && amount > 10) {
            amount /= 2
        }
        bukkitPlayer.spawnParticle(
            particle,
            loc,
            amount,
            offset.x,
            offset.y,
            offset.z,
            data
        )
    }

    override fun setActiveSound(sound: DeathSound) {
        gameplaySettings!!.activeSound = sound
    }

    override fun setActiveParticle(particle: DeathParticle) {
        gameplaySettings!!.activeParticle = particle
    }

    /**
     * Experimental method to unlock a sound for a player.
     * May be removed/replaced in future releases.
     *
     * @param sound The [DeathSound] that should be unlocked
     */
    override fun unlockSound(sound: DeathSound) {
        userPool.submit {
            deathSounds = deathSounds or sound.unlockValue
            playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.2f, 1f)
            bukkitPlayer.sendTitle("§3§lNew Unlock!", "§7You've unlocked Sound §f" + sound.friendlyName(), 10, 80, 10)
            spawnEffect(WarriorEffects.CONFETTI)
            try {
                TimeUnit.SECONDS.sleep(5)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Experimental method to unlock a sound for a player.
     * May be removed/replaced in future releases.
     *
     * @param particle The [DeathParticle] that should be unlocked
     */
    override fun unlockParticle(particle: DeathParticle) {
        deathParticles = deathParticles or particle.unlockValue
        playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.2f, 1f)
        bukkitPlayer.sendTitle(
            "§3§lNew Unlock!",
            "§7You've unlocked Particle §f" + particle.friendlyName(),
            10,
            80,
            10
        )
        bukkitPlayer.spawnParticle(Particle.HEART, bukkitPlayer.location, 30, 2.0, 2.0, 2.0)
    }

    /**
     * Experimental method to unlock a sound for a player.
     * May be removed/replaced in future releases.
     *
     * @param title The [WarriorTitle] that should be unlocked
     */
    override fun unlockTitle(title: WarriorTitle) {
        if (!WarriorTitle.canUnlock(title)) return
        titles = titles or title.unlockValue
        playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.2f, 1f)
        bukkitPlayer.sendTitle("§3§lNew Unlock!", "§7You've unlocked Title §f" + title.title, 10, 80, 10)
        bukkitPlayer.spawnParticle(Particle.HEART, bukkitPlayer.location, 30, 2.0, 2.0, 2.0)
    }

    /**
     * Plays the death sound of this player at their location.
     */
    override fun playDeathSound() {
        val loc = bukkitPlayer.location
        loc.world.playSound(loc, gameplaySettings!!.activeSound.sound, 1f, 1f)
    }

    /**
     * Displays the death particles of this player at their location.
     */
    override fun showDeathParticles() {
        val loc = bukkitPlayer.location
        loc.world.spawnParticle(gameplaySettings!!.activeParticle.particle, loc, 15, 0.35, 0.35, 0.35)
    }

    /**
     * Sends a Packet via reflection. This method is currently designed
     * to accept raw notchian packets. This may change in the future
     * with the implementation of an in-house packet layer.
     *
     * @param packet the packet.
     */
    fun sendPacket(packet: Packet) {
        _sendPacket(packet.handle)
    }

    /**
     * handles the internal reflection part for
     * sending packets
     */
    private fun _sendPacket(packet: Any) {
        try {
            val nmsPacket = NMSUtil.NMS_PACKET_CLASS
            val conn: Any? = playerConnection
            val sendPacket = conn?.javaClass?.getMethod("sendPacket", nmsPacket)
            sendPacket?.invoke(conn, packet)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    /**
     * Gets the [Channel] instance of the CraftBukkit player.
     *
     * @return See [Channel]
     */
    override fun getChannel(): Channel {
        var ch: Channel? = null
        try {
            val channel = NETWORKMANAGER_CLASS.getField("channel")
            ch = channel[networkManager] as Channel
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return ch!!
    }

    /**
     * Returns the players current ping.
     *
     * @return [Integer] representing the players ping
     */
    override fun getPing(): Int {
        return try {
            val pingField = ENTITYPLAYER_CLASS.getField("ping")
            pingField.getInt(craftPlayer)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            0
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Sets the spectating state of this player.
     *
     * @param spectating whether the player should be in
     * spectator mode
     */
    override fun setSpectating(spectating: Boolean) {
        this.spectating = spectating
        object : BukkitRunnable() {
            override fun run() {
                if (spectating) {
                    bukkitPlayer!!.gameMode = GameMode.ADVENTURE
                    Warrior.specTeam.addEntry(bukkitPlayer.name)
                    bukkitPlayer.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, Int.MAX_VALUE, 1))

                    for (p in Bukkit.getOnlinePlayers()) {
                        if (Warrior.specTeam.hasEntry(p.name)) continue
                        p.hidePlayer(Warrior.instance, bukkitPlayer)
                    }
                    for (s in Warrior.specTeam.entries) {
                        val p = Bukkit.getPlayer(s!!) ?: return
                        bukkitPlayer.showPlayer(Warrior.instance, p)
                    }
                } else {
                    Warrior.specTeam.removeEntry(bukkitPlayer!!.name)
                    for (p in Bukkit.getOnlinePlayers()) {
                        p.showPlayer(Warrior.instance, bukkitPlayer)
                    }
                    for (s in Warrior.specTeam.getEntries()) {
                        val p = Bukkit.getPlayer(s!!) ?: return
                        bukkitPlayer.hidePlayer(Warrior.instance, p)
                    }
                    for (effect in bukkitPlayer.activePotionEffects) bukkitPlayer.removePotionEffect(effect.type)
                }
                bukkitPlayer.allowFlight = spectating
                bukkitPlayer.isFlying = spectating
            }
        }.runTask(Warrior.instance)
    }

    override fun addExperience(exp: Int) {
        val nextXp: Int = Warrior.levelManager.levelToXp(level)
        relativeXp += exp
        totalXp += exp
        if (relativeXp >= nextXp && level <= 100) {
            relativeXp -= nextXp
            level++
            val e = WarriorLevelUpEvent(this)
            Bukkit.getPluginManager().callEvent(e)
        }
        updateExperienceBar()
    }

    private fun updateExperienceBar() {
        bukkitPlayer.level = level
        val actualProgress = 0.0.coerceAtLeast(Warrior.levelManager.getProgress(this))
        bukkitPlayer.exp = 0.99.coerceAtMost(actualProgress).toFloat()
    }

    /**
     * Increments the [User.getKills] value by one.
     */
    override fun addKill() {
        kills++
    }

    /**
     * Increments the [User.getDeaths] value by one.
     */
    override fun addDeath() {
        deaths++
    }

    /**
     * Returns the Kills/Deaths ratio. **May return
     * amount of kills if player doesnt have any deaths.**
     *
     * @return Kills/Deaths ratio as [Double]
     */
    override fun getKDR(): Double {
        return if (deaths < 1) kills.toDouble() else kills.toDouble() / deaths.toDouble()
    }

    /**
     * Adds the specified amount of coins to the players balance.
     *
     * @param amount [Integer]
     */
    override fun addCoins(amount: Int) {
        Preconditions.checkState(amount > 0, "Cannot add a negative amount of coins!")
        coins = coins + amount
    }

    /**
     * Sets the player balance to the specified amount.
     *
     * @param amount [Integer]
     */
    override fun setCoins(amount: Int) {
        Preconditions.checkState(amount > -1, "Cannot set coins to a negative value!")
        coins = amount
    }

    /**
     * Removes the specified amount of coins to the players balance.
     *
     * @param amount [Integer]
     */
    override fun removeCoins(amount: Int) {
        Preconditions.checkState(amount > 0, "Cannot remove a negative amount of coins!")
        coins = 0.coerceAtLeast(coins - amount)
    }

    override fun compareTo(user: User): Int {
        return this.entityId.compareTo(user.entityId)
    }

    /**
     * Gets the user data from a connected database
     */
    fun loadData() {
        val data: UserData = Warrior.connection.getData(userId)
        settings = Warrior.connection.getUserSettings(userId)
        gameplaySettings = Warrior.connection.getGameplaySettings(userId)
        visualSettings = Warrior.connection.getVisualSettings(userId)
        if (data.isSuccessful) {
            val msg = String.format("&2%1\$s &aPlayer Data Loaded &2%1\$s", Symbols.HEAVY_CHECK_MARK)
            sendActionBar(TranslationUtil.translateColor(msg))
            playSound(Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        }

        kills = data.getKills()
        deaths = data.getDeaths()
        coins = data.getCoins()
        deathSounds = data.getDeathSounds()
        deathParticles = data.getDeathParticles()
        titles = data.getTitles()
        firstJoin = data.getFirstJoin()
        lastJoin = System.currentTimeMillis()
        totalTime = data.getTotalTime()
        relativeXp = data.getRelativeXp()
        totalXp = data.getTotalXp()
        level = Warrior.levelManager.xpToLevel(data.getTotalXp())
        activeTitle = settings!!.title

        updateExperienceBar()
    }

    fun saveData() {
        val data = UserData(this)
        Warrior.connection.saveData(data)
        Warrior.connection.saveSettings(settings)
        Warrior.connection.saveGameplaySettings(gameplaySettings)
        Warrior.connection.saveVisualSettings(visualSettings)
    }

    companion object {
        /**
         * TODO: should be replaced with task queue!
         */
        private val userPool = Executors.newCachedThreadPool()

        /**
         * used for reflection
         */
        private val ENTITYPLAYER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("EntityPlayer"))

        /**
         * used for reflection
         */
        private val NETWORKMANAGER_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("NetworkManager"))
    }

    /**
     * Creates a WarriorUser instance to access
     * plugin specific functionalities.
     *
     * @param player A [Player] instance
     */
    init {
        Preconditions.checkNotNull(player, "Player cannot be null!")
        bukkitPlayer = player
        userId = bukkitPlayer.getUniqueId()

        // Resetting our player
        reset()
        if (player.activePotionEffects.size > 0) removeEffects()

        // if the player disconnects while in spectator
        // we have to remove them from the spectator team
        isSpectating = false

        // if our player doesn't have bypass perms, apply adventure mode
        // to possibly remove spectator mode side-effects.
        if (!player.hasPermission("warrior.lobby.bypass")) {
            player.isFlying = false
            player.gameMode = GameMode.ADVENTURE
        }
        updateExperienceBar()
        try {
            // Let's create a few nms related objects!
            craftPlayer = player.javaClass.getMethod("getHandle").invoke(player)
            val conField = ENTITYPLAYER_CLASS.getField("playerConnection")
            playerConnection = conField[craftPlayer]
            val nmField = playerConnection.javaClass.getField("networkManager")
            networkManager = nmField[playerConnection]
        } catch (e: IllegalAccessException) {
            val msg = String.format(
                "Oh no! Something went wrong while trying to create a WarriorUser instance! %s",
                e.message
            )
            Warrior.pluginLogger.error(msg)
            if (Warrior.instance.config.getBoolean("general-settings.debug-mode")) e.printStackTrace()
        } catch (e: InvocationTargetException) {
            val msg = String.format(
                "Oh no! Something went wrong while trying to create a WarriorUser instance! %s",
                e.message
            )
            Warrior.pluginLogger.error(msg)
            if (Warrior.instance.config.getBoolean("general-settings.debug-mode")) e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            val msg = String.format(
                "Oh no! Something went wrong while trying to create a WarriorUser instance! %s",
                e.message
            )
            Warrior.pluginLogger.error(msg)
            if (Warrior.instance.config.getBoolean("general-settings.debug-mode")) e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            val msg = String.format(
                "Oh no! Something went wrong while trying to create a WarriorUser instance! %s",
                e.message
            )
            Warrior.pluginLogger.error(msg)
            if (Warrior.instance.config.getBoolean("general-settings.debug-mode")) e.printStackTrace()
        }
    }
}