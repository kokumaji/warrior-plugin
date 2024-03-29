package com.dumbdogdiner.warrior.api.kit.kits;

import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.kit.Ability;
import com.dumbdogdiner.warrior.api.kit.BaseKit;
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WarriorKit extends BaseKit {

    public WarriorKit(String name, int cost, String permission, Material icon) {
        super(name, cost, permission, icon, SpecialAbilities.MEDIC, desc);
    }

    @Override
    public BaseKit giveKit(Player p) {
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
        giveHealItems(p, 7);

        withAbility(p);
        return this;
    }
}
