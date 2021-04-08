package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.WithWarriorPlugin;
import com.dumbdogdiner.warrior.api.managers.WarriorPlayerManager;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerManager implements WithWarriorPlugin, WarriorPlayerManager {

    private final TreeMap<UUID, WarriorUser> users = new TreeMap<>();

    public boolean contains(UUID uuid) {
        return users.containsKey(uuid);
    }

    public WarriorUser addUser(@NotNull UUID userId) {
        Player p = Preconditions.checkNotNull(Bukkit.getPlayer(userId), "Player cannot be null!");

        if(!users.containsKey(userId)) {
            WarriorUser user = new WarriorUser(userId);
            Warrior.getConnection().insertUser(user);
            users.put(userId, user);

            return user;
        } else {
            String msg = String.format("Attempted to register WarriorUser %s{%2s} twice!", p.getName(), p.getUniqueId());
            Warrior.getPluginLogger().warn(msg);
            return get(userId);
        }
    }

    public void remove(@NotNull WarriorUser user) {
        remove(user.getUserId());
    }

    public void remove(@NotNull UUID userId) {
        if(users.containsKey(userId)) {
            WarriorUser user = users.get(userId);
            user.saveData();

            users.remove(userId);
        } else {
            String msg = String.format("Failed to remove WarriorUser %s from cache.", userId);
            Warrior.getPluginLogger().warn(msg);
        }
    }

    public WarriorUser get(UUID userId) {
        return Preconditions.checkNotNull(users.get(userId), "WarriorUser is null!");
    }

    public List<WarriorUser> getList() {
        return new ArrayList<>(users.values());
    }

    public List<WarriorUser> getListOf(Predicate<WarriorUser> predicate) {
        return users.values().stream().filter(predicate).collect(Collectors.toList());
    }
}
