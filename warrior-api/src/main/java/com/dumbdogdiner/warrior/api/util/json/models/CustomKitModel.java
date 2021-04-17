package com.dumbdogdiner.warrior.api.util.json.models;


import com.dumbdogdiner.warrior.api.kit.kits.CustomKit;
import com.dumbdogdiner.warrior.api.util.json.JsonModel;
import lombok.Data;
import org.bukkit.Material;

@Data
public class CustomKitModel implements JsonModel {

    private String kitName;

    private String[] description;

    private int cost;

    private String permission;

    private Material icon;

    private ItemStackModel[] inventory;

    private String ability;

    public CustomKitModel(CustomKit kit) {
        this.kitName = kit.getName();
        this.description = kit.getDescription();
        this.cost = kit.getCost();
        this.permission = kit.getPermission();
        this.icon = kit.getIcon();
        this.inventory = kit.getItems();
        this.ability = kit.getAbility().getName();
    }

}
