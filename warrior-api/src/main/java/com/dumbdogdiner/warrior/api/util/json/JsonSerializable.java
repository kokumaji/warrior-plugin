package com.dumbdogdiner.warrior.api.util.json;

public interface JsonSerializable {

    JsonModel toJson();

    String getFilePath();

}
