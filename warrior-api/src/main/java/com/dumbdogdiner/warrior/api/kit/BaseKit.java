package com.dumbdogdiner.warrior.api.kit;

import com.dumbdogdiner.warrior.api.user.WarriorUser;

public interface BaseKit {

    Integer getPrice();

    String getName();

    default String[] getDescription() {
        return {
            "&7Default Kit", " "
        };
    }

    default Material getIcon() {
        return Material.LEATHER_CHESTPLATE;
    }

    /**
     * Gets the Permission required for this Kit.
     * @return Permission as String
     */
    default String getPermission() {
        return "warrior.kit.defaults";
    }

    default boolean hasAbility() {
        try {
            return this.getClass().isAssignableFrom(WithAbility.class);
        } catch(Exception e) {
            return false;
        }
    }

    void setupInventory(WarriorUser user);

}