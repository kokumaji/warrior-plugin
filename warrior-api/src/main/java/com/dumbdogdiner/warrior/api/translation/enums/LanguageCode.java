package com.dumbdogdiner.warrior.api.translation.enums;

import com.dumbdogdiner.warrior.api.util.HeadTexture;
import lombok.Getter;

public enum LanguageCode {
    EN_US("English (US)", HeadTexture.UNITED_STATES),
    EN_GB("English (UK)", HeadTexture.GREAT_BRITAIN),
    DE_DE("Deutsch (Deutschland)", HeadTexture.GERMANY);

    @Getter
    private final String friendlyName;

    @Getter
    private final HeadTexture headTexture;

    LanguageCode(String name, HeadTexture texture) {
        this.friendlyName = name;
        this.headTexture = texture;
    }
}
