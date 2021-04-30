package com.dumbdogdiner.warrior.api.kit;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import org.bukkit.Material;

public interface BaseKit {

    /**
     * Gets the Kit Price - TODO: Add Vault Support
     * @return Price of this Kit
     */
    default Integer getPrice() {
        return 0;
    }

    /**
     * Gets the Kit Name
     * @return Name of this Kit
     */
    String getName();

    /**
     * Gets the Kit Description
     * @return Description of this Kit
     */
    default String[] getDescription() {
        return new String[]{
            "&7Default Kit", " "
        };
    }

    /**
     * Gets the Material that should be used to
     * represent this Kit in a GUI.
     *
     * @return Material for GUIs
     */
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

    /**
     * (UNTESTED) Gets whether the implementing Class
     * also implements {@link WithAbility}, which is used
     * for handling Special Abilities.
     *
     * @return `True` if the implementing class also
     *         implements {@link WithAbility}, otherwise `False`
     */
    default boolean hasAbility() {
        try {
            return this.getClass().isAssignableFrom(WithAbility.class);
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * This method is used to set up the
     * Inventory for a User.
     *
     * @param user WarriorUser instance that should
     *             receive this kit.
     */
    void setupInventory(WarriorUser<?> user);

}