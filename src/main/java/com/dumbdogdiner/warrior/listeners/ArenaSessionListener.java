package com.dumbdogdiner.warrior.listeners;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.effects.WarriorEffects;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.builders.GameBossBar;
import com.dumbdogdiner.warrior.api.builders.HologramBuilder;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.events.KillStreakChangeEvent;
import com.dumbdogdiner.warrior.api.events.KillStreakResetEvent;
import com.dumbdogdiner.warrior.api.kit.kits.ArcherKit;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.GameState;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.api.sound.InstrumentSound;
import com.dumbdogdiner.warrior.api.sound.Melody;
import com.dumbdogdiner.warrior.api.sound.Note;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.api.util.MathUtil;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.managers.GameBarManager;
import com.dumbdogdiner.warrior.managers.LevelManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import com.google.common.base.Preconditions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

public class ArenaSessionListener implements Listener {

    WeakHashMap<Player, Integer> clickTime = new WeakHashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(ArenaManager.isPlaying(p)) {
            if(e.getClickedBlock() != null) {
                Block b = e.getClickedBlock();

                if(b.getType() == Material.NOTE_BLOCK) {
                    e.setCancelled(true);
                }
            }

            if(e.getItem() == null) return;

            WarriorUser user = PlayerManager.get(p.getUniqueId());
            ItemMeta meta = e.getItem().getItemMeta();

            // credit to spazzylemons for this solution uwu
            int currentTick = Bukkit.getCurrentTick();
            if(clickTime.get(user.getBukkitPlayer()) == null || clickTime.get(user.getBukkitPlayer()) != currentTick) {
                clickTime.put(user.getBukkitPlayer(), currentTick);

                if (user.getBukkitPlayer().getOpenInventory().getType() == InventoryType.CHEST) return;

                else if(((ArenaSession)user.getSession()).getState() == GameState.IN_GAME) {
                    if(meta.getDisplayName().equals("§8» §3§lSPECIAL ABILITY §8«")) {
                        ((ArenaSession)user.getSession()).getKit().activateAbility(user);
                    }
                } else if(((ArenaSession)user.getSession()).getState() == GameState.SPECTATING) {
                    if(meta.getDisplayName().equals("§4§l☓ §c§lQUIT §4§l☓")) {
                        user.setSession(new LobbySession(user.getUserId()));
                        if(user.isSpectating()) user.setSpectating(false);
                    } else {
                        p.sendActionBar("§4§lFeature Not Implemented!");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f);
                    }
                }

            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        WarriorUser user = PlayerManager.get(e.getEntity().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        ArenaSession session = (ArenaSession) user.getSession();

        new BukkitRunnable() {

            @Override
            public void run() {
                e.getEntity().setFireTicks(0);
            }
        }.runTaskLater(Warrior.getInstance(), 2L);

        if(session.getState() == GameState.SPECTATING) ((ArenaSession) user.getSession()).setState(GameState.PRE_GAME);
        if(session.getState() == GameState.PRE_GAME) {
            ((ArenaSession) user.getSession()).setState(GameState.PRE_GAME);
            e.setCancelled(true);
            e.getDrops().clear();

            new BukkitRunnable() {

                @Override
                public void run() {
                    e.getEntity().teleport(session.getArena().getSpawn());
                }
            }.runTaskLater(Warrior.getInstance(), 2L);

        }
        if(session.getState() == GameState.IN_GAME) {
            e.setCancelled(true);

            ((ArenaSession) user.getSession()).setState(GameState.SPECTATING);
            ((ArenaSession) user.getSession()).resetStreak();
            user.setSpectating(true);
            user.getBukkitPlayer().setVelocity(user.getBukkitPlayer().getVelocity().add(new Vector(0, 1, 0)));
            user.addDeath();

            user.playDeathSound();
            user.showDeathParticles();

            new BukkitRunnable() {

                int totalTime = 3;

                @Override
                public void run() {
                    if(!user.getBukkitPlayer().isOnline()) {
                        cancel();
                        return;
                    }

                    if(!(user.getSession() instanceof ArenaSession)) {
                        cancel();
                        return;
                    }

                    if(this.totalTime == 0) {
                        ((ArenaSession) user.getSession()).setState(GameState.PRE_GAME);
                        user.setSpectating(false);
                        cancel();
                    } else {
                        user.getBukkitPlayer().sendTitle("§4§lYOU DIED", "§7Respawning in " +totalTime+ " seconds", 0, 25, 0);
                        user.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 0.5f, 1f);
                    }

                    totalTime--;
                }
            }.runTaskTimer(Warrior.getInstance(), 2L, 20L);
        }
    }

