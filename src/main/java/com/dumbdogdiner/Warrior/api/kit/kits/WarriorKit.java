package com.dumbdogdiner.Warrior.api.kit.kits;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.kit.IWarriorKit;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class WarriorKit implements IWarriorKit {

    public WarriorKit() {

    }

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
    public int getCost() {
        return 0;
    }

    @Override
    public String getPermission() {
        return "warrior.kit.default";
    }

    @Override
    public Material getIcon() {
        return Material.LEATHER_HELMET;
    }

    @Override
    public void executeSpecial(Player player) {
        new BukkitRunnable() {

            @Override
            public void run() {
                ItemStack deactivated = new ItemBuilder(Material.FIREWORK_STAR)
                                        .setName("&8» &7&lSPECIAL ABILITY &8«")
                                        .setLore("&7Activate your Special Ability")
                                        .build();
                player.getInventory().setItem(8, deactivated);
                player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.5f, 1f);
                player.spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 50, 1, 1, 1);
            }
        }.runTask(Warrior.getInstance());
    }

    @Override
    public void giveKit(Player p) {
        p.getInventory().clear();

        p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));

        p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
        ItemStack special = new ItemBuilder(Material.MAGMA_CREAM)
                            .setName("&8» &3&lSPECIAL ABILITY &8«")
                            .setLore("&7Activate your Special Ability")
                            .build();

        p.getInventory().setItem(8, special);
    }
}
