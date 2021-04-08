package com.dumbdogdiner.warrior.api.models;

import lombok.Getter;

public class LobbyDataModel {

    @Getter
    private LocationModel spawn;

    public LobbyDataModel(LocationModel spawn) {
        this.spawn = spawn;
    }

}
