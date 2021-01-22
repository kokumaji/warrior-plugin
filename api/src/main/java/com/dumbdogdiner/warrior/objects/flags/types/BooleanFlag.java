package com.dumbdogdiner.warrior.objects.flags.types;

import com.dumbdogdiner.warrior.objects.flags.GameFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public abstract class BooleanFlag<F extends GameFlag<Boolean, F>> extends GameFlag<Boolean, F> {

    List<String> ALIAS_TRUE = Arrays.asList("true", "yes", "1");
    List<String> ALIAS_FALSE = Arrays.asList("false", "no", "0");

    protected BooleanFlag(boolean value) {
        super(value);
    }

    @Override
    public F setValue(Boolean value) {
        return flagOf(value);
    }

    @Override
    public F parse(@NotNull String value) {
        if(ALIAS_TRUE.contains(value)) return this.setValue(true);
        else if(ALIAS_FALSE.contains(value)) return this.setValue(false);
        else throw new IllegalArgumentException("Invalid Flag Value: " + value);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

}
