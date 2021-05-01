package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.effects.WarriorEffects.spawnExplosion
import com.dumbdogdiner.warrior.effects.WarriorEffects.getGoreEffect
import com.dumbdogdiner.warrior.api.sessions.ArenaSession
import com.dumbdogdiner.warrior.api.arena.gameflags.implementation.MaxHealthFlag
import com.dumbdogdiner.warrior.api.sessions.GameState
import com.dumbdogdiner.warrior.api.sessions.LobbySession
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.translation.Placeholders
import com.dumbdogdiner.warrior.managers.LevelManager
import com.dumbdogdiner.warrior.api.builders.ItemBuilder
import com.dumbdogdiner.warrior.api.builders.HologramBuilder
import com.dumbdogdiner.warrior.api.events.KillstreakResetEvent
import com.dumbdogdiner.warrior.api.events.KillStreakChangeEvent
import com.dumbdogdiner.warrior.api.builders.GameBossBar
import com.dumbdogdiner.warrior.api.sound.Melody
import com.dumbdogdiner.warrior.api.util.TranslationUtil
import com.dumbdogdiner.warrior.api.kit.kits.ArcherKit
import com.dumbdogdiner.warrior.api.sound.InstrumentSound
import com.dumbdogdiner.warrior.api.sound.Note
import com.dumbdogdiner.warrior.api.translation.Constants
import com.dumbdogdiner.warrior.api.util.MathUtil
import com.dumbdogdiner.warrior.user.User
import com.google.common.base.Preconditions
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.util.*

class ArenaSessionListener : Listener {

    private var clickTime = WeakHashMap<Player, Int>()

    @EventHandler
    fun onHealUse(e: PlayerInteractEvent) {
        val player = e.player
        if (Warrior.arenaManager.isPlaying(player)) {
            if (e.item == null) return

            val user: User = Warrior.userCache[player.uniqueId]
            val item = e.item
            val meta = item!!.itemMeta

            val arena = (user.session as ArenaSession?)!!.arena

            if (meta.displayName != "§8» §3§lFood §8«") return

            val maxHealth: Double = if (arena.flags.getFlag(MaxHealthFlag::class.java) != null)
                arena.flags.getFlag(MaxHealthFlag::class.java).value
            else Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH))!!.baseValue

