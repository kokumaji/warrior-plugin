package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.kit.BaseKit;
import com.dumbdogdiner.Warrior.api.kit.kits.ArcherKit;
import com.dumbdogdiner.Warrior.api.kit.kits.CustomKit;
import com.dumbdogdiner.Warrior.api.kit.kits.WarriorKit;
import com.dumbdogdiner.Warrior.api.models.CustomKitModel;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.bukkit.Material;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class KitManager {

    private static final ArrayList<BaseKit> kits = new ArrayList<>();

    public static void registerKits() {
        kits.add(new WarriorKit("Warrior", 0, "warrior.kit.warrior", Material.IRON_SWORD));
        kits.add(new ArcherKit("Archer", 0, "warrior.kit.archer", Material.BOW));

        loadFiles();
    }

    private static void loadFiles() {
        File dataFolder = new File(JSONUtil.KIT_DATA_PATH);
        File[] files = dataFolder.listFiles();

        if(files == null) return;

        int i = 0;
        for(File f : files) {
            addKit(f);
            i++;
        }

        Warrior.getInstance().getLogger().info("Loaded " + i + " custom kit(s) from data folder!");

    }

    public static void addKit(File f) {
        if(!f.canRead()) return;
        try(JsonReader reader = new JsonReader(new FileReader(f))) {
            CustomKitModel model = new Gson().fromJson(reader, CustomKitModel.class);
            kits.add(new CustomKit(model));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addKit(CustomKit kit) {
        kits.add(kit);
    }


    public static BaseKit get(String n) {
        for(BaseKit k : kits) {
            if(k.getName().equalsIgnoreCase(n)) return k;
        }
        return kits.get(0);
    }


    public static List<BaseKit> getKits() {
        return kits;
    }
}
