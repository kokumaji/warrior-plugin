package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.gui.ArenaGUI;
import com.dumbdogdiner.warrior.gui.DeathSoundGUI;
import com.dumbdogdiner.warrior.gui.KitGUI;
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.gui.SettingsGUI;

import java.util.HashMap;

public class GUIManager {

    private static HashMap<Class<?>, GUI> map = new HashMap<>();

    public static void registerGUIs() {
        map.put(ArenaGUI.class, new ArenaGUI(5, "Select Arena", Warrior.getInstance()));
        map.put(KitGUI.class, new KitGUI(5, "Select a Kit", Warrior.getInstance()));
        map.put(DeathSoundGUI.class, new DeathSoundGUI());
        map.put(SettingsGUI.class, new SettingsGUI());
    }

    public static  <T> T get(Class<T> guiClass) {
        return guiClass.cast(map.get(guiClass));
    }

}
