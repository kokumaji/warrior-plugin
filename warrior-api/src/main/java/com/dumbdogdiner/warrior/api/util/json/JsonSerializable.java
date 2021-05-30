package com.dumbdogdiner.warrior.api.util.json;

public interface JsonSerializable {

    JSONModel toJson();

    String getFilePath();

}