package com.dumbdogdiner.warrior.gui;

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.kit.BaseKit;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.sessions.GameState;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.managers.KitManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class KitGUI extends GUI {

    /**
     * Create a GUI with the given number of rows.
     *
     * @param rows   The number of rows in the inventory, must be between 1 and 6
     * @param name   The name of the GUI, displayed as the inventory's name.
     * @param plugin The plugin responsible for this GUI
     */
    public KitGUI(int rows, @NotNull String name, @NotNull Plugin plugin) {
        super(rows, name, plugin);
    }

    @Override
    protected void onInventoryOpen(@NotNull InventoryOpenEvent event) {
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ").build();

        ItemStack blank = new ItemBuilder(Material.GRAY_DYE)
                .setName("§7Kit §3???").build();

        for(int i = 0; i < 9; i++) {
            addSlot(i, 0, placeholder);
            addSlot(i, 4, placeholder);
        }

        ClickableSlot exit = new ClickableSlot(Material.BARRIER, 1, "§c§lClose Menu", 4, 4);
        addSlot(exit);

        int kitCount = KitManager.getKits().size();
        int i = 0;

        for (int y = 1; y < 4; y++) {
            for (int x = 1; x < 8; x++) {
                if (i >= kitCount) {
                    addSlot(x, y, blank);
                    continue;
                }
                BaseKit k = KitManager.getKits().get(i);
                ItemStack kitItem = new ItemBuilder(k.getIcon())
                        .setName("§7Kit §3" + k.getName())
                        .setLore(k.getDescription())
                        .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .build();

                ClickableSlot cs = new ClickableSlot(kitItem, x, y);
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
        } else if(name.contains("Kit")) {
            String kitName = name.replace("§7Kit §3", "");
            User user = PlayerManager.get(event.getWhoClicked().getUniqueId());

            if(user == null) return;
            BaseKit kit = KitManager.get(kitName);
            if(kit == null) return;

            kit.giveKit(user.getBukkitPlayer());
            ((ArenaSession) user.getSession()).setState(GameState.IN_GAME);
            ((ArenaSession) user.getSession()).setKit(kit);

            String msg = Warrior.getTranslator().translate(Constants.Lang.KIT_EQUIP, new HashMap<>() {
                {
                    put("KIT", kit.getName());
                }
            }, user);

            user.sendMessage(TranslationUtil.getPrefix() + msg);
            user.playSound(Sound.ITEM_ARMOR_EQUIP_CHAIN, 0.5f, 1f);

            user.getBukkitPlayer().closeInventory();

        }
    }
}
