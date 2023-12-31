package edu.odtu.ceng453.group10.catanfrontend.requests;

public record Buildings(
    String id,
    String gameStateId,
    int user,
    BuildingType type,
    String vertexKey1,
    String vertexKey2
) {

}