    private static final int MIN_COINS = Warrior.getInstance().getConfig().getInt("arena-settings.min-coins");
    private static final int MAX_COINS = Warrior.getInstance().getConfig().getInt("arena-settings.max-coins");

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        if(e.getEntity().getKiller() == null) return;
        Player killer = e.getEntity().getKiller();

        if(killer.equals(e.getEntity())) return;

        WarriorUser killerUser = PlayerManager.get(killer.getUniqueId());
        if(!(killerUser.getSession() instanceof ArenaSession)) return;

        ((ArenaSession)killerUser.getSession()).addKill();

        Preconditions.checkState(MIN_COINS > 0 && MIN_COINS < MAX_COINS, "Invalid value for property MIN_COINS");
        int coins = MathUtil.randomInt(MIN_COINS, MAX_COINS);
        killerUser.addCoins(coins);

        int xp = MathUtil.randomInt(LevelManager.MIN_XP, LevelManager.MAX_XP);
        killerUser.addExperience(xp);

        Location loc = e.getEntity().getLocation();

        Object nmsHead = new ItemBuilder(Material.PLAYER_HEAD)
                .setOwner(e.getEntity().getName())
                .asNMSCopy();

        new HologramBuilder(loc)
        .setText("&c+1 Kill", String.format("§7+%d Coins", coins), String.format("§7+%d XP", xp))
        .withItem(nmsHead)
        .removeAfter(2)
        .sendTo(killerUser);

