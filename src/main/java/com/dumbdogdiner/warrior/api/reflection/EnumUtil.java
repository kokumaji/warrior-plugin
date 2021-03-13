package com.dumbdogdiner.warrior.api.reflection;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class EnumUtil {

    public static Enum<?>[] getEnums(Class<Enum<?>> enumClass) {
        Enum<?>[] enums;
        enums = enumClass.getEnumConstants();

        Preconditions.checkState(enums.length > 0, "No Enums found.");
        return enums;
    }

    public static @NotNull Method getValueOfMethod(Class<?> clazz) {
         Method method = null;

         try {
             method = clazz.getMethod("valueOf", String.class);
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         }

         return Objects.requireNonNull(method);
    }

    public static Object asObject(Class<?> clazz, String enumName) {
        Object obj = null;

        try {
            obj = getValueOfMethod(clazz).invoke(null, enumName);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @SuppressWarnings("unchecked")
    public static @NotNull Enum<?> getEnum(Class<?> enumClass, String enumName) {
        Enum<?> en = null;
        for(Enum<?> e : getEnums((Class<Enum<?>>)enumClass)) {
            if(e.name().equalsIgnoreCase(enumName)) {
                en = e;
                break;
            }
        }

        return Objects.requireNonNull(en);
    }

}
