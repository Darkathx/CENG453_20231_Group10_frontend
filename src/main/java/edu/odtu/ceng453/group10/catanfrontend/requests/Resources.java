package edu.odtu.ceng453.group10.catanfrontend.requests;

public record Resources(
    String id,
    String gameStateId,
    int brick,
    int lumber,
    int wool,
    int grain,
    int ore
) {

}
