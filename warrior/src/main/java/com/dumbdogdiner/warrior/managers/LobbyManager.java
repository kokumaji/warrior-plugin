package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.managers.WarriorLobbyManager;
import com.dumbdogdiner.warrior.models.LobbyDataModel;
import com.dumbdogdiner.warrior.models.LocationModel;
import com.dumbdogdiner.warrior.api.util.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class LobbyManager implements WarriorLobbyManager {

    @Getter
    private Location lobbySpawn;

    /**
     * Load lobby spawn data from plugin configuration.
     */
    public void loadData() {
        FileConfiguration c = Warrior.getInstance().getConfig();
        if(c.getBoolean("lobby-settings.custom-spawn")) {
            File lobbyData = new File(JSONUtil.LOBBY_DATA_PATH);
            if(!lobbyData.exists()) {
                lobbySpawn = getVanillaSpawn();
                return;
            }

            try(JsonReader reader = new JsonReader(new FileReader(lobbyData))) {
                LobbyDataModel lobbyDataModel = new Gson().fromJson(reader, LobbyDataModel.class);
                LocationModel spawnJson = lobbyDataModel.getSpawn();

                lobbySpawn = new Location(Bukkit.getWorld(spawnJson.getWorld()), spawnJson.getX(), spawnJson.getY(), spawnJson.getZ(), spawnJson.getYaw(), spawnJson.getPitch());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lobbySpawn = getVanillaSpawn();
        }
    }

    private Location getVanillaSpawn() {
        FileConfiguration c = Warrior.getInstance().getConfig();
        String world = c.getString("lobby-settings.lobby-world");
        if(world == null) {
            return Bukkit.getWorlds().get(0).getSpawnLocation();
        } else {
            return Objects.requireNonNull(Bukkit.getWorld(world)).getSpawnLocation();
        }
    }

    public void updateLocation(Location location) {
        lobbySpawn = location;
        JSONUtil.saveSpawn();
    }

}
