package me.kokumaji.warrior.api.util.json;

import me.kokumaji.warrior.api.WarriorAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class JSONHelper {

    public static final String JSON_DATA_PATH = "{PLUGIN_PATH}/data/";

    /**
     * Returns whether the given file exists in Warrior's data folder
     * @param filePath File path relative to the plugin data folder.
     * @return `true` if the given file exists, otherwise `false`
     */
    public static boolean fileExists(String filePath) {
        return fileExists(filePath, WarriorAPI.getService().getInstance());
    }

    /**
     * Returns whether the given file exists.
     *
     * @param filePath File path relative to the plugin data folder.
     * @param plugin The Bukkit plugin that should be checked.
     * @return `true` if the given file exists, otherwise `false`
     */
    public static boolean fileExists(String filePath, Plugin plugin) {
        String pluginPath = plugin.getDataFolder().getAbsolutePath();
        File dataFolder = new File(getDataPath(plugin));

        if(!dataFolder.exists()) return false;
        else {
            File f = new File(dataFolder.getAbsolutePath() + filePath);
            return f.exists();
        }
    }

    public static boolean makeDataFolder() {
        return makeDataFolder(WarriorAPI.getService().getInstance());
    }

    /**
     *
     * @param plugin
     * @return `true` if the data folder exists OR if
     *          data folder was created successfully
     */
    public static boolean makeDataFolder(Plugin plugin) {
        File dataFolder = new File(getDataPath(plugin));
        if(!dataFolder.exists()) return dataFolder.mkdirs();
        else return true;
    }

    public static String getDataPath() {
        return getDataPath(WarriorAPI.getService().getInstance());
    }

    public static String getDataPath(Plugin plugin) {
        return JSON_DATA_PATH.replace("{PLUGIN_PATH}", plugin.getDataFolder().getAbsolutePath());
    }

    public static void save(JsonSerializable obj) {
        File objFile = new File(getDataPath() + obj.getFilePath());
        if(objFile.exists()) return;

        String filePath = objFile.getAbsolutePath().endsWith(".json")
                ? objFile.getAbsolutePath()
                : objFile.getAbsolutePath() + ".json";

        if(makeDataFolder()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try(FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(obj.toJson(), writer);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T extends JSONModel> T readFile(File f, Class<T> jsonModel) {
        try(JsonReader reader = new JsonReader(new FileReader(f))) {
            return new Gson().fromJson(reader, jsonModel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}