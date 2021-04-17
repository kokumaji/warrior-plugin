package com.dumbdogdiner.warrior.api.kit.kits;

import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.kit.Ability;
import com.dumbdogdiner.warrior.api.kit.BaseKit;
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities;
import com.dumbdogdiner.warrior.api.util.json.JSONHelper;
import com.dumbdogdiner.warrior.api.util.json.JsonModel;
import com.dumbdogdiner.warrior.api.util.json.JsonSerializable;
import com.dumbdogdiner.warrior.api.util.json.models.CustomKitModel;
import com.dumbdogdiner.warrior.api.util.json.models.ItemStackModel;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomKit extends BaseKit implements JsonSerializable {

    @Getter
    private final ItemStackModel[] items;

    public CustomKit(CustomKitModel model) {
        super(model.getKitName(), model.getCost(), model.getPermission(), model.getIcon(), SpecialAbilities.fromString(model.getAbility()), model.getDescription());

        this.items = model.getInventory();
    }

    public CustomKit(String name, int cost, String perm, Material icon, Ability ability, String[] desc, ItemStack... items) {
        super(name, cost, perm, icon, ability, desc);

        List<ItemStackModel> tmp = new ArrayList<>();
        for(int i = 0; i < items.length; i++) {
            if(items[i] == null) continue;
            tmp.add(new ItemStackModel(items[i], i));
        }

        this.items = tmp.toArray(ItemStackModel[]::new);
    }

    @Override
    public BaseKit giveKit(Player p) {
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

        withAbility(p);
        return this;

    }

    public void saveKit() {
        JSONHelper.save(this);
    }

    @Override
    public JsonModel toJson() {
        return new CustomKitModel(this);
    }

    @Override
    public String getFilePath() {
        return "kit/" + getName() + ".json";
    }
}
