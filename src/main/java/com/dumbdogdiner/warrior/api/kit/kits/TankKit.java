package com.dumbdogdiner.warrior.api.kit.kits;

import com.dumbdogdiner.warrior.api.builders.ItemBuilder;
import com.dumbdogdiner.warrior.api.kit.Ability;
import com.dumbdogdiner.warrior.api.kit.BaseKit;
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TankKit extends BaseKit {

    private static final String[] desc = {
            "&8Default Kit",
            " ",
            "&7&oBattle your way through the battle field",
            "&7&owith this trusty old sword. Perfect for",
            "&7&oa newcomer like you!",
            " ",
            "&7Main Weapon: &fIron Sword"
    };

    public TankKit(String name, int cost, String permission, Material icon) {
        super(name, cost, permission, icon, SpecialAbilities.MEDIC, desc);
    }

    @Override
    public BaseKit giveKit(Player p) {
        p.getInventory().clear();

        p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

        p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));

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

        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 3));
        giveHealItems(p, 7);
        withAbility(p);

        return this;
    }
}
