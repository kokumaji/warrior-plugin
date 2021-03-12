package com.dumbdogdiner.warrior.api.nms.objects;

import com.dumbdogdiner.warrior.api.nms.Packet;
import com.dumbdogdiner.warrior.api.util.FieldUtil;
import com.dumbdogdiner.warrior.api.util.NMSUtil;
import org.bukkit.Sound;

public class SoundEffect {

    public static final Class<?> SOUNDEFFECT_CLASS = NMSUtil.getNMSClass("SoundEffect");

    private Object nmsSound;
    private MinecraftKey key;

    protected SoundEffect(String name) {
        this.nmsSound = FieldUtil.getConstant(name, NMSUtil.getNMSClass("SoundEffects"));
        this.key = new MinecraftKey(FieldUtil.getWithType(MinecraftKey.MINECRAFTKEY_CLASS, SOUNDEFFECT_CLASS, nmsSound).get(0));
    }

    protected SoundEffect(Object sound) {
        this.nmsSound = sound;
        this.key = new MinecraftKey(FieldUtil.getWithType(MinecraftKey.MINECRAFTKEY_CLASS, SOUNDEFFECT_CLASS, sound).get(0));
    }

    public MinecraftKey getKey() {
        return key;
    }

    public Object toNMS() {
        return nmsSound;
    }

    public Sound toBukkit() {
        String soundString = key.toString().replace("minecraft:", "")
                             .replace(".", "_").toUpperCase();

        return Sound.valueOf(soundString);
    }

    public static SoundEffect fromBukkit(Sound sound) {
        String soundName = sound.name();
        return new SoundEffect(soundName);
    }

    public static SoundEffect fromPacket(Packet packet) {
        Object sound = FieldUtil.getWithType(SOUNDEFFECT_CLASS, packet.getPacket().getClass(), packet.getPacket()).get(0);
        return new SoundEffect(sound);
    }

}
