package com.dumbdogdiner.Warrior.api.kit;

import com.dumbdogdiner.Warrior.api.WarriorUser;

public interface Ability {

    public static String ACTIVE_ABILITY_STRING = "§8» §3§lSPECIAL ABILITY §8«";
    public static String DEACTIVATED_ABILITY_STRING = "§8» §7§lSPECIAL ABILITY §8«";

    String getName();
    String getDescription();

    int getCost();
    int getStreakMinimum();
    Runnable run(WarriorUser user);

    boolean canExecute(WarriorUser user, boolean value);
    boolean availableOnStart();

}
