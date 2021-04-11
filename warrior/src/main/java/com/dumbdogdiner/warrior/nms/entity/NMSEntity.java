package com.dumbdogdiner.warrior.nms.entity;

import com.dumbdogdiner.warrior.api.reflection.FieldUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import lombok.Getter;
import org.bukkit.Location;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class NMSEntity {

    public static final Class<?> ENTITY_CLASS = Objects.requireNonNull(NMSUtil.getNMSClass("Entity"));

    @Getter
    private Object entity;

    @Getter
    private Object type;

    public NMSEntity(Location loc, Class<?> entityClass, NMSEntityType type) {
        Class<?> typesClass = Objects.requireNonNull(NMSUtil.getNMSClass("EntityTypes"));
        try {
            Constructor<?> entityConstructor = entityClass.getConstructor(typesClass, NMSUtil.getNMSClass("World"));
            Object entityType = FieldUtil.getConstant(type.name(), typesClass);
            this.type = entityType;
            this.entity = entityConstructor.newInstance(entityType, NMSUtil.getWorldServer(loc.getWorld()));
            this.setPosition(loc);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(Location loc) {
        try {
            Method setPosition = ENTITY_CLASS.getMethod("setPosition", Double.TYPE, Double.TYPE, Double.TYPE);
            setPosition.invoke(entity, loc.getX(), loc.getY(), loc.getZ());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setCustomName(String name) {
        try {
            Object chatComponentText = NMSUtil.getNMSClass("ChatComponentText")
                    .getDeclaredConstructor(String.class)
                    .newInstance(name);

            ENTITY_CLASS.getMethod("setCustomName", NMSUtil.getNMSClass("IChatBaseComponent")).invoke(entity, chatComponentText);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setCustomNameVisible(boolean bool) {
        try {
            ENTITY_CLASS.getMethod("setCustomNameVisible", Boolean.TYPE).invoke(entity, bool);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setInvisible(boolean bool) {
        try {
            ENTITY_CLASS.getMethod("setInvisible", Boolean.TYPE).invoke(entity, bool);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setNoGravity(boolean bool) {
        try {
            ENTITY_CLASS.getMethod("setNoGravity", Boolean.TYPE).invoke(entity, bool);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        try {
            return (int)ENTITY_CLASS.getMethod("getId").invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Object getDataWatcher() {
        try {
            return ENTITY_CLASS.getMethod("getDataWatcher").invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
