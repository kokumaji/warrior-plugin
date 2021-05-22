package com.dumbdogdiner.warrior.api.kit;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.util.json.JSONHelper;
import com.dumbdogdiner.warrior.api.util.json.JSONModel;
import com.dumbdogdiner.warrior.api.util.json.JsonSerializable;
import com.dumbdogdiner.warrior.api.util.json.models.KitModel;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface Kit extends JsonSerializable {

    /**
     * Gets the price of this kit. Setting this to 0
     * may indicate that this is a default kit.
     *
     * @return price of this kit
     */
    @NotNull default Integer getPrice() {
        return 0;
    }

    /**
     * Gets the name of this kit.
     * @return name of this kit
     */
    @NotNull String getName();

    /**
     * Gets the Kit description. Lines shouldn't be longer than 24 characters!
     * @return description of this kit
     */
    @NotNull default String[] getDescription() {
        return new String[] {
                "&7Default Kit", " "
        };
    }

    /**
     * Gets the Material that should be used to
     * represent this Kit in a GUI.
     *
     * @return Material for GUIs
     */
    @NotNull default Material getIcon() {
        return Material.LEATHER_CHESTPLATE;
    }

    /**
     * Gets the Permission required for this Kit.
     * @return Permission as String
     */
    @NotNull default String getPermission() {
        return "warrior.kit.defaults";
    }

    /**
     * This method is used to set up the
     * Inventory for a User.
     *
     * @param user WarriorUser instance that should
     *             receive this kit.
     */
    void setInventory(WarriorUser<?> user);

    @NotNull ItemStack[] getItems();

    // UNTESTED JSON CODE

    @Override @NotNull
    default String getFilePath() {
        return "kits/" + getName().toLowerCase() + ".json";
    }

    @Override @NotNull
    default JSONModel toJson() {
        return new KitModel(this);
    }

    default void save() {
        JSONHelper.save(this);
    }

    default KitModel load() {
        if(!JSONHelper.fileExists(this.getFilePath())) {
            WarriorAPI.getService().getLogger().warn("Could not load File for Kit " + getName());

            // attempting to recover the file from memory
            JSONHelper.save(this);
        }

        return JSONHelper.readFile(new File(this.getFilePath()), KitModel.class);

    }

}
