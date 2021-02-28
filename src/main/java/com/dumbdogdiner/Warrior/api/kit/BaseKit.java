package com.dumbdogdiner.Warrior.api.kit;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import lombok.Data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        if (hasAbility()) {
            // could add some error handling here??
            Runnable r = ability.run(user);
            new Thread(r).start();
        }
    }

    public BaseKit giveKit(Player p) {
        withAbility(p);
        return this;
    }

    public BaseKit withAbility(Player p) {
        Material m = Material.FIREWORK_STAR;
        String decorator = Ability.DEACTIVATED_ABILITY_STRING;
        if(getAbility().availableOnStart()) {
            m = Material.MAGMA_CREAM;
            decorator = Ability.ACTIVE_ABILITY_STRING;
        }

        ItemStack special = new ItemBuilder(m)
                .setName(decorator)
                .setLore("&7Activate your Special Ability")
                .build();

        p.getInventory().setItem(8, special);

        return this;
    }

}