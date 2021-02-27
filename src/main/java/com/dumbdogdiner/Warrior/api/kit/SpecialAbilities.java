package com.dumbdogdiner.Warrior.api.kit;

import com.dumbdogdiner.Warrior.api.kit.abilities.PaceMakerAbility;

import java.util.HashMap;

public class SpecialAbilities {

    private static final HashMap<Integer, Ability> abilities = new HashMap<>();

    public static final Ability PACEMAKER = new PaceMakerAbility("Pacemaker", 0, 3);
    public static final Ability MEDIC = new PaceMakerAbility("Medic", 0, 3);

    public static void registerAbility() {
        abilities.put(abilities.size(), PACEMAKER);
        abilities.put(abilities.size(), MEDIC);
    }

    public static Ability fromString(String s) {
        for(Ability ability : abilities.values()) {
            if(ability.getName().equalsIgnoreCase(s)) return ability;
        }

        return null;
    }

    public static <T> T getAbility(Class<T> tClass)  {
        for(Ability ability : abilities.values()) {
            if(ability.getClass().equals(tClass)) return tClass.cast(ability);
        }

        return null;
    }

}
