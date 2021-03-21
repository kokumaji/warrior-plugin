package com.dumbdogdiner.warrior.api.nms.networking.packets;

import com.dumbdogdiner.warrior.api.nms.PacketType;
import com.dumbdogdiner.warrior.api.nms.networking.Protocol;
import com.dumbdogdiner.warrior.api.nms.networking.ProtocolDirection;
import com.dumbdogdiner.warrior.api.nms.objects.SoundEffect;
import com.dumbdogdiner.warrior.api.reflection.FieldUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Packet {

    @Getter
    private Object handle;

    @Getter
    private final Protocol protocol;

    public Packet(Object packet) {
        this.handle = packet;
        this.protocol = guessProtocol(packet.getClass().getSimpleName());
    }

    public Packet(PacketType packetType) {
        Class<?> packetClass = NMSUtil.getNMSClass(packetType.getName());
        this.protocol = packetType.getProtocol();
        try {
            this.handle = packetClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Integer getPacketId(ProtocolDirection direction) {
        Object nmsProtocol = Protocol.toNMS(protocol);
        Object nmsDirection = ProtocolDirection.toNMS(direction);

        Integer id = null;

        try {
            id = (Integer) nmsProtocol.getClass().getMethod("a", nmsDirection.getClass(), NMSUtil.NMS_PACKET_CLASS)
                    .invoke(nmsProtocol, nmsDirection, handle);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        // if not successful, return -1
        // to indicate invalid packet
        return id == null ? -1 : id;
    }

    public static @NotNull Method getWriteMethod() {
        Method method = null;

        try {
            method = NMSUtil.NMS_PACKET_CLASS.getMethod("b", NMSUtil.getNMSClass("PacketDataSerializer"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(method);
    }

    private Protocol guessProtocol(String packetName) {
        packetName = packetName.replace("Packet", "");
        if(packetName.startsWith("Play")) return Protocol.PLAY;
        else if(packetName.startsWith("Handshaking")) return Protocol.HANDSHAKING;
        else if(packetName.startsWith("Status")) return Protocol.STATUS;
        else if(packetName.startsWith("Login")) return Protocol.LOGIN;

        return Protocol.PLAY;
    }

    public void setDeclared(String fieldName, Object value) {
        try {
            Field f = this.handle.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(handle, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void set(String fieldName, Object value) {
        try {
            Field f = this.handle.getClass().getField(fieldName);
            f.setAccessible(true);
            f.set(handle, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void set(Class<?> type, Object value) {
        try {
            Field f = FieldUtil.getDeclaredField(type, this.handle.getClass());
            f.setAccessible(true);
            f.set(handle, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setUUID(UUID uuid) {
        try {
            Field f = FieldUtil.getDeclaredField(UUID.class, this.handle.getClass());
            f.setAccessible(true);
            f.set(handle, uuid);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setSound(Sound sound) {
        try {
            Field f = FieldUtil.getDeclaredField(SoundEffect.SOUNDEFFECT_CLASS, this.handle.getClass());
            f.setAccessible(true);
            f.set(handle, SoundEffect.fromBukkit(sound).toNMS());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setDataWatcherItems(Object dataWatcher) {
        try {
            Object dataWatcherItems = dataWatcher.getClass().getMethod("b").invoke(dataWatcher);
            Field f = FieldUtil.getDeclaredField(List.class, this.handle.getClass());
            f.setAccessible(true);

            f.set(handle, dataWatcherItems);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setInteger(int pos, int i) {
        List<Field> fields = FieldUtil.getDeclaredFields(Integer.TYPE, this.handle.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(handle, i);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setDouble(int pos, double d) {
        List<Field> fields = FieldUtil.getDeclaredFields(Double.TYPE, this.handle.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(handle, d);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setBoolean(int pos, boolean bool) {
        List<Field> fields = FieldUtil.getDeclaredFields(Boolean.TYPE, this.handle.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(handle, bool);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setFloat(int pos, float fl) {
        List<Field> fields = FieldUtil.getDeclaredFields(Float.TYPE, this.handle.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(handle, fl);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setString(int pos, String s) {
        List<Field> fields = FieldUtil.getDeclaredFields(String.class, this.handle.getClass());
        try {
            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(handle, s);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setVec3d(int pos, Vector vector) {
        try {
            Object vec3d = NMSUtil.toVec3d(vector);
            List<Field> fields = FieldUtil.getDeclaredFields(NMSUtil.getNMSClass("Vec3D"), this.handle.getClass());

            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(handle, vec3d);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public void setChatComponent(int pos, String string) {
        try {
            Object icbc = NMSUtil.getNMSClass("IChatBaseComponent")
                    .getDeclaredClasses()[0]
                    .getMethod("a", String.class)
                    .invoke(NMSUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0], "{\"text\": \"" + string + "\"}");
            
            List<Field> fields = FieldUtil.getDeclaredFields(NMSUtil.getNMSClass("IChatBaseComponent"), this.handle.getClass());

            Field f = fields.get(pos);
            f.setAccessible(true);
            f.set(handle, icbc);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean equals(PacketType type) {
        return this.getName().equals(type.getName());
    }

    public String getName() {
        return this.handle.getClass().getSimpleName();
    }

    public SoundEffect getSound() {
        return SoundEffect.fromPacket(this);
    }

    public List<Boolean> getBooleans() { return FieldUtil.getWithType(boolean.class, this.handle.getClass(), this.handle); }

    public List<String> getStrings() { return FieldUtil.getWithType(String.class, this.handle.getClass(), this.handle); }

    public List<Integer> getIntegers() {
        return FieldUtil.getWithType(int.class, this.handle.getClass(), this.handle);
    }

    public List<Double> getDoubles() { return FieldUtil.getWithType(double.class, this.handle.getClass(), this.handle); }

    public List<Float> getFloats() {
        return FieldUtil.getWithType(float.class, this.handle.getClass(), this.handle);
    }

    public List<Long> getLongs() {
        return FieldUtil.getWithType(long.class, this.handle.getClass(), this.handle);
    }

    public List<Short> getShorts() {
        return FieldUtil.getWithType(short.class, this.handle.getClass(), this.handle);
    }

    public <T> List<T> get(Class<T> type) {
        return FieldUtil.getWithType(type, this.handle.getClass(), this.handle);
    }
}
