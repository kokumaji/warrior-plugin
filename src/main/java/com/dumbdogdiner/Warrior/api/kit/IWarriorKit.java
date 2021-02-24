package com.dumbdogdiner.Warrior.api.kit;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface IWarriorKit {

    String getName();

    String[] getDescription();

    int getCost();

    String getPermission();

    Material getIcon();

    void activateAbility(WarriorUser user);

    void giveKit(Player p);

    Ability getAbility();
}
