package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.arena.Arena;
import com.dumbdogdiner.warrior.api.kit.kits.CustomKit;
import com.dumbdogdiner.warrior.api.models.ArenaModel;
import com.dumbdogdiner.warrior.api.models.CustomKitModel;
import com.dumbdogdiner.warrior.api.models.LobbyDataModel;
import com.dumbdogdiner.warrior.api.models.LocationModel;
import com.dumbdogdiner.warrior.managers.LobbyManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtil {

    private static final String[] subFolderNames = {"/arenas", "/holograms", "/npcData", "/kits"};
    private static final String DATA_FOLDER_PATH = Warrior.getInstance().getDataFolder().getPath() + "/data";

    public static final String LOBBY_DATA_PATH = DATA_FOLDER_PATH + "/lobby.json";
    public static final String KIT_DATA_PATH = DATA_FOLDER_PATH + subFolderNames[3];
    public static final String ARENA_DATA_PATH = DATA_FOLDER_PATH + subFolderNames[0];

    public static boolean fileExists(DataType type, String fileName) {
        File mainFolder = new File(DATA_FOLDER_PATH);

        if(!mainFolder.exists()) {
            if(mainFolder.mkdir()) {
                for(String s : subFolderNames) {
                    File folder = new File(DATA_FOLDER_PATH + s);
                    if(!folder.exists()) folder.mkdir();
                }
            }
        }

        File f;
        fileName = fileName.endsWith(".json") ? fileName : fileName + ".json";
        switch (type) {
            case ARENA:
                f = new File(DATA_FOLDER_PATH + subFolderNames[0] + fileName);
                break;
            case HOLOGRAM:
                f = new File(DATA_FOLDER_PATH + subFolderNames[1] + fileName);
                break;
            case ENTITY:
                f = new File(DATA_FOLDER_PATH + subFolderNames[2] + fileName);
                break;
            case LOBBY:
                f = new File(LOBBY_DATA_PATH);
                break;
            case KIT:
                f = new File(DATA_FOLDER_PATH + subFolderNames[3] + fileName);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return f.exists();
    }

    public static void saveKit(CustomKit kit) {
        String filePath = DATA_FOLDER_PATH + subFolderNames[3] + "/" + kit.getName() + ".json";
        CustomKitModel kitModel = new CustomKitModel(kit);

        // idk.. will have to adjust a few functions here ig
        // for now this is a workaround to create folders when
        // setting the lobby..
        fileExists(DataType.KIT, kit.getName() + ".json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(kitModel, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveArena(Arena arena) {
        String filePath = DATA_FOLDER_PATH + subFolderNames[0] + "/" + arena.getName() + ".json";
        ArenaModel arenaJson = new ArenaModel(arena);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(arenaJson, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSpawn() {
        LobbyDataModel ldm = new LobbyDataModel(new LocationModel(LobbyManager.getLobbySpawn()));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // idk.. will have to adjust a few functions here ig
        // for now this is a workaround to create folders when
        // setting the lobby..
        fileExists(DataType.LOBBY, "lobby.json");

        try(FileWriter writer = new FileWriter(LOBBY_DATA_PATH)) {
            gson.toJson(ldm, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
