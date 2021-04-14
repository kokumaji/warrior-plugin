package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.stickyapi.bukkit.gui.GUI;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.managers.WarriorGUIManager;
import com.dumbdogdiner.warrior.gui.*;
import com.dumbdogdiner.warrior.gui.settings.LanguageGUI;
import com.dumbdogdiner.warrior.gui.settings.NotificationsGUI;
import com.dumbdogdiner.warrior.gui.settings.PrivacyGUI;
import com.dumbdogdiner.warrior.gui.settings.SettingsGUI;

import java.util.HashMap;

public class GUIManager implements WarriorGUIManager  {

    private HashMap<Class<?>, GUI> map = new HashMap<>();

    public void registerGUIs() {
        map.put(ArenaGUI.class, new ArenaGUI(5, "Select Arena", Warrior.getInstance()));
        map.put(KitGUI.class, new KitGUI(5, "Select a Kit", Warrior.getInstance()));
        map.put(DeathSoundGUI.class, new DeathSoundGUI());
        map.put(SettingsGUI.class, new SettingsGUI());
        map.put(LanguageGUI.class, new LanguageGUI());
        map.put(NotificationsGUI.class, new NotificationsGUI());
        map.put(PrivacyGUI.class, new PrivacyGUI());
        map.put(ParticleTrailGUI.class, new ParticleTrailGUI(Warrior.getInstance()));
    }

    public <T> T get(Class<T> guiClass) {
        return guiClass.cast(map.get(guiClass));
    }

}
