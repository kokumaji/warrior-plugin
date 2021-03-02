package com.dumbdogdiner.warrior.api.util;

import java.util.List;

public class Packet {
    private Object packet;

    public Packet(Object packet) {
        this.packet = packet;
    }

    public String getName() {
        return this.packet.getClass().getSimpleName();
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
