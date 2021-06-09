package com.dumbdogdiner.warrior.api.kit.weapons;

import lombok.Getter;

public enum ItemRarity {

    COMMON("&8&lCOMMON"),

    UNCOMMON("&a&lCOMMON"),

    RARE("&9&lRARE"),

    EPIC("&5&lEPIC");

    @Getter
    private String formatted;

    ItemRarity(String s) {
        this.formatted = s;
    }
}
