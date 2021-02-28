package com.dumbdogdiner.Warrior.api.sesssions;

import org.bukkit.entity.Player;

public interface Session {

    SessionType getType();

    long getTimestamp();

    void setupInventory(Player player);

}
