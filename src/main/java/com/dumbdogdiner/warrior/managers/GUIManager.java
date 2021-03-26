package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.gui.*;

import java.util.HashMap;

public class GUIManager {

    private static HashMap<Class<?>, GUI> map = new HashMap<>();

    public static void registerGUIs() {
        map.put(ArenaGUI.class, new ArenaGUI(5, "Select Arena", Warrior.getInstance()));
        map.put(KitGUI.class, new KitGUI(5, "Select a Kit", Warrior.getInstance()));
        map.put(DeathSoundGUI.class, new DeathSoundGUI());
        map.put(SettingsGUI.class, new SettingsGUI());
        map.put(ParticleTrailGUI.class, new ParticleTrailGUI(Warrior.getInstance()));
    }

    public static  <T> T get(Class<T> guiClass) {
        return guiClass.cast(map.get(guiClass));
    }

}
