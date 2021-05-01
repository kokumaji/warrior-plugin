package com.dumbdogdiner.warrior.api.sessions;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.events.ArenaJoinEvent;
import com.dumbdogdiner.warrior.api.events.GameStateChangeEvent;
import com.dumbdogdiner.warrior.api.events.KillStreakChangeEvent;
import com.dumbdogdiner.warrior.api.events.KillstreakResetEvent;
import com.dumbdogdiner.warrior.api.kit.BaseKit;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class ArenaSession extends Session {

    public static ItemStack KIT_SELECTOR = new ItemBuilder(Material.IRON_SWORD)
            .setName("&8» &3&lSELECT KIT &8«")
            .setLore("&7Select One of Many Kits!")
            .build();

    public static ItemStack STATS_MENU(String owner) {
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName("&8» &3&lSTATS &8«")
                .setLore("&7View Your Warrior Stats")
                .setOwner(owner)
                .build();
    }

    public static ItemStack SPECTATE_GAME = new ItemBuilder(Material.COMPASS)
            .makeGlow(true)
            .setName("&8» &3&lSPECTATE &8«")
            .setLore("&7Enter Spectator Mode")
            .build();

    public static ItemStack EXIT_ARENA = new ItemBuilder(Material.BARRIER)
            .setName("&4&l☓ &c&lQUIT &4&l☓")
            .setLore("&7Return to Lobby")
            .build();

    @Accessors(fluent = true)
    @Getter @Setter
    private boolean canUseAbility;

    @Getter
    private final Arena arena;

    @Getter
    private GameState state;

    @Getter
    private int killStreak;

    @Getter @Setter
    private BaseKit kit;

    @Getter @Setter
    private long lastArrow;

    public ArenaSession(UUID uuid, Arena arena) {
        this(uuid, arena, GameState.PRE_GAME);
    }

    public ArenaSession(UUID uuid, Arena arena, GameState state) {
        super(uuid);
        this.arena = arena;

        Player player = Preconditions.checkNotNull(Bukkit.getPlayer(uuid), "Player cannot be null!");

        setState(state);
        this.setupInventory(player);
        ArenaJoinEvent e = new ArenaJoinEvent(this, player);
        Bukkit.getPluginManager().callEvent(e);
    }

    @Override
    public SessionType getType() {
        return SessionType.GAME;
    }

    public void setState(GameState newState) {
        GameState old = state;
        this.state = newState;
        GameStateChangeEvent e = new GameStateChangeEvent(this.state, newState, this, Objects.requireNonNull(Bukkit.getPlayer(getUserId())));
        Bukkit.getPluginManager().callEvent(e);
        if(e.isCancelled())
            this.state = old;
    }

    public void addKill() {
        WarriorUser user = WarriorAPI.getService().getPlayerManager().get(getUserId());
        user.addKill();
        killStreak++;

        KillStreakChangeEvent e = new KillStreakChangeEvent(killStreak, this);
        Bukkit.getPluginManager().callEvent(e);
    }

    public void resetStreak() {
        killStreak = 0;

        KillstreakResetEvent e = new KillstreakResetEvent(this);
        Bukkit.getPluginManager().callEvent(e);
    }

    public void setupInventory(Player player) {
        player.getInventory().clear();

        if(state.equals(GameState.PRE_GAME)) {

            player.getInventory().setItem(0, KIT_SELECTOR);
            player.getInventory().setItem(1, LobbySession.SHOP_ITEM);
            player.getInventory().setItem(4, SPECTATE_GAME);
            player.getInventory().setItem(7, STATS_MENU(player.getName()));
            player.getInventory().setItem(8, EXIT_ARENA);

        } else if(state.equals(GameState.SPECTATING)) {

            ItemStack spec = new ItemBuilder(Material.COMPASS)
                    .setName("&8» &3&lTELEPORT &8«")
                    .setLore("&7Teleport to a specific player")
                    .build();

            player.getInventory().setItem(4, spec);
            player.getInventory().setItem(8, EXIT_ARENA);
        }

    }

}
