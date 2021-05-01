package com.dumbdogdiner.warrior.kits;

import com.dumbdogdiner.warrior.api.kit.Kit;
import com.dumbdogdiner.warrior.api.kit.WithAbility;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WarriorKit implements Kit, WithAbility {

    @Override
    public String getName() {
        return "Warrior";
    }

    @Override
    public String[] getDescription() {
        return new String[] {
            "&8Default Kit",
            " ",
            "&7&oBattle your way through the battle field",
            "&7&owith this trusty old sword. Perfect for",
            "&7&oa newcomer like you!",
            " ",
            "&7Main Weapon: &fIron Sword"
        };
    }

    @Override
    public void setupInventory(WarriorUser<?> user) {
        Player p = user.getBukkitPlayer();
        p.getInventory().clear();

        p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));

        p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));

        // TODO: How to handle Special Items???

        /**
         *         Material m = Material.FIREWORK_STAR;
         *         String decorator = Ability.DEACTIVATED_ABILITY_STRING;
         *         if(getAbility().availableOnStart()) {
         *             m = Material.MAGMA_CREAM;
         *             decorator = Ability.ACTIVE_ABILITY_STRING;
         *         }
         *
         *         ItemStack special = new ItemBuilder(m)
         *                             .setName(decorator)
         *                             .setLore("&7Activate your Special Ability")
         *                             .build();
         *
         *         p.getInventory().setItem(8, special);
         *         giveHealItems(p, 7);
         */
    }

    @Override
    public void execute(WarriorUser<?> user) {
        user.executeAsync((u) -> {
            Player player = u.getBukkitPlayer();

            for(PotionEffect potion : player.getActivePotionEffects()) {
                player.removePotionEffect(potion.getType());
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));

        });
    }

    @Override
    public int getCooldown() {
        return 300;
    }

    @Override
    public boolean abilityReady(WarriorUser<?> user) {
        return !user.getAbilityActive();
    }
}
