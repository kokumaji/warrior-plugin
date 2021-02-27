package com.dumbdogdiner.Warrior.listeners;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.events.WarriorItemUseEvent;
import com.dumbdogdiner.Warrior.api.sesssions.LobbySession;
import com.dumbdogdiner.Warrior.gui.ArenaGUI;
import com.dumbdogdiner.Warrior.gui.DeathSoundGUI;
import com.dumbdogdiner.Warrior.managers.GUIManager;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemClickListener implements Listener {

    @EventHandler
    public void onWarriorClick(WarriorItemUseEvent e) {
        WarriorUser user = PlayerManager.get(e.getPlayer().getUniqueId());
        if(user.getSession() instanceof LobbySession) {
            ItemMeta meta = e.getItem().getItemMeta();

            // TODO: Check for Tool + Lobby Item
            // disgusting hotfix for now.. gonna replace this with an
            // actual item comparison
            if(!meta.getDisplayName().startsWith("§")) return;

            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");

            if(meta.getDisplayName().equals("§8» §3§lARENAS §8«")) {
                ArenaGUI gui = GUIManager.get(ArenaGUI.class);
                gui.open(e.getPlayer());
            } else if(meta.getDisplayName().equals("§8» §3§lSHOP §8«")) {
                DeathSoundGUI gui = GUIManager.get(DeathSoundGUI.class);
                gui.open(e.getPlayer());
            } else {
                user.getBukkitPlayer().sendActionBar("§4§lFeature Not Implemented!");
                user.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 0.75f, 0.5f);
            }
        }
    }
}
