package me.kokumaji.warrior.api.kit;

public interface KitContainer {

    <T extends Kit> boolean registerKit(T kit);

    <T extends Kit> T get(Class<T> kitClass);

    Kit get(String kitName);

}
