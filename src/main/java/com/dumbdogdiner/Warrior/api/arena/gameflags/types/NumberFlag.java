package com.dumbdogdiner.Warrior.api.arena.gameflags.types;

import com.dumbdogdiner.Warrior.api.arena.gameflags.GameFlag;
import com.dumbdogdiner.stickyapi.common.util.MathUtil;
import org.jetbrains.annotations.NotNull;

public abstract class NumberFlag<N extends Number & Comparable<N>, F extends GameFlag<N, F>> extends GameFlag<N, F> {

    private final N min;
    private final N max;

    public NumberFlag(@NotNull String identifier, @NotNull N value, @NotNull N minValue, @NotNull N maxValue, String... description) {
        super(identifier, value, description);

        if(maxValue.compareTo(minValue) < 0)
            throw new IllegalArgumentException("min value is bigger than max");

        this.min = minValue;
        this.max = maxValue;

    }

    @Override
    public F parse(@NotNull String input) {
        N number = parseNumber(input);
        if(number.compareTo(min) < 0 || number.compareTo(max) > 0)
            throw new IllegalArgumentException("input number not in range");

        return flagOf(number);
    }

    @NotNull protected abstract N parseNumber(String input) throws IllegalArgumentException;

}
