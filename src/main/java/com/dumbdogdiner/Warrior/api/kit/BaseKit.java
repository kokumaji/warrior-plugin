package com.dumbdogdiner.Warrior.api.kit;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import lombok.Data;

import org.bukkit.Material;
import org.bukkit.entity.Player;

@Data
public abstract class BaseKit {

    private String name;
    private int cost;
    private String permission;
    private Material icon;
    private Ability ability;
    private String[] description;

    public BaseKit(String name, int cost, String permission, Material icon, Ability ability, String... description) {
        this.name = name;
        this.cost = cost;
        this.permission = permission;
        this.icon = icon;
        this.ability = ability;
        this.description = description;
    }

    public BaseKit(String name, int cost, String permission, String... description) {
        this.name = name;
        this.cost = cost;
        this.permission = permission;
        this.icon = Material.PAPER;
        this.ability = null;
        this.description = description;
    }

    public boolean hasAbility() {
        return ability != null;
    }

    public void activateAbility(WarriorUser user) {
        if (hasAbility()) ability.run(user).run();
    }

    public abstract void giveKit(Player p);

}