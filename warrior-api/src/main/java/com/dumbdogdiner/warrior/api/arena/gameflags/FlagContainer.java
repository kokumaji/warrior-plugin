package com.dumbdogdiner.warrior.api.arena.gameflags;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlagContainer {

    /**
     *  COLOR VALUES FOR STRINGIFIED LIST
     */
    private static final String DEFAULT    = ChatColor.WHITE.toString();
    private static final String BOOL_TRUE  = ChatColor.GREEN.toString();
    private static final String BOOL_FALSE = ChatColor.RED.toString();
    private static final String NUMBER     = ChatColor.GOLD.toString();

    private static final String LIST_ENTRY = ChatColor.GRAY + "%s = %2s%3s" + ChatColor.RESET;


    @Getter
    private final Map<Class<?>, GameFlag<?, ?>> flags = new HashMap<>();

    public FlagContainer() {

    }

    public <V, T extends GameFlag<V, ?>> void addFlag(@NotNull T flag) {
        flags.put(flag.getClass(), flag);
    }

    public String toString() {
        List<String> result = new ArrayList<>();
        for(GameFlag<?, ?> f : flags.values()) {
            if(f.getValue() instanceof Boolean) {
                if((boolean)f.getValue()) result.add(String.format(LIST_ENTRY, f.getIdentifier(), BOOL_TRUE, f.toString()));
                else result.add(String.format(LIST_ENTRY, f.getIdentifier(), BOOL_FALSE, f.toString()));
            } else if(f.getValue() instanceof Number) {
                result.add(String.format(LIST_ENTRY, f.getIdentifier(), NUMBER, f.toString()));
            } else {
                result.add(String.format(LIST_ENTRY, f.getIdentifier(), DEFAULT, f.toString()));
            }
        }

        return String.join(", ", result);
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
