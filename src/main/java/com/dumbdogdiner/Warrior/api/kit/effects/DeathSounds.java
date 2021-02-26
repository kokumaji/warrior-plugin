package com.dumbdogdiner.Warrior.api.kit.effects;

import com.dumbdogdiner.Warrior.api.WarriorUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DeathSounds {

    private static final HashSet<DeathSound> sounds = new HashSet<>();

    public static void registerSounds() {
        sounds.add(DeathSound.WATERSPLASH);
        sounds.add(DeathSound.GHAST_DEATH);
        sounds.add(DeathSound.THUNDERSTORM);
    }

    public static List<DeathSound> getDeathSounds(WarriorUser user) {
        ArrayList<DeathSound> unlock = new ArrayList<>();
        for(DeathSound sound : sounds) {
            if((user.getDeathSounds() & sound.getUnlockValue()) == sound.getUnlockValue())
                unlock.add(sound);
        }

        return unlock;
    }

    public boolean canUse(WarriorUser user, DeathSound sound) {
        return (user.getDeathSounds() & sound.getUnlockValue()) == sound.getUnlockValue();
    }

}
