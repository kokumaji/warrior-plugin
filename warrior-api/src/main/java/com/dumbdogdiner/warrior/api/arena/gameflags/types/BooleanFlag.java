package com.dumbdogdiner.warrior.api.arena.gameflags.types;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.arena.gameflags.GameFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

public abstract class BooleanFlag<F extends GameFlag<Boolean, F>> extends GameFlag<Boolean, F> {

    private static final Collection<String> TRUE_KEYWORDS = Arrays.asList("true", "yes", "allow");
    private static final Collection<String> FALSE_KEYWORDS = Arrays.asList("false", "no", "deny");

    public BooleanFlag(@NotNull String identifier, @NotNull Boolean value, String... description) {
        super(identifier, value, description);
    }

    @Override
    public F parse(@NotNull String input) {
        if(TRUE_KEYWORDS.contains(input.toLowerCase())) {
            return this.flagOf(true);
        } else if(FALSE_KEYWORDS.contains(input.toLowerCase())) {
            return this.flagOf(false);
        } else {
            String msg = String.format("Invalid flag argument '%s' for boolean flag. Defaulting to 'false'", input);
            Warrior.getPluginLogger().warn(msg);
            return this.flagOf(false);
        }
    }

    @Override
    public F merge(@NotNull Boolean newValue) {
        return flagOf(getValue() || newValue);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

}
