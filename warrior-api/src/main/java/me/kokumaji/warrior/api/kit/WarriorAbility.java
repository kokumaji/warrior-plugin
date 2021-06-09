package com.dumbdogdiner.warrior.api.kit;

import com.dumbdogdiner.warrior.api.user.WarriorUser;

public interface WarriorAbility {

    String getName();

    void run(WarriorUser<?> user);

    boolean shouldExecute(WarriorUser<?> user);

}
