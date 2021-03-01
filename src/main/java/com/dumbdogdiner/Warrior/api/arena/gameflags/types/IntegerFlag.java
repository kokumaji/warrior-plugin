package com.dumbdogdiner.Warrior.api.arena.gameflags.types;

import org.jetbrains.annotations.NotNull;

public abstract class IntegerFlag<F extends NumberFlag<Integer, F>> extends NumberFlag<Integer, F>  {

    public IntegerFlag(@NotNull String identifier, int value, int minValue, int maxValue, String... description) {
        super(identifier, value, minValue, maxValue, description);
    }

    @Override
    public F merge(@NotNull Integer newValue) {
        return null;
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    protected F flagOf(@NotNull Integer value) {
        return null;
    }

    @Override
    protected @NotNull Integer parseNumber(String input) throws IllegalArgumentException {
        try {
            return Integer.parseInt(input);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("expected integer value, got '%s'", input));
        }
    }
}
