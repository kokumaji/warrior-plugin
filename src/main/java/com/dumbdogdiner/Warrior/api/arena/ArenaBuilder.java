package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.Warrior;

import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.api.translation.Translator;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ArenaBuilder {

    private static HashMap<World, ArenaSession> sessions = new HashMap<>();

    private static Translator TR = Warrior.getTranslator();

    public static void registerSession(World world, ArenaSession session) {
        Player player = session.getSessionUser().getBukkitPlayer();
        if(sessions.containsKey(world)) {
            player.sendMessage(TR.translate("arena-setup.setup-in-progress", true));
            return;
        }

        sessions.put(world, session);

        session.startSession(new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent e) {
                if(e.getHand() == EquipmentSlot.OFF_HAND) return;
                if(e.getHand() == EquipmentSlot.OFF_HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK) return;
                if(e.getPlayer() != player) return;

                Player pl = e.getPlayer();
                Action action = e.getAction();
                ItemStack item = e.getItem();

                if(item == null) return;

                e.setCancelled(true);

                boolean rightClick = action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);

                if(item.getType() == Material.BLAZE_ROD) {
                    if(rightClick) {
                        session.setPos1(player.getLocation());
                        String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_BUILDER_LOCATION, new HashMap<String, String>() {
                            {
                                put("LOCATION", TranslationUtil.readableLocation(player.getLocation(), true, false));
                                put("TYPE", "Position 1");
                            }
                        });
                        player.sendMessage(TranslationUtil.getPrefix() + msg);
                    }
                    if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                        session.setPos2(player.getLocation());
                        String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_BUILDER_LOCATION, new HashMap<String, String>() {
                            {
                                put("LOCATION", TranslationUtil.readableLocation(player.getLocation(), true, false));
                                put("TYPE", "Position 2");
                            }
                        });
                        player.sendMessage(TranslationUtil.getPrefix() + msg);
                    }
                } else if(item.getType() == Material.NETHER_STAR) {
                    if (rightClick) {
                        session.setSpawn(player.getLocation());
                        String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_BUILDER_LOCATION, new HashMap<String, String>() {
                            {
                                put("LOCATION", TranslationUtil.readableLocation(player.getLocation(), true, false));
                                put("TYPE", "Spawn");
                            }
                        });
                        player.sendMessage(TranslationUtil.getPrefix() + msg);
                    }
                } else if(item.getType() == Material.LIME_DYE) {
                    unregisterAll();
                    sessions.remove(world);

                    Region region = new Region(session.getPos1(), session.getPos2());
                    ArenaManager.registerArena(new Arena(session.getArenaName(), region, session.getSpawn()));

                    String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_CREATE_SUCCESS, new HashMap<String, String>() {
                        {
                            put("ARENA", session.getArenaName());
                        }
                    });

                    player.sendMessage(TranslationUtil.getPrefix() + msg);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    session.restoreInventory();

                }
            }

            private void unregisterAll() {
                PlayerInteractEvent.getHandlerList().unregister(this);
            }
        });
    }

}
