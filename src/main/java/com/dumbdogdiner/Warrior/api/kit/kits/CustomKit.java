package com.dumbdogdiner.Warrior.api.kit.kits;

import com.dumbdogdiner.Warrior.api.kit.IWarriorKit;
import com.dumbdogdiner.Warrior.api.models.CustomKitModel;
import com.dumbdogdiner.Warrior.api.models.ItemStackModel;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.Getter;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public class CustomKit implements IWarriorKit {

    private int cost;
    private String perm;
    private String name;

    private String[] desc;
    private Material icon;

    @Getter
    private ItemStackModel[] items;

    public CustomKit(File f) {
        if(f.canRead()) {
            try(JsonReader reader = new JsonReader(new FileReader(f))) {
                CustomKitModel model = new Gson().fromJson(reader, CustomKitModel.class);
                this.name = model.getKitName();
                this.cost = model.getCost();
                this.perm = model.getPermission();
                this.desc = model.getDescription();
                this.icon = model.getIcon();
                this.items = model.getInventory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public CustomKit(String name, int cost, String perm, Material icon, String[] desc, ItemStack... items) {
        this.name = name;
        this.cost = cost;
        this.perm = perm;
        this.icon = icon;

        this.desc = desc;

        List<ItemStackModel> tmp = new ArrayList<>();
        for(int i = 0; i < items.length; i++) {
            if(items[i] == null) continue;
            tmp.add(new ItemStackModel(items[i], i));
        }

        this.items = tmp.toArray(ItemStackModel[]::new);
    }

    public void save() {
        JSONUtil.saveKit(this);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String[] getDescription() {
        return this.desc;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public String getPermission() {
        return this.perm;
    }

    @Override
    public Material getIcon() {
        return this.icon;
    }

    @Override
    public void executeSpecial(Player player) {

    }

    @Override
    public void giveKit(Player p) {
        p.getInventory().clear();
        HashMap<Integer, ItemStack> inv = new HashMap<>();

        for(ItemStackModel model : this.items) {
            Material mat = Material.valueOf(model.getType().toUpperCase());
            int slot = model.getSlot();
            int amount = model.getAmount();
            String name = model.getName();

            ItemStack item = new ItemBuilder(mat)
                    .setAmount(amount)
                    .setName(name)
                    .build();

            inv.put(slot, item);
        }

        for(int i : inv.keySet()) {
            p.getInventory().setItem(i, inv.get(i));
        }
    }
}
