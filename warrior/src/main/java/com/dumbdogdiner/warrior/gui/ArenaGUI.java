package com.dumbdogdiner.warrior.gui;

import com.dumbdogdiner.stickyapi.bukkit.gui.ClickableSlot;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.Symbols;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.managers.ArenaManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
                int avgRating = (int) a.getMetadata().averageRating();
                List<String> colorDesc = Arrays.stream(a.getMetadata().getDesc())
                        .map(str -> "§7§o" + str)
                        .collect(Collectors.toList());

                ItemStack arenaItem = new ItemBuilder(Material.FILLED_MAP)
                                        .setName("§7Arena §3" + a.getName())
                                        .setLore(" ", "&7Rating &8» &b" + calcRating((int) a.getMetadata().averageRating()), " ")
                                        .appendLore(colorDesc.toArray(String[]::new))
                                        .build();
                ClickableSlot cs = new ClickableSlot(arenaItem, x, y);
                addSlot(cs);
                i++;
            }
        }
    }

    private String calcRating(int rating) {
        String stars = String.valueOf(Symbols.BLACK_STAR).repeat(rating);

        return stars + String.valueOf(Symbols.WHITE_STAR).repeat(5 - rating);
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
            }, user);

            user.sendMessage(TranslationUtil.getPrefix() + msg);
            user.playSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1f);

        }
    }
}