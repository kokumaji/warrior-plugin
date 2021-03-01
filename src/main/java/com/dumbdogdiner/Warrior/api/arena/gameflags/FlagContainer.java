package com.dumbdogdiner.Warrior.api.arena.gameflags;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FlagContainer {

    @Getter
    private final Map<Class<?>, GameFlag<?, ?>> flags = new HashMap<>();

    public FlagContainer() {

    }

    public <V, T extends GameFlag<V, ?>> void addFlag(@NotNull T flag) {
        flags.put(flag.getClass(), flag);
    }

    public <V, T extends GameFlag<V, ?>> void removeFlag(@NotNull Class<?> flag) {
        flags.remove(flag);
    }

    public <V, T extends GameFlag<V, ?>> T getFlag(Class<? extends T> flagClass) {
        GameFlag<?, ?> flag = flags.get(flagClass);
        if(flag != null) {
            return flagClass.cast(flag);
        }

        return null;
    }
}
