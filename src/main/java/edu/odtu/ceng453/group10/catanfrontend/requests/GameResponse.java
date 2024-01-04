package edu.odtu.ceng453.group10.catanfrontend.requests;

import java.util.List;

public record GameResponse(
    String id,
    String stateId,
    List<String> players,
    int playerCount
) {}
