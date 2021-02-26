package com.dumbdogdiner.Warrior.api.kit;

import com.dumbdogdiner.Warrior.api.kit.abilities.PaceMakerAbility;

import java.util.HashMap;

public class SpecialAbilities {

    private static HashMap<Integer, Ability> abilities = new HashMap<>();

    public static void registerAbility() {
        abilities.put(abilities.size(), new PaceMakerAbility("Pacemaker", 0, 3, false, "Grants Speed 1 and Jump Boost 1 for 30 seconds."));
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

    public static final Ability PACEMAKER = getAbility(PaceMakerAbility.class);

}
