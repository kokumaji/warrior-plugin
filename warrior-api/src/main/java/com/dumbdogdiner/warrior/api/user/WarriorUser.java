package com.dumbdogdiner.warrior.api.user;

import java.util.UUID;

public interface WarriorUser<T extends WarriorUser> {

    UUID getUniqueId();

}
