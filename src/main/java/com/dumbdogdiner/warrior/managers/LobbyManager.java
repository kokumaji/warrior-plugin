package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.api.models.LobbyDataModel;
import com.dumbdogdiner.warrior.api.models.LocationModel;
import com.dumbdogdiner.warrior.api.util.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LobbyManager {

    @Getter
    private static Location lobbySpawn;

    public static void loadData() {
        File lobbyData = new File(JSONUtil.LOBBY_DATA_PATH);
        if(!lobbyData.exists()) {
            lobbySpawn = Bukkit.getWorlds().get(0).getSpawnLocation();
            return;
        }

        try(JsonReader reader = new JsonReader(new FileReader(lobbyData))) {
            LobbyDataModel lobbyDataModel = new Gson().fromJson(reader, LobbyDataModel.class);
            LocationModel spawnJson = lobbyDataModel.getSpawn();

            lobbySpawn = new Location(Bukkit.getWorld(spawnJson.getWorld()), spawnJson.getX(), spawnJson.getY(), spawnJson.getZ(), spawnJson.getYaw(), spawnJson.getPitch());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void updateLocation(Location location) {
        lobbySpawn = location;
        JSONUtil.saveSpawn();
    }

}
