package com.dumbdogdiner.Warrior.gui;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ArenaGUI extends GUI {

    /**
     * Create a GUI with the given number of rows.
     *
     * @param rows   The number of rows in the inventory, must be between 1 and 6
     * @param name   The name of the GUI, displayed as the inventory's name.
     * @param plugin The plugin responsible for this GUI
     */
    public ArenaGUI(int rows, @NotNull String name, @NotNull Plugin plugin) {
        super(rows, name, plugin);
    }

    @Override
    protected void onInventoryOpen(@NotNull InventoryOpenEvent event) {
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                                    .setName(" ").build();

        ItemStack blank = new ItemBuilder(Material.GRAY_DYE)
                .setName("§7Arena §3???").build();

        for(int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 4, placeholder);
        }

        ClickableSlot exit = new ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4);
        addSlot(exit);

        int arenaCount = ArenaManager.getArenas().size();
        int i = 0;

        for (int y = 1; y < 4; y++) {
            for (int x = 1; x < 8; x++) {
                if (i >= arenaCount) {
                    addSlot(x, y, blank);
                    continue;
                }
                Arena a = ArenaManager.getArenas().get(i);
                ClickableSlot cs = new ClickableSlot(Material.FILLED_MAP, 1, "§7Arena §3" + a.getName(), x, y);
                addSlot(cs);
                i++;
            }
        }
    }

    @Override
    protected void onInventoryClick(@NotNull InventoryClickEvent event, @Nullable String tag) {
        ItemStack item = event.getCurrentItem();
        if(item == null) return;
        String name = item.getItemMeta().getDisplayName();

        if(name.contains("Close Menu")) {
            event.getWhoClicked().closeInventory();
        } else if(name.contains("Arena")) {
            String arenaName = name.replace("§7Arena §3", "");
            WarriorUser user = PlayerManager.get(event.getWhoClicked().getUniqueId());

            if(user == null) return;
            Arena a = ArenaManager.get(arenaName);
            if(a == null) return;

            user.setSession(new ArenaSession(user.getUserId(), a));
            String msg = Warrior.getTranslator().translate(Constants.Lang.ARENA_TELEPORT, new HashMap<>() {
                {
                    put("ARENA", a.getName());
                }
            });

            user.sendMessage(TranslationUtil.getPrefix() + msg);
            user.playSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1f);

        }
    }
}
