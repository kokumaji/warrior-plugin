package com.dumbdogdiner.warrior.api.nms;

import com.dumbdogdiner.stickyapi.bukkit.nms.BukkitHandler;
import com.dumbdogdiner.warrior.api.nms.objects.SoundEffect;
import com.dumbdogdiner.warrior.api.util.FieldUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.List;

public class Packet {

    @Getter
    private Object packet;

    public Packet(Object packet) {
        this.packet = packet;
    }

    public void setDeclared(String fieldName, Object value) {
        try {
            Field f = this.packet.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(packet, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void set(String fieldName, Object value) {
        try {
            Field f = this.packet.getClass().getField(fieldName);
            f.setAccessible(true);
            f.set(packet, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setSound(Sound sound) {
        try {
            Field f = FieldUtil.getDeclaredField(SoundEffect.SOUNDEFFECT_CLASS, this.packet.getClass());
            f.setAccessible(true);
            f.set(packet, SoundEffect.fromBukkit(sound).toNMS());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setInteger(int pos, int i) {
        List<Field> fields = FieldUtil.getDeclaredFields(Integer.TYPE, this.packet.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(packet, i);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setDouble(int pos, double d) {
        List<Field> fields = FieldUtil.getDeclaredFields(Double.TYPE, this.packet.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(packet, d);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setString(int pos, String s) {
        List<Field> fields = FieldUtil.getDeclaredFields(String.class, this.packet.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(packet, s);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setVec3d(int pos, Vector vector) {
        try {
            Object vec3d = NMSUtil.toVec3d(vector);
            List<Field> fields = FieldUtil.getDeclaredFields(BukkitHandler.getNMSClass("Vec3D"), this.packet.getClass());

            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(packet, vec3d);
        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.packet.getClass().getSimpleName();
    }

    public SoundEffect getSound() {
        return SoundEffect.fromPacket(this);
    }

    public List<Boolean> getBooleans() { return FieldUtil.getWithType(boolean.class, this.packet.getClass(), this.packet); }

    public List<String> getStrings() { return FieldUtil.getWithType(String.class, this.packet.getClass(), this.packet); }

    public List<Integer> getIntegers() {
        return FieldUtil.getWithType(int.class, this.packet.getClass(), this.packet);
    }

    public List<Double> getDoubles() { return FieldUtil.getWithType(double.class, this.packet.getClass(), this.packet); }

    public List<Float> getFloats() {
        return FieldUtil.getWithType(float.class, this.packet.getClass(), this.packet);
    }

    public List<Long> getLongs() {
        return FieldUtil.getWithType(long.class, this.packet.getClass(), this.packet);
    }

    public List<Short> getShorts() {
        return FieldUtil.getWithType(short.class, this.packet.getClass(), this.packet);
    }

    public <T> List<T> get(Class<T> type) {
        return FieldUtil.getWithType(type, this.packet.getClass(), this.packet);
    }
}
