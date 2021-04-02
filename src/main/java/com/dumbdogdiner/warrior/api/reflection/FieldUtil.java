package com.dumbdogdiner.warrior.api.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FieldUtil {

    public static Field getDeclaredField(Class<?> fieldType, Class<?> targetClass) {
        Field field = null;
        for(Field f : targetClass.getDeclaredFields()) {
            if(f.getType() == fieldType) field = f;
        }

        return Objects.requireNonNull(field);
    }

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

    public static Object getConstant(String name, Class<?> clazz) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);

            return f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return new Object();
    }

    public static String getStringConstant(String name, Class<?> clazz) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);

            return String.valueOf(f.get(null));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            return name;
        }
    }

    public static String getStringConstant(Integer pos, Class<?> clazz) {
        try {
            if(pos > clazz.getDeclaredFields().length) return String.valueOf(pos);
            Field f = clazz.getDeclaredFields()[pos];
            f.setAccessible(true);

            return String.valueOf(f.get(null));
        } catch (IllegalAccessException ignored) {
            return String.valueOf(pos);
        }
    }

    public static Object getConstant(Integer pos, Class<?> clazz) {
        try {
            Field f = clazz.getDeclaredFields()[pos];
            f.setAccessible(true);

            return f.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new Object();
    }

    public static List<Field> getDeclaredFields(Class<?> fieldType, Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for(Field f : clazz.getDeclaredFields()) {
            if(f.getType() == fieldType) fields.add(f);
        }

        return fields;
    }

    public static int indexOfDeclared(String fieldName, Class<?> clazz) {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        for(int i = 0; i < fields.size(); i++)
            if(fields.get(i).getName().equalsIgnoreCase(fieldName)) return i;

        return -1;
    }

    public static String getNameDeclared(Integer index, Class<?> clazz) {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        Field f = fields.get(index);
        if(f == null) return "null";

        return f.getName();
    }

}
