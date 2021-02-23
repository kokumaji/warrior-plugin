package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.api.kit.IWarriorKit;
import com.dumbdogdiner.Warrior.api.kit.kits.WarriorKit;
import com.dumbdogdiner.Warrior.gui.KitGUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KitManager {

    private static final ArrayList<IWarriorKit> kits = new ArrayList<>();

    public static void registerKits() {
        kits.add(new WarriorKit());
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