        if(killerUser.getVisualSettings().getGoreLevel() == 2) {
            double rnd = MathUtil.randomDouble(0, 1);

            if(rnd > 0.99) {
                Material[] sweetStuff = new Material[]{Material.COOKIE, Material.CAKE};
                WarriorEffects.spawnExplosion(killerUser, Arrays.asList(sweetStuff), e.getEntity().getLocation(), 25);
                killerUser.playSound(Sound.ENTITY_PLAYER_BURP, 1f, 1f);
            }

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onArrowDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Projectile)) return;
        if(!(e.getEntity() instanceof Player)) return;

        Projectile projectile = (Arrow) e.getDamager();
        if(!(projectile.getShooter() instanceof Player)) return;

        WarriorUser victim = PlayerManager.get(e.getEntity().getUniqueId());

        if(victim.getSession() == null) return;

        if(!(victim.getSession() instanceof ArenaSession)) {
            e.setCancelled(true);
        } else if(((ArenaSession)victim.getSession()).getState() != GameState.IN_GAME) {
            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;

        WarriorUser victim = PlayerManager.get(e.getEntity().getUniqueId());
        WarriorUser attacker = PlayerManager.get(e.getDamager().getUniqueId());

        if(victim == null || attacker == null) return;

        if(!(attacker.getSession() instanceof ArenaSession)) return;
        if(!(victim.getSession() instanceof ArenaSession)) return;

        GameState attackerState = ((ArenaSession)attacker.getSession()).getState();
        GameState victimState = ((ArenaSession)victim.getSession()).getState();

        if(victimState != GameState.IN_GAME || attackerState != GameState.IN_GAME) {
            e.setCancelled(true);
        } else {
            int goreLevel = attacker.getVisualSettings().getGoreLevel();

            BiConsumer<WarriorUser, Location> effect = WarriorEffects.getGoreEffect(goreLevel);

            // if our shooter has blood/gore disabled, return.
            if(effect != null) effect.accept(attacker, victim.getLocation());
        }

    }

    @EventHandler
    public void onStreakReset(KillStreakResetEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;

        if(GameBarManager.getBossBar(user) != null) GameBarManager.removePlayer(user);
    }

    @EventHandler
    public void onKillStreak(KillStreakChangeEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        ArenaSession session = (ArenaSession) user.getSession();

        String barMsg = "§8» §7Killstreak §3§l" + e.getStreak() + " §8«";
        String actionMsg = "§3§l+1 Kill §7(" + e.getStreak() + " Total)";
        user.getBukkitPlayer().sendActionBar(actionMsg);

        int abilityMin = session.getKit().getAbility().getMinStreak();
        int streak = session.getKillStreak();

        double percent = getPercent(streak, abilityMin);

        if(GameBarManager.getBossBar(user) == null) {
            GameBossBar bossBar = new GameBossBar(barMsg, BarColor.WHITE, BarStyle.SOLID)
                    .setProgress(percent);
            GameBarManager.addPlayer(user, bossBar);
        } else {
            if(!session.canUseAbility()) {
                GameBarManager.updateTitle(user, barMsg);
                GameBarManager.updateProgress(user, percent);
                GameBarManager.updateColor(user, BarColor.WHITE);
            }
        }

        if(user.isAbilityActive()) return;

        if(!session.canUseAbility()) {
            if(session.getKit().getAbility() == null) return;

            if(streak == 0) return;

            if(streak % abilityMin == 0) {
                Melody melody = new Melody(Instrument.XYLOPHONE, 2L, 2f, Note.C2, Note.E2, Note.G2);
                melody.play(user.getBukkitPlayer());

                session.getKit().getAbility().canExecute(user, true);
                String msg = Warrior.getTranslator().translate(Constants.Lang.ABILITY_READY);
                user.sendMessage(TranslationUtil.getPrefix() + msg);

                GameBarManager.updateColor(user, BarColor.BLUE);
                GameBarManager.updateTitle(user, "§3§lSpecial Ability is Ready!");
            }

        }
    }

    private double getPercent(double streak, double abilityMin) {
        if (streak == 0) return 0.0;
        double v = (streak % abilityMin)/abilityMin;
        return v == 0 ? 1.0 : v;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        WarriorUser user = PlayerManager.get(p.getUniqueId());
        if(user == null) return;

        if(!(user.getSession() instanceof ArenaSession)) return;
        if(!(((ArenaSession) user.getSession()).getKit() instanceof ArcherKit)) return;
        ((ArenaSession) user.getSession()).setLastArrow(System.currentTimeMillis());

        if(e.getArrowItem().getAmount() == 1) {
            ItemStack item = p.getInventory().getItem(7);
            item.setType(Material.GRAY_DYE);

            p.getInventory().setItem(7, item);
        }
    }

    private static final Note[] easter_egg = {
            Note.D2, Note.D2, Note.D3, Note.A2, Note.G2_SHARP, Note.G2, Note.F2, Note.D2, Note.F2, Note.G2
    };

    private static int pointer = 0;

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        Location loc = projectile.getLocation();
        if (e.getHitEntity() != null) {
            Entity shooter = (Entity) e.getEntity().getShooter();
            Entity target = e.getHitEntity();

            // particles can be annoying! so lets only
            // display them to the *actual* shooter
            if(target == shooter) return;

            if(shooter instanceof Player) {
                WarriorUser userShooter = PlayerManager.get(shooter.getUniqueId());
                int goreLevel = userShooter.getVisualSettings().getGoreLevel();

                BiConsumer<WarriorUser, Location> effect = WarriorEffects.getGoreEffect(goreLevel);

                // if our shooter has blood/gore disabled, return.
                if(effect == null) return;
                else effect.accept(userShooter, loc);
            }

            if(target instanceof Player) {
                WarriorUser userTarget = PlayerManager.get(target.getUniqueId());
                int goreLevel = userTarget.getVisualSettings().getGoreLevel();

                BiConsumer<WarriorUser, Location> effect = WarriorEffects.getGoreEffect(goreLevel);

                // if our shooter has blood/gore disabled, return.
                if(effect == null) return;
                else effect.accept(userTarget, loc);
            }

            projectile.remove();
        }
        if(e.getHitBlock() != null) {
            Block b = e.getHitBlock();

            //automatically clean up arrows.. we dont need them
            projectile.remove();
            projectile.getWorld().spawnParticle(Particle.BLOCK_CRACK, projectile.getLocation(), 10, b.getBlockData());

            if (e.getHitBlock().getType().equals(Material.NOTE_BLOCK)) {
                Instrument instrument = ((NoteBlock) e.getHitBlock().getBlockData()).getInstrument();
                Sound sound = InstrumentSound.fromInstrument(instrument);

                if (pointer > easter_egg.length - 1) pointer = 0;
                projectile.getWorld().playSound(projectile.getLocation(), sound, 2f, (float) easter_egg[pointer].getPitch());
                pointer++;
            }
        }
    }

}
