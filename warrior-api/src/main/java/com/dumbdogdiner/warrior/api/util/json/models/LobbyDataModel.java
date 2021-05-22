package com.dumbdogdiner.warrior.api.util.json.models;

import com.dumbdogdiner.warrior.api.util.json.models.LocationModel;
import lombok.Getter;

public class LobbyDataModel {

    @Getter
    private LocationModel spawn;

    public LobbyDataModel(LocationModel spawn) {
        this.spawn = spawn;
    }

}
