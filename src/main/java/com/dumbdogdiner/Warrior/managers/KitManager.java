package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.kit.IWarriorKit;
import com.dumbdogdiner.Warrior.api.kit.kits.ArcherKit;
import com.dumbdogdiner.Warrior.api.kit.kits.CustomKit;
import com.dumbdogdiner.Warrior.api.kit.kits.WarriorKit;
import com.dumbdogdiner.Warrior.api.util.JSONUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class KitManager {

    private static final ArrayList<IWarriorKit> kits = new ArrayList<>();

    public static void registerKits() {
        kits.add(new WarriorKit());
        kits.add(new ArcherKit());

        loadFiles();
    }

    private static void loadFiles() {
        File dataFolder = new File(JSONUtil.KIT_DATA_PATH);
        File[] files = dataFolder.listFiles();

        if(files == null) return;

        int i = 0;
        for(File f : files) {
            addKit(new CustomKit(f));
            i++;
        }

        Warrior.getInstance().getLogger().info("Loaded " + i + " custom kit(s) from data folder!");

    }

    public static void addKit(CustomKit k) {
        kits.add(k);
    }

    public static IWarriorKit get(String n) {
        for(IWarriorKit k : kits) {
            if(k.getName().equalsIgnoreCase(n)) return k;
        }
        return kits.get(0);
    }


    public static List<IWarriorKit> getKits() {
        return kits;
    }
}
