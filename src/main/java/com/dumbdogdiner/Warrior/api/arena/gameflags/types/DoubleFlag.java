package com.dumbdogdiner.Warrior.api.arena.gameflags.types;

import org.jetbrains.annotations.NotNull;

public abstract class DoubleFlag<F extends NumberFlag<Double, F>> extends NumberFlag<Double, F> {

    public DoubleFlag(@NotNull String identifier, double value, double minValue, double maxValue, String... description) {
        super(identifier, value, minValue, maxValue, description);
    }

    @Override
    public F merge(@NotNull Double newValue) {
        return null;
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    protected @NotNull Double parseNumber(String input) throws IllegalArgumentException {
        try {
            return Double.parseDouble(input);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("expected double value, got '%s'", input));
        }
    }
}
