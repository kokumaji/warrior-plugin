package com.dumbdogdiner.Warrior.api.kit.kits;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.kit.Ability;
import com.dumbdogdiner.Warrior.api.kit.BaseKit;
import com.dumbdogdiner.Warrior.api.kit.SpecialAbilities;

import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WarriorKit extends BaseKit {


    private static final String[] desc = {
            "&8Default Kit",
            " ",
            "&7&oBattle your way through the battle field",
            "&7&owith this trusty old sword. Perfect for",
            "&7&oa newcomer like you!",
            " ",
            "&7Main Weapon: &fIron Sword"
    };

    public WarriorKit(String name, int cost, String permission, Material icon) {
        super(name, cost, permission, icon, SpecialAbilities.PACEMAKER, desc);
    }

    @Override
    public void giveKit(Player p) {
        p.getInventory().clear();

        p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));

        p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));

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
    }
}
