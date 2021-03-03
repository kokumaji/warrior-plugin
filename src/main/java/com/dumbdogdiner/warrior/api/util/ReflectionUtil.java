package com.dumbdogdiner.warrior.api.util;

import org.jetbrains.annotations.NotNull;

public class ReflectionUtil {

    public static boolean isSuperclassRecursive(Class<?> testClass, Class<?> superClass) {
        Class<?> c = testClass;
        while (c != null) {
            if (c.equals(superClass))
                return true;
            c = c.getSuperclass();
        }
        return false;
    }

    public static boolean classImplements(@NotNull Class<?> testClass, @NotNull Class<?> interfaceClass) {
        return interfaceClass.isAssignableFrom(testClass);
    }

}
