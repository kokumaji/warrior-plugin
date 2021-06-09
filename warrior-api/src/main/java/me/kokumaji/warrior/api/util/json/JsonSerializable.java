package me.kokumaji.warrior.api.util.json;

public interface JsonSerializable {

    JSONModel toJson();

    String getFilePath();

}