package com.dumbdogdiner.warrior.api.kit;

/**
 * Interface for Kit Abilities
 */
public interface WithAbility {

    default execute(WarriorUser user) {

    }

    /**
     * Returns the Ability Cooldown in Seconds
     * @return Seconds until Ability is ready.
     */
    default int getCooldown() {
        return 15;
    }

    boolean abilityReady();

}