package com.dumbdogdiner.warrior.api.arena.gameflags;

import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class GameFlag<T, F extends GameFlag<T, F>> {

    @Getter
    private final T value;

    @Getter
    private final String[] description;

    @Getter
    private final String identifier;

    public GameFlag(@NotNull String identifier, @NotNull T value, String... description) {
        this.value = value;
        this.identifier = identifier;
        this.description = Arrays.stream(description)
                .map(TranslationUtil::translateColor)
                .toArray(String[]::new);
    }

    public abstract F parse(@NotNull final String input);

    public abstract F merge(@NotNull final T newValue);

    public abstract String toString();

    protected abstract F flagOf(@NotNull T value);

    public final F createFlagInstance(@NotNull final T value) {
        return flagOf(Preconditions.checkNotNull(value));
    }

}
