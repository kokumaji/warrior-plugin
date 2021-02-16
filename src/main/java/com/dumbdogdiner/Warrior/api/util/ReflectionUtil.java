package com.dumbdogdiner.Warrior.api.util;

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

}
