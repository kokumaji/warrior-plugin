package com.dumbdogdiner.Warrior.api.arena;

import com.dumbdogdiner.Warrior.Warrior;

import com.dumbdogdiner.Warrior.api.translation.Translator;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import org.bukkit.Material;
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
                if(e.getPlayer() != player) return;

                EquipmentSlot eq = e.getHand();
                ItemStack is = e.getItem();

                if(eq == null) return;
                if(is == null) return;

                e.setCancelled(true);

                if(e.getHand().equals(EquipmentSlot.OFF_HAND)) return;

                if(is.getType() == Material.BLAZE_ROD) {
                    if(eq == EquipmentSlot.HAND) {
                        if(e.getAction() == Action.RIGHT_CLICK_AIR) {
                            session.setPos1(player.getLocation());
                            player.sendMessage("set pos 1 to " + session.getPos1());
                        }
                        if(e.getAction() == Action.LEFT_CLICK_AIR) {
                            session.setPos2(player.getLocation());
                            player.sendMessage("set pos 2 to " + session.getPos2());
                        }
                    }
                } else if(is.getType() == Material.BLAZE_POWDER) {
                    if(eq == EquipmentSlot.HAND) {
                        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                            session.setSpawn(player.getLocation());
                            player.sendMessage("set spawn to " + player.getLocation());
                        }
                    }
                } else if(is.getType() == Material.LIME_DYE) {
                    unregisterAll();
                    sessions.remove(world);

                    Region region = new Region(session.getPos1(), session.getPos2());
                    ArenaManager.registerArena(new Arena(session.getArenaName(), region, session.getSpawn()));

                    player.sendMessage("session builder complete");

                }
            }

            private void unregisterAll() {
                PlayerInteractEvent.getHandlerList().unregister(this);
            }
        });
    }

}
