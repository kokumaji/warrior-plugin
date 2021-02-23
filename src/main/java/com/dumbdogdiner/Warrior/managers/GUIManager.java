package com.dumbdogdiner.Warrior.managers;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.gui.ArenaGUI;
import com.dumbdogdiner.Warrior.gui.KitGUI;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;

import java.util.HashMap;

public class GUIManager {

    private static HashMap<Class<?>, GUI> map = new HashMap<>();

    public static void registerGUIs() {
        map.put(ArenaGUI.class, new ArenaGUI(5, "Select Arena", Warrior.getInstance()));
        map.put(KitGUI.class, new KitGUI(5, "Select a Kit", Warrior.getInstance()));
    }

    public static  <T> T get(Class<T> guiClass) {
        return guiClass.cast(map.get(guiClass));
    }

}
