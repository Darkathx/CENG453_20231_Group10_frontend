package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.Map;

public class City {
    private Vertex location;

    public City(Vertex location) {
        this.location = location;
    }
    public static final Map<ResourceType, Integer> COST = Map.of(
            ResourceType.GRAIN, 2,
            ResourceType.ORE, 3
    );


    public Vertex getLocation() {
        return this.location;
    }

    public void setLocation(Vertex location) {
        this.location = location;
    }
}
