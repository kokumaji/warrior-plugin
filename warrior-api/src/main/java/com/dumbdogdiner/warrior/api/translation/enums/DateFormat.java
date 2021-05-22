package com.dumbdogdiner.warrior.api.translation.enums;

import lombok.Getter;

public enum DateFormat {

    EN_US("MM/dd/yyyy"),
    EN_GB("dd/MM/yyyy"),
    DE_DE("dd.MM.yyyy");

    @Getter
    private final String formatString;

    DateFormat(String s) {
        this.formatString = s;
    }
}


