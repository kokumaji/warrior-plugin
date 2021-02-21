package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.api.WarriorUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerManager {

    private static final HashMap<UUID, WarriorUser> users = new HashMap<>();

    public static boolean contains(UUID uuid) {
        return users.containsKey(uuid);
    }

    public static void addUser(WarriorUser user) {
        if(!users.containsKey(user.getUserId()))
            users.put(user.getUserId(), user);
        else throw new IllegalStateException("User " + user.getUserId() + " is already registered!");
    }

    public static void addUser(UUID userId) {
        Player p = Bukkit.getPlayer(userId);
        if(p == null) return;

        if(!users.containsKey(userId))
            users.put(userId, new WarriorUser(userId));
        else throw new IllegalStateException("User " + userId + " is already registered!");
    }

    public static void remove(WarriorUser user) {
        users.remove(user.getUserId());
    }

    public static void remove(UUID userId) {
        users.remove(userId);
    }

    public static WarriorUser get(UUID userId) {
        if(users.containsKey(userId))
            return users.get(userId);

        return null;
    }

    public static List<WarriorUser> getList() {
        return new ArrayList<>(users.values());
    }

    public static List<WarriorUser> getListOf(Predicate<WarriorUser> predicate) {
        return users.values().stream().filter(predicate).collect(Collectors.toList());
    }

}
