package com.dumbdogdiner.Warrior.listeners;


import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.events.ItemAction;
import com.dumbdogdiner.Warrior.api.events.WarriorItemUseEvent;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.api.util.Packet;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import com.dumbdogdiner.stickyapi.bukkit.nms.BukkitHandler;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class PlayerListener implements Listener {

    String[] actionPackets = {
            "PacketPlayInBlockPlace",
            "PacketPlayInBlockDig",
            "PacketPlayInArmAnimation"
    };

    private static final Material[] BLOCKED_TOOLS = {
            Material.MAP,
            Material.LEATHER_CHESTPLATE,
    };



    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!PlayerManager.contains(p.getUniqueId())) {
            UUID uuid = p.getUniqueId();
            PlayerManager.addUser(uuid);
            PlayerManager.get(uuid).setSession(new LobbySession(uuid));
            p.setExp(0); // reset xp on join, will be replaced with Warrior™ level
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }

            p.setHealth(20D);

            // Our Action Handler workaround...

            BukkitHandler.injectPlayer((ctx, packet) -> {
                String packetName = packet.getClass().getSimpleName();
                if (Arrays.asList(actionPackets).contains(packetName)) {
                    try {
                        WarriorItemUseEvent event = null;
                        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();

                        switch (packetName) {
                            case "PacketPlayInBlockPlace":
                                if (Arrays.asList(BLOCKED_TOOLS).contains(item.getType())) break;
                                event = new WarriorItemUseEvent(p, ItemAction.RIGHT_CLICK, item);
                                break;
                            case "PacketPlayInBlockDig":
                                event = new WarriorItemUseEvent(p, ItemAction.LEFT_CLICK, item);
                                break;
                            case "PacketPlayInArmAnimation":
                                Field f = packet.getClass().getDeclaredField("a");
                                f.setAccessible(true);

                                String hand = f.get(packet).toString();

                                if (p.getTargetBlockExact(5) != null) break;
                                if (hand.equals("OFF_HAND")) break;
                                event = new WarriorItemUseEvent(p, ItemAction.LEFT_CLICK, item);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + packetName);
                        }

                        if (event != null) {
                            WarriorItemUseEvent finalEvent = event;
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    Bukkit.getPluginManager().callEvent(finalEvent);
                                }

                            }.runTask(Warrior.getInstance());

                        }
                    } catch (NoSuchFieldException | IllegalAccessException err) {
                        err.printStackTrace();
                    }

                }
            }, p);

        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        if(PlayerManager.contains(e.getPlayer().getUniqueId())) {
            PlayerManager.remove(e.getPlayer().getUniqueId());
            BukkitHandler.removeInjection(e.getPlayer());
        }
    }
}
