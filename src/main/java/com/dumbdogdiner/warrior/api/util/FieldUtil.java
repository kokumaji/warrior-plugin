package com.dumbdogdiner.warrior.api.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldUtil {
    public static <T> T getValue(Class<T> fieldType, String fieldName, Class<?> targetClass) {
        return getValue(fieldType, fieldName, targetClass, null);
    }

    public static <T> T getValue(Class<T> fieldType, String fieldName, Class<?> targetClass, Object targetObject) {
        try {
            Field f = targetClass.getField(fieldName);
            f.setAccessible(true);
            return fieldType.cast(f.get(targetObject));
        } catch (NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> getWithType(Class<T> fieldType, Class<?> targetClass) {
        return getWithType(fieldType, targetClass, null);
    }

    public static <T> List<T> getWithType(Class<T> fieldType, Class<?> targetClass, Object targetObject) {
        List<T> result = new ArrayList<>();
        for (Field f : targetClass.getDeclaredFields()) {
            if (f.getType() == fieldType)
                try {
                    f.setAccessible(true);
                    T value = fieldType.cast(f.get(targetObject));
                    result.add(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }
}
