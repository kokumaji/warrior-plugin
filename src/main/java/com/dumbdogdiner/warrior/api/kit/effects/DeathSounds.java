package com.dumbdogdiner.warrior.api.kit.effects;

import com.dumbdogdiner.warrior.api.WarriorUser;

import java.util.ArrayList;
import java.util.List;

public class DeathSounds {


    public static List<DeathSound> getDeathSounds(WarriorUser user) {
        ArrayList<DeathSound> unlock = new ArrayList<>();
        for(DeathSound sound : DeathSound.values()) {
            if(canUse(user, sound))
                unlock.add(sound);
        }

        return unlock;
    }

    public static boolean canUse(WarriorUser user, DeathSound sound) {
        return (user.getDeathSounds() & sound.getUnlockValue()) == sound.getUnlockValue();
    }

}
