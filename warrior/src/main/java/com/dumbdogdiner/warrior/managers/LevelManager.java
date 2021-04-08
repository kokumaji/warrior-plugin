package com.dumbdogdiner.warrior.managers;

import com.dumbdogdiner.warrior.api.managers.WarriorLevelManager;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.util.MathUtil;

public class LevelManager implements WarriorLevelManager {
    // level constants
    public static final int MIN_XP = 5;
    public static final int MAX_XP = 20;

    public int xpToLevel(int xp) {
        double res = Math.floor((Math.sqrt(20 * xp * 500) - 50) / 10) + 1;
        if(res < 0) res = 0;

        int actualLevel = 0;
        for(int i = 0; i < res; i++) {
            int sub = levelToXp(i);
            if(sub <= xp) {
                xp -= sub;
                actualLevel++;
            }
        }

        return actualLevel;
    }

    public int levelToXp(int level) {
        return 5 * (level ^ 2) + (50 * level) + 100;
    }


    public double getProgress(WarriorUser user) {
        double nextXp = levelToXp(user.getLevel());
        return MathUtil.round((user.getRelativeXp() / nextXp), 2);
    }

}
