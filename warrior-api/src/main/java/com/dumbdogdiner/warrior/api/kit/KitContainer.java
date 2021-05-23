package com.dumbdogdiner.warrior.api.kit;

import org.bukkit.plugin.Plugin;

public interface KitContainer {

    <T extends Kit> boolean registerKit(T kit);

    <T extends Kit> T get(Class<T> kitClass);

}
