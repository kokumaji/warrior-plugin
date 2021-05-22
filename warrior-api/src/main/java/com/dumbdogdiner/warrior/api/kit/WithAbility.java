package com.dumbdogdiner.warrior.api.kit;

import com.dumbdogdiner.warrior.api.user.WarriorUser;

/**
 * Interface for Kit Abilities
 */
public interface WithAbility {

    /**
     * Runs the Ability specific code.
     * TODO: how do we want to handle this exactly???
     *
     * @param user WarriorUser instance that this
     *             ability should be applied to.
     */
    default void execute(WarriorUser<?> user) {

    }

    /**
     * Returns the Ability Cooldown in Seconds
     * @return Seconds until Ability is ready.
     */
    default int getCooldown() {
        return 15;
    }

    /**
     * Check whether the given User can use this Ability
     *
     * @param user WarriorUser instance to test
     * @return Should return true if the ability can be used.
     */
    boolean abilityReady(WarriorUser<?> user);

}