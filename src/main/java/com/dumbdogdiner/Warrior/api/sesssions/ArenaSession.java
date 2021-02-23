package com.dumbdogdiner.Warrior.api.sesssions;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.events.ArenaJoinEvent;
import com.dumbdogdiner.Warrior.api.events.GameStateChangeEvent;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class ArenaSession implements Session {

    @Getter
    public UUID playerId;

    private final long startTime;

    @Getter
    private final Arena arena;

    @Getter
    private GameState state;

    public ArenaSession(UUID uuid, Arena arena) {
        this(uuid, arena, GameState.PRE_GAME);
    }

    public ArenaSession(UUID uuid, Arena arena, GameState state) {
        this.playerId = uuid;
        this.startTime = System.currentTimeMillis();
        this.arena = arena;

        setState(state);
        ArenaJoinEvent e = new ArenaJoinEvent(this, Objects.requireNonNull(Bukkit.getPlayer(uuid)));
        Bukkit.getPluginManager().callEvent(e);
    }

    @Override
    public SessionType getType() {
        return SessionType.GAME;
    }

    @Override
    public long getTimestamp() {
        return startTime;
    }

    public void setState(GameState newState) {
        GameState old = state;
        this.state = newState;
        GameStateChangeEvent e = new GameStateChangeEvent(this.state, newState, this, Objects.requireNonNull(Bukkit.getPlayer(playerId)));
        Bukkit.getPluginManager().callEvent(e);
        if(e.isCancelled())
            this.state = old;
    }

    public void setInventory() {
        WarriorUser user = PlayerManager.get(playerId);

        switch(state) {
            case PRE_GAME:

                ItemStack kit = new ItemBuilder(Material.IRON_SWORD)
                                .setName("&8» &3&lSELECT KIT &8«")
                                .setLore("&7Select One of Many Kits!")
                                .build();
                ItemStack shop = new ItemBuilder(Material.ENDER_CHEST)
                                .setName("&8» &3&lSHOP &8«")
                                .setLore("&7Browse & Unlock new Kits!")
                                .build();
                ItemStack exit = new ItemBuilder(Material.BARRIER)
                                .setName("&4&l☓ &c&lQUIT &4&l☓")
                                .setLore("&7Return to Lobby")
                                .build();
                ItemStack stats = new ItemBuilder(Material.PLAYER_HEAD)
                                .setName("&8» &3&lSTATS &8«")
                                .setLore("&7View Your Warrior Stats")
                                .build();
                ItemStack spectate = new ItemBuilder(Material.COMPASS)
                                .setName("&8» &3&lSPECTATE &8«")
                                .setLore("&7Enter Spectator Mode")
                                .build();

                Player p = Objects.requireNonNull(Bukkit.getPlayer(playerId));
                p.getInventory().clear();
                p.getInventory().setItem(0, kit);
                p.getInventory().setItem(1, shop);
                p.getInventory().setItem(4, spectate);
                p.getInventory().setItem(7, stats);
                p.getInventory().setItem(8, exit);
                break;

        }
    }
}
