package edu.odtu.ceng453.group10.catanfrontend.requests;

import java.util.List;

public record GameStateResponse(
    String id,
    String gameId,
    int playerTurn,
    int dice1,
    int dice2,
    List<Resources> resources,
    List<Buildings> buildings
) {

}
