package com.dumbdogdiner.Warrior.api.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface IWarriorKit {

    String getName();

    String[] getDescription();

    int getCost();

    String getPermission();

    Material getIcon();

    void executeSpecial(Player player);

    void giveKit(Player p);
}
