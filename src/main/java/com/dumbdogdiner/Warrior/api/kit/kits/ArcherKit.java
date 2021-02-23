package com.dumbdogdiner.Warrior.api.kit.kits;

import com.dumbdogdiner.Warrior.api.kit.IWarriorKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ArcherKit implements IWarriorKit {
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
        return null;
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

    }
}
