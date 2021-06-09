package me.kokumaji.warrior.api.arena;

import me.kokumaji.warrior.api.user.WarriorUser;
import me.kokumaji.warrior.api.util.json.JSONModel;
import me.kokumaji.warrior.api.util.json.JsonSerializable;
import me.kokumaji.warrior.api.util.json.models.ArenaModel;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface WarriorArena extends JsonSerializable {

    @NotNull String getName();

    @NotNull String[] getDescription();

    default Material getIcon() {
        return Material.FILLED_MAP;
    }

    default int getMaxPlayers() {
        return 20;
    }

    <T extends WarriorUser<T>> List<T> getPlayers();

    <T extends WarriorUser<T>> void addPlayer(T user);

    <T extends WarriorUser<T>> void removePlayer(T user);

    // Region getBounds();

    @Override @NotNull
    default String getFilePath() {
        return "arenas/" + this.getName() + ".json";
    }

    @Override @NotNull
    default JSONModel toJson() {
        return new ArenaModel(this);
    }

}