            e.isCancelled = true
            if (player.health < maxHealth) {
                player.health = (player.health + 2.5).coerceAtMost(maxHealth)
                item.amount = item.amount - 1

                val slot = player.inventory.heldItemSlot
                player.inventory.setItem(slot, item)
                user.playSound(Sound.ENTITY_GENERIC_EAT, 0.5f, 1f)
            }
        }
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        val player = e.player
        if (Warrior.arenaManager.isPlaying(player)) {
            if (e.clickedBlock != null) {
                val block = e.clickedBlock
                if (block!!.type == Material.NOTE_BLOCK) {
                    e.isCancelled = true
                }
            }
            if (e.item == null) return
            val user: User = Warrior.userCache[player.uniqueId]
            val meta = e.item!!.itemMeta

            // credit to spazzylemons for this solution uwu
            val currentTick = Bukkit.getCurrentTick()
            if (clickTime[user.bukkitPlayer] == null || clickTime[user.bukkitPlayer] != currentTick) {
                clickTime[user.bukkitPlayer] = currentTick
                if (user.bukkitPlayer.openInventory.type == InventoryType.CHEST) return
                else if ((user.session as ArenaSession?)!!.state == GameState.IN_GAME) {
                    if (meta.displayName == "§8» §3§lSPECIAL ABILITY §8«") {
                        (user.session as ArenaSession?)!!.kit.activateAbility(user)
                    }
                } else if ((user.session as ArenaSession?)!!.state == GameState.SPECTATING) {
                    if (meta.displayName == "§4§l☓ §c§lQUIT §4§l☓") {
                        user.session = LobbySession(user.userId)
                        if (user.isSpectating) user.isSpectating = false
                    } else {
                        player.sendActionBar("§4§lFeature Not Implemented!")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val user: User = Warrior.userCache[e.entity.uniqueId]
        if (user.session !is ArenaSession) return

        val session = user.session as ArenaSession?

        object : BukkitRunnable() {
            override fun run() {
                e.entity.fireTicks = 0
            }
        }.runTaskLater(Warrior.instance, 2L)

        if (session!!.state == GameState.SPECTATING) (user.session as ArenaSession?)!!.state = GameState.PRE_GAME

        if (session.state == GameState.PRE_GAME) {
            (user.session as ArenaSession?)!!.state = GameState.PRE_GAME
            e.isCancelled = true
            e.drops.clear()

            object : BukkitRunnable() {
                override fun run() {
                    e.entity.teleport(session.arena.spawn)
                }
            }.runTaskLater(Warrior.instance, 2L)

        }
        if (session.state == GameState.IN_GAME) {
            e.isCancelled = true

            (user.session as ArenaSession?)!!.state = GameState.SPECTATING
            (user.session as ArenaSession?)!!.resetStreak()

            user.isSpectating = true
            user.bukkitPlayer.velocity = user.bukkitPlayer.velocity.add(Vector(0, 1, 0))

            user.addDeath()
            user.playDeathSound()
            user.showDeathParticles()

            object : BukkitRunnable() {

                var totalTime = 3

                override fun run() {
                    if (!user.bukkitPlayer.isOnline || user.session !is ArenaSession) {
                        cancel()
                        return
                    }
                    if (totalTime == 0) {
                        (user.session as ArenaSession?)!!.state = GameState.PRE_GAME
                        user.isSpectating = false
                        cancel()
                    } else {
                        val title = Placeholders.applyPlaceholders("{gameplay.you-died}", user.settings.language)
                        val subTitle = Placeholders.applyPlaceholders("{gameplay.respawn-in}", user.settings.language)
                        user.sendTitle(title, subTitle.replace("{time}", totalTime.toString()), 0, 25, 0)
                        user.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 0.5f, 1f)
                    }
                    totalTime--
                }
            }.runTaskTimer(Warrior.instance, 2L, 20L)
        }
    }

    @EventHandler
    fun onKill(e: PlayerDeathEvent) {
        if (e.entity.killer == null) return

        val killer = e.entity.killer
        if (killer == e.entity) return

        val killerUser: User = Warrior.userCache[e.entity.killer!!.uniqueId]
        if (killerUser.session !is ArenaSession) return
        (killerUser.session as ArenaSession?)!!.addKill()

        Preconditions.checkState(MIN_COINS in 1 until MAX_COINS, "Invalid value for property MIN_COINS")
        val coins = MathUtil.randomInt(MIN_COINS, MAX_COINS)
        killerUser.addCoins(coins)

        val xp = MathUtil.randomInt(LevelManager.MIN_XP, LevelManager.MAX_XP)
        killerUser.addExperience(xp)

        val loc = e.entity.location
        val nmsHead = ItemBuilder(Material.PLAYER_HEAD)
            .setOwner(e.entity.name)
            .asNMSCopy()

        HologramBuilder(loc)
            .setText("&c+1 Kill", String.format("§7+%d Coins", coins), String.format("§7+%d XP", xp))
            .withItem(nmsHead)
            .removeAfter(2)
            .sendTo(killerUser)

        if (killerUser.visualSettings.goreLevel == 2) {
            val rnd = MathUtil.randomDouble(0.0, 1.0)
            if (rnd > 0.99) { // 1% chance to see this easter egg uwu
                val sweetStuff = arrayOf(Material.COOKIE, Material.CAKE)
                spawnExplosion(killerUser, listOf(*sweetStuff), e.entity.location, 25)
                killerUser.playSound(Sound.ENTITY_PLAYER_BURP, 1f, 1f)
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onArrowDamage(e: EntityDamageByEntityEvent) {
        if (e.damager !is Projectile) return
        if (e.entity !is Player) return

        val projectile: Projectile = e.damager as Arrow
        if (projectile.shooter !is Player) return

        val victim: User = Warrior.userCache[e.entity.uniqueId]
        if (victim.session == null) return

        if (victim.session !is ArenaSession) {
            e.isCancelled = true
        } else if ((victim.session as ArenaSession?)!!.state != GameState.IN_GAME) {
            e.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onDamage(e: EntityDamageByEntityEvent) {
        if (e.entity !is Player) return
        if (e.damager !is Player) return
        val victim: User = Warrior.userCache[e.entity.uniqueId]
        val attacker: User = Warrior.userCache[e.damager.uniqueId]

        if (attacker.session !is ArenaSession) return
        if (victim.session !is ArenaSession) return

        val attackerState = (attacker.session as ArenaSession?)!!.state
        val victimState = (victim.session as ArenaSession?)!!.state

        if (victimState != GameState.IN_GAME || attackerState != GameState.IN_GAME) {
            e.isCancelled = true
        } else {
            val goreLevel = attacker.visualSettings.goreLevel
            val effect: ((User, Location) -> Unit)? = getGoreEffect(goreLevel)

            effect?.invoke(attacker, victim.location)
        }
    }

    @EventHandler
    fun onStreakReset(e: KillstreakResetEvent) {
        val user: User = Warrior.userCache[e.player.uniqueId]

        if (user.session !is ArenaSession) return

        if (Warrior.gameBarManager.getBossBar(user) != null) Warrior.gameBarManager.removePlayer(user)
    }

    @EventHandler
    fun onKillStreak(e: KillStreakChangeEvent) {
        val user: User = Warrior.userCache[e.player.uniqueId]
        if (user.session !is ArenaSession) return

        val session = user.session as ArenaSession?

        val barMsg = "§8» §7Killstreak §3§l" + e.streak + " §8«"
        val actionMsg = "§3§l+1 Kill §7(" + e.streak + " Total)"

        user.bukkitPlayer.sendActionBar(actionMsg)
        val abilityMin = session!!.kit.ability.minStreak
        val streak = session.killStreak
        val percent = getPercent(streak.toDouble(), abilityMin.toDouble())

        if (Warrior.gameBarManager.getBossBar(user) == null) {
            val bossBar = GameBossBar(barMsg, BarColor.WHITE, BarStyle.SOLID).setProgress(percent)
            Warrior.gameBarManager.addPlayer(user, bossBar)
        } else {
            if (!session.canUseAbility()) {
                Warrior.gameBarManager.updateTitle(user, barMsg)
                Warrior.gameBarManager.updateProgress(user, percent)
                Warrior.gameBarManager.updateColor(user, BarColor.WHITE)
            }
        }
        if (user.abilityActive) return
        if (!session.canUseAbility()) {
            if (session.kit.ability == null) return
            if (streak == 0) return
            if (streak % abilityMin == 0) {
                val melody = Melody(Instrument.XYLOPHONE, 2L, 2f, Note.C2, Note.E2, Note.G2)
                melody.play(user.bukkitPlayer)
                session.kit.ability.canExecute(user, true)
                val msg: String = Warrior.translator.translate(Constants.Lang.ABILITY_READY)
                user.sendMessage(TranslationUtil.getPrefix() + msg)
                Warrior.gameBarManager.updateColor(user, BarColor.BLUE)
                Warrior.gameBarManager.updateTitle(user, "§3§lSpecial Ability is Ready!")
            }
        }
    }

    private fun getPercent(streak: Double, abilityMin: Double): Double {
        if (streak == 0.0) return 0.0
        val v = streak % abilityMin / abilityMin
        return if (v == 0.0) 1.0 else v
    }

    @EventHandler
    fun onShoot(e: EntityShootBowEvent) {
        if (e.entity !is Player) return
        val p = e.entity as Player
        val user: User = Warrior.userCache[p.uniqueId]

        if (user.session !is ArenaSession) return
        if ((user.session as ArenaSession?)!!.kit !is ArcherKit) return

        (user.session as ArenaSession?)!!.lastArrow = System.currentTimeMillis()
        if (e.arrowItem.amount == 1) {
            val item = p.inventory.getItem(7)
            item!!.type = Material.GRAY_DYE
            p.inventory.setItem(7, item)
        }
    }

    @EventHandler
    fun onProjectileHit(e: ProjectileHitEvent) {
        val projectile = e.entity
        val loc = projectile.location
        if (e.hitEntity != null) {
            val shooter = e.entity.shooter as Entity?
            val target = e.hitEntity

            // particles can be annoying! so lets only
            // display them to the *actual* shooter
            if (target === shooter) return
            if (shooter is Player) {
                val userShooter: User = Warrior.userCache[shooter.uniqueId]
                val goreLevel = userShooter.visualSettings.goreLevel
                val effect: ((User, Location) -> Unit)? = getGoreEffect(goreLevel)
                effect?.invoke(userShooter, loc)
            }
            if (target is Player) {
                val userTarget: User = Warrior.userCache[target.uniqueId]
                val goreLevel = userTarget.visualSettings.goreLevel

                val effect: ((User, Location) -> Unit)? = getGoreEffect(goreLevel)
                effect?.invoke(userTarget, loc)
            }
            projectile.remove()
        }
        if (e.hitBlock != null) {
            val b = e.hitBlock

            //automatically clean up arrows.. we dont need them
            projectile.remove()
            projectile.world.spawnParticle(Particle.BLOCK_CRACK, projectile.location, 10, b!!.blockData)

            // another easter egg
            if (e.hitBlock!!.type == Material.NOTE_BLOCK) {
                val instrument = (e.hitBlock!!.blockData as NoteBlock).instrument
                val sound = InstrumentSound.fromInstrument(instrument)
                if (pointer > easter_egg.size - 1) pointer = 0
                projectile.world.playSound(
                    projectile.location, sound, 2f, easter_egg[pointer].pitch
                        .toFloat()
                )
                pointer++
            }
        }
    }

    companion object {
        private val MIN_COINS: Int = Warrior.instance.config.getInt("arena-settings.min-coins")
        private val MAX_COINS: Int = Warrior.instance.config.getInt("arena-settings.max-coins")
        private val easter_egg = arrayOf(
            Note.D2, Note.D2, Note.D3, Note.A2, Note.G2_SHARP, Note.G2, Note.F2, Note.D2, Note.F2, Note.G2
        )
        private var pointer = 0
    }
}