package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.kit.BaseKit;
import com.dumbdogdiner.warrior.api.kit.kits.ArcherKit;
import com.dumbdogdiner.warrior.api.kit.kits.CustomKit;
import com.dumbdogdiner.warrior.api.kit.kits.TankKit;
import com.dumbdogdiner.warrior.api.kit.kits.WarriorKit;
import com.dumbdogdiner.warrior.api.managers.WarriorKitManager;
import com.dumbdogdiner.warrior.models.CustomKitModel;
import com.dumbdogdiner.warrior.api.translation.Placeholders;
import com.dumbdogdiner.warrior.api.util.JSONUtil;
import com.dumbdogdiner.warrior.utils.DefaultMessages;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.bukkit.Material;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class KitManager implements WarriorKitManager {

    private final ArrayList<BaseKit> kits = new ArrayList<>();

    public void registerKits() {
        kits.add(new WarriorKit("Warrior", 0, "warrior.kit.warrior", Material.IRON_SWORD));
        kits.add(new ArcherKit("Archer", 0, "warrior.kit.archer", Material.BOW));
        kits.add(new TankKit("Tank", 0, "warrior.kit.tank", Material.IRON_CHESTPLATE));

        loadFiles();
    }

    /**
     * Load kits from plugin configuration.
     */
    private void loadFiles() {
        File dataFolder = new File(JSONUtil.KIT_DATA_PATH);
        File[] files = dataFolder.listFiles();

        if(files == null) return;

        int i = 0;
        for(File f : files) {
            addKit(f);
            i++;
        }

        int finalI = i;
        String msg = Placeholders.applyPlaceholders(DefaultMessages.LOADED_OBJECT, new HashMap<>() {
            {
                put("AMOUNT", Integer.toString(finalI));
                put("TYPE", "kit");
            }
        });
        Warrior.getPluginLogger().info(msg);
    }

    public void addKit(File f) {
        if(!f.canRead()) return;
        try(JsonReader reader = new JsonReader(new FileReader(f))) {
            CustomKitModel model = new Gson().fromJson(reader, CustomKitModel.class);
            kits.add(new CustomKit(model));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addKit(CustomKit kit) {
        kits.add(kit);
    }

    public BaseKit get(String n) {
        for(BaseKit k : kits) {
            if(k.getName().equalsIgnoreCase(n)) return k;
        }
        return kits.get(0);
    }

    public List<BaseKit> getKits() {
        return kits;
    }
}
