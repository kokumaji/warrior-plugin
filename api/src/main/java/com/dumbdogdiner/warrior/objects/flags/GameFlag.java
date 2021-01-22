package com.dumbdogdiner.warrior.objects.flags;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public abstract class GameFlag<T, F extends GameFlag<T, F>> {

    @Getter
    public final String flagName;
    @Getter
    protected T value;

    protected GameFlag(@NotNull T value) {
        this.value = value;

        char[] chars = this.getClass().getSimpleName().replace("Flag", "").toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                sb.append(Character.toLowerCase(chars[i]));
            } else if (Character.isUpperCase(chars[i])) {
                sb.append('-').append(Character.toLowerCase(chars[i]));
            } else {
                sb.append(chars[i]);
            }
        }

        this.flagName = sb.toString();
    }

    public abstract F setValue(T value);

    public abstract F parse(@NotNull String value);

    public abstract String toString();

    public abstract F flagOf(@NotNull T value);

}
