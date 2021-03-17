package com.dumbdogdiner.warrior.api.effects;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum ParticleTrail {

    HEART(0x00000001, "Hearts", null);

    @Accessors(fluent = true)
    @Getter
    String friendlyName;

    @Getter ParticleTrail parent;
    @Getter int unlockValue;

    ParticleTrail(int value, String name, ParticleTrail parent) {
        this.unlockValue = value;
        this.friendlyName = name;
        this.parent = parent;
    }

}
