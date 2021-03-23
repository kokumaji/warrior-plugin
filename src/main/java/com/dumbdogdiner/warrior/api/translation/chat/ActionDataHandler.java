package com.dumbdogdiner.warrior.api.translation.chat;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ActionDataHandler implements Listener {

    private static HashMap<String, ChatMessage.ActionDataPacket> actionMap = new HashMap<>();

    private static Plugin owner;

    public ActionDataHandler(Plugin plugin) {
        owner = plugin;
        Bukkit.getPluginManager().registerEvents(this, owner);
        ActionDataFilter filter = new ActionDataFilter();
        filter.registerFilter();
    }

    public static void add(String id, ChatMessage.ActionDataPacket action) throws IllegalStateException {
        if(owner == null) {
            throw new IllegalStateException("ActionDataHandler has not been initialized!");
        }
        if(!actionMap.containsKey(id)) {
            actionMap.put(id, action);
            Bukkit.getScheduler().runTaskLater(owner, () -> actionMap.remove(id), action.getExpiry());
        }
    }

    public static HashMap<String, ChatMessage.ActionDataPacket> getActions() {
        return actionMap;
    }

    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().split(" ")[0];

        if(owner == null) return;
        if(cmd.startsWith("/" + owner.getName() + "-ADP-")) {
            String cmdString = cmd.substring(1);
            e.setCancelled(true);
            if(actionMap.containsKey(cmdString)) {
                ChatMessage.ActionDataPacket packet = actionMap.get(cmdString);
                packet.getExecuteAction().run(e.getPlayer());

                actionMap.remove(cmdString);
            }
        }
    }
}