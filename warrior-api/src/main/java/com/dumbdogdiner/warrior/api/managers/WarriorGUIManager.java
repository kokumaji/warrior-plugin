package com.dumbdogdiner.warrior.api.managers;

/**
 * Manages the registration of plugin GUIs.
 */
public interface WarriorGUIManager {
    /**
     * Retrieve the GUI of the target class type.
     * @param guiClass The class of the GUI
     * @param <T> The type of the GUI
     * @return A GUI, if one of the target class type exists.
     */
    <T> T get(Class<T> guiClass);
}
