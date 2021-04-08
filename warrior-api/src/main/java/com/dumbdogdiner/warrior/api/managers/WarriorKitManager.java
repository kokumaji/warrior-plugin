package com.dumbdogdiner.warrior.api.managers;

import com.dumbdogdiner.warrior.api.kit.BaseKit;
import com.dumbdogdiner.warrior.api.kit.kits.CustomKit;

import java.io.File;
import java.util.List;

/**
 * Manages the registration and loading of kits.
 */
public interface WarriorKitManager {
    /**
     * Load a kit from the target file.
     * @param f The target file
     */
    void addKit(File f);

    /**
     * Add a kit to the kit manager.
     * @param kit The kit to add.
     */
    void addKit(CustomKit kit);

    /**
     * Get the kit with the target name.
     * @param name
     * @return A kit extending the {@link BaseKit} class, if one exists.
     */
    BaseKit get(String name);

    /**
     * Fetch a list of available kits.
     * @return A {@link List} of available kits.
     */
    List<BaseKit> getKits();
}
