package com.dumbdogdiner.warrior.api.user;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface WarriorUser<T extends WarriorUser> {

    UUID getUniqueId();

    Player toBukkit();

    void setKit(@NotNull String name);
    String getKit();

}
