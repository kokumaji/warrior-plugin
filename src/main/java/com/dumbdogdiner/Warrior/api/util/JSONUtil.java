package com.dumbdogdiner.Warrior.api.util;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.arena.Arena;
import com.dumbdogdiner.Warrior.api.models.ArenaModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtil {

    private static final String DATA_FOLDER_PATH = Warrior.getInstance().getDataFolder().getPath() + "/data";
    private static final String[] subFolderNames = {"/arenas", "/holograms", "/npcData"};

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
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return f.exists();
    }

    public static void saveArena(Arena arena) {
        String filePath = DATA_FOLDER_PATH + subFolderNames[0] + "/" + arena.getName() + ".json";
        ArenaModel arenaJson = new ArenaModel(arena);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer =  new FileWriter(filePath)) {
            gson.toJson(arenaJson, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
