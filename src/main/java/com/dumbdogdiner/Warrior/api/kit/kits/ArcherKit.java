package com.dumbdogdiner.Warrior.api.kit.kits;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.WarriorUser;
import com.dumbdogdiner.Warrior.api.kit.IWarriorKit;
import com.dumbdogdiner.Warrior.api.sesssions.ArenaSession;
import com.dumbdogdiner.Warrior.api.util.ItemBuilder;
import com.dumbdogdiner.Warrior.managers.ArenaManager;
import com.dumbdogdiner.Warrior.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ArcherKit implements IWarriorKit {

    public ArcherKit() {

    }

    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public String[] getDescription() {
        return new String[] {
                "Default Kit"
        };
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getPermission() {
        return "warrior.kit.archer";
    }

    @Override
    public Material getIcon() {
        return Material.BOW;
    }

    @Override
    public void executeSpecial(Player player) {

    }

    @Override
    public void giveKit(Player p) {
        p.getInventory().clear();

        p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));

        ItemStack arrows = new ItemBuilder(Material.ARROW)
                            .setAmount(16)
                            .setName("&8» &3&lARROWS &8«")
                            .build();

        p.getInventory().setItem(1, new ItemStack(Material.STONE_SWORD));
        p.getInventory().setItem(0, new ItemStack(Material.BOW));
        p.getInventory().setItem(7, arrows);

        ItemStack special = new ItemBuilder(Material.MAGMA_CREAM)
                .setName("&8» &3&lSPECIAL ABILITY &8«")
                .setLore("&7Activate your Special Ability")
                .build();

        p.getInventory().setItem(8, special);

        new BukkitRunnable() {

            @Override
            public void run() {
                if(!ArenaManager.isPlaying(p)) {
                    cancel();
                    return;
                }

                WarriorUser user = PlayerManager.get(p.getUniqueId());
                double lastShotDelta = Math.min(System.currentTimeMillis() - ((ArenaSession)user.getSession()).getLastArrow(), 3000);

                if(lastShotDelta < 3000) return;

                ItemStack item = p.getInventory().getItem(7);
                if(item == null || (item.getType() != Material.ARROW && item.getType() != Material.GRAY_DYE)) {
                    cancel();
                    return;
                }
                if(item.getType() == Material.ARROW) {
                    if(item.getAmount() < 16) {
                        item.setAmount(item.getAmount() + 1);
                        p.getInventory().setItem(7, item);
                    }
                } else if(item.getType() == Material.GRAY_DYE) {
                    item.setType(Material.ARROW);
                    p.getInventory().setItem(7, item);
                }
            }
        }.runTaskTimer(Warrior.getInstance(), 0L, 20L);

    }
}
