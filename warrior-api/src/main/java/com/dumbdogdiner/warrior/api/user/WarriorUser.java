package com.dumbdogdiner.warrior.api.user;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface WarriorUser<T extends WarriorUser> {

    UUID getUniqueId();

    Player toBukkit();

}
