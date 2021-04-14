package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.WithWarriorPlugin;
import com.dumbdogdiner.warrior.api.managers.WarriorPlayerManager;
import com.dumbdogdiner.warrior.user.User;
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

    private final TreeMap<UUID, User> users = new TreeMap<>();

    public boolean contains(UUID uuid) {
        return users.containsKey(uuid);
    }

    public User addUser(@NotNull UUID userId) {
        Player p = Preconditions.checkNotNull(Bukkit.getPlayer(userId), "Player cannot be null!");

        if(!users.containsKey(userId)) {
            User user = new User(userId);
            Warrior.getConnection().insertUser(user);
            users.put(userId, user);

            return user;
        } else {
            String msg = String.format("Attempted to register WarriorUser %s{%2s} twice!", p.getName(), p.getUniqueId());
            Warrior.getPluginLogger().warn(msg);
            return get(userId);
        }
    }

    public void remove(@NotNull User user) {
        remove(user.getUserId());
    }

    public void remove(@NotNull UUID userId) {
        if(users.containsKey(userId)) {
            User user = users.get(userId);
            user.saveData();

            users.remove(userId);
        } else {
            String msg = String.format("Failed to remove WarriorUser %s from cache.", userId);
            Warrior.getPluginLogger().warn(msg);
        }
    }

    public User get(UUID userId) {
        return Preconditions.checkNotNull(users.get(userId), "WarriorUser is null!");
    }

    public List<User> getList() {
        return new ArrayList<>(users.values());
    }

    public List<User> getListOf(Predicate<User> predicate) {
        return users.values().stream().filter(predicate).collect(Collectors.toList());
    }
}
