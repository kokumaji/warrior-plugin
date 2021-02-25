package com.dumbdogdiner.Warrior.api.kit.kits;

import com.dumbdogdiner.Warrior.api.kit.BaseKit;
import com.dumbdogdiner.Warrior.api.models.CustomKitModel;
import com.dumbdogdiner.Warrior.api.models.ItemStackModel;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;

import lombok.Getter;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public class CustomKit extends BaseKit {

    @Getter
    private ItemStackModel[] items;

    public CustomKit(CustomKitModel model) {
        super(model.getKitName(), model.getCost(), model.getPermission(), model.getIcon(), null, model.getDescription());

        this.items = model.getInventory();
    }

    public CustomKit(String name, int cost, String perm, Material icon, String[] desc, ItemStack... items) {
        super(name, cost, perm, icon, null, desc);

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
    public void giveKit(Player p) {
        p.getInventory().clear();
        HashMap<Integer, ItemStack> invMap = new HashMap<Integer, ItemStack>();

        for(ItemStackModel model : this.items) {
            Material mat = Material.valueOf(model.getType().toUpperCase());
            int slot = model.getSlot();
            int amount = model.getAmount();
            String name = model.getName();

            ItemStack item = new ItemBuilder(mat)
                    .setAmount(amount)
                    .setName(name)
                    .build();

            invMap.put(slot, item);
        }

        for(int i : invMap.keySet()) {
            p.getInventory().setItem(i, invMap.get(i));
        }
    }

}
