package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

@Getter @Setter
public class ArenaBuilderSession {

    public enum SessionResult {
        CANCEL,
        CONFIRM
    }

    public enum PositionType {
        LOC1("Position 1"),
        LOC2("Position 2"),
        SPAWN("Spawn");

        @Getter
        private final String lang;

        PositionType(String type) {
            this.lang = type;
        }
    }

    private Listener listener;

    private WarriorUser sessionUser;
    private String arenaName;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private ItemStack[] inventoryCapture;

    private World world;

    private Location pos1;
    private Location pos2;
    private Location spawn;

    public ArenaBuilderSession(WarriorUser user, String arenaName) {
        this.sessionUser = user;
        this.arenaName = arenaName;
        this.world = user.getBukkitPlayer().getWorld();
        inventoryCapture = user.getBukkitPlayer().getInventory().getContents();
    }

    public void setPosition(PositionType type) {
        if(type.equals(PositionType.LOC1)) setPos1(sessionUser.getBukkitPlayer().getLocation());
        if(type.equals(PositionType.LOC2)) setPos2(sessionUser.getBukkitPlayer().getLocation());
        if(type.equals(PositionType.SPAWN)) setSpawn(sessionUser.getBukkitPlayer().getLocation());
        String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_BUILDER_LOCATION, new HashMap<String, String>() {
            {
                put("LOCATION", TranslationUtil.readableLocation(sessionUser.getBukkitPlayer().getLocation(), true, false));
                put("TYPE", type.getLang());
            }
        });
        sessionUser.getBukkitPlayer().sendMessage(TranslationUtil.getPrefix() + msg);
    }

    protected void restoreInventory() {
        Player player = sessionUser.getBukkitPlayer();
        player.getInventory().clear();
        for(int i = 0; i < inventoryCapture.length; i++) {
            if(inventoryCapture[i] == null) continue;
            player.getInventory().setItem(i, inventoryCapture[i]);
        }
    }

    public void startSession(WarriorUser user) {
        this.listener = makeListener(user.getBukkitPlayer());
        Bukkit.getPluginManager().registerEvents(listener, Warrior.getInstance());
        giveSessionItems(this.sessionUser.getBukkitPlayer());
    }

    public void endSession(SessionResult result) {
        PlayerInteractEvent.getHandlerList().unregister(listener);
        PlayerQuitEvent.getHandlerList().unregister(listener);
        PlayerRespawnEvent.getHandlerList().unregister(listener);
        PlayerChangedWorldEvent.getHandlerList().unregister(listener);
        PlayerDropItemEvent.getHandlerList().unregister(listener);
        InventoryClickEvent.getHandlerList().unregister(listener);

        if(result.equals(SessionResult.CANCEL)) {
            ArenaBuilder.getSessions().remove(world);

            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_CREATE_CANCEL, new HashMap<>() {
                {
                    put("ARENA", getArenaName());
                }
            });

            sessionUser.getBukkitPlayer().sendMessage(TranslationUtil.getPrefix() + msg);
            sessionUser.getBukkitPlayer().playSound(sessionUser.getBukkitPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 0.5f, 1f);
            restoreInventory();
        } else if(result.equals(SessionResult.CONFIRM)) {
            ArenaBuilder.getSessions().remove(world);

            Region region = new Region(getPos1(), getPos2());
            Arena a = new Arena(getArenaName(), region, getSpawn(), true, ArenaManager.getArenas().size());
            ArenaManager.registerArena(a);
            a.save();

            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_CREATE_SUCCESS, new HashMap<>() {
                {
                    put("ARENA", getArenaName());
                }
            });

            sessionUser.getBukkitPlayer().sendMessage(TranslationUtil.getPrefix() + msg);
            sessionUser.getBukkitPlayer().playSound(sessionUser.getBukkitPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1f);
            restoreInventory();
        }
    }

    private void giveSessionItems(Player player) {
        player.getInventory().clear();

        ItemStack locationWand = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta lwMeta = locationWand.getItemMeta();
        lwMeta.setDisplayName("§8» §3§lLocation Wand §8«");
        lwMeta.setLore(Arrays.asList("§fLeft-Click §7to set Position 1", "§fRight-Click §7to set Position 2"));
        locationWand.setItemMeta(lwMeta);

        player.getInventory().setItem(1, locationWand);

        ItemStack spawnSet = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta ssMeta = spawnSet.getItemMeta();
        ssMeta.setDisplayName("§8» §3§lSpawn Location §8«");
        ssMeta.setLore(Collections.singletonList("§fRight-Click §7to set Spawn"));
        spawnSet.setItemMeta(ssMeta);

        player.getInventory().setItem(4, spawnSet);

        ItemStack confirmOut = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta coMeta = confirmOut.getItemMeta();
        coMeta.setDisplayName("§8» §3§lConfirm §8«");
        coMeta.setLore(Collections.singletonList("§fRight-Click §7to confirm"));
        confirmOut.setItemMeta(coMeta);

        player.getInventory().setItem(7, confirmOut);

        ItemStack cancelOut = new ItemStack(Material.BARRIER, 1);
        ItemMeta cancelMeta = cancelOut.getItemMeta();
        cancelMeta.setDisplayName("§8» §c§lCancel §8«");
        cancelMeta.setLore(Collections.singletonList("§fRight-Click §7to cancel"));
        cancelOut.setItemMeta(cancelMeta);

        player.getInventory().setItem(8, cancelOut);

    }

    protected Listener makeListener(Player player) {
        return new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent e) {
                if(e.getHand() == EquipmentSlot.OFF_HAND) return;
                if(e.getHand() == EquipmentSlot.OFF_HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK) return;
                if(e.getPlayer() != player) return;

                Action action = e.getAction();
                ItemStack item = e.getItem();

                if(item == null) return;

                e.setCancelled(true);

                boolean rightClick = action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);

                if(item.getType() == Material.BLAZE_ROD) {
                    if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                        setPosition(PositionType.LOC1);
                    }

                    if(rightClick) {
                        setPosition(PositionType.LOC2);
                    }

                    player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 0.5f, 1f);
                } else if(item.getType() == Material.NETHER_STAR) {
                    if (rightClick) {
                        setPosition(PositionType.SPAWN);
                    }

                    player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 0.5f, 1f);
                } else if(item.getType() == Material.LIME_DYE) {
                    if(getSpawn() == null || (getPos1() == null || getPos2() == null)) {
                        String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_SETUP_INCOMPLETE);

                        player.sendMessage(TranslationUtil.getPrefix() + msg);
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.5f, 1f);
                        return;
                    }
                    endSession(SessionResult.CONFIRM);
                } else if(item.getType() == Material.BARRIER) {
                    endSession(SessionResult.CANCEL);
                }
            }

            @EventHandler
            public void onDrop(PlayerDropItemEvent e) {
                if(e.getPlayer() != player) return;
                e.setCancelled(true);
            }

            @EventHandler
            public void onInventoryMove(InventoryClickEvent e) {
                if(e.getWhoClicked() != player) return;
                e.setCancelled(true);
            }

            @EventHandler
            public void onQuit(PlayerQuitEvent e) {
                if(e.getPlayer() != player) return;
                endSession(SessionResult.CANCEL);
            }

            @EventHandler
            public void onRespawn(PlayerRespawnEvent e) {
                if(e.getPlayer() != player) return;
                endSession(SessionResult.CANCEL);
            }

            @EventHandler
            public void onWorldChange(PlayerChangedWorldEvent e) {
                if(e.getPlayer() != player) return;
                endSession(SessionResult.CANCEL);
            }

        };
    }
}
