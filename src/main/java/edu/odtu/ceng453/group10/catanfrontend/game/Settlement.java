package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.Map;

public class Settlement {
    private Vertex location;
    private boolean isCity = false;

    public static final Map<ResourceType, Integer> COST = Map.of(
            ResourceType.BRICK, 1,
            ResourceType.LUMBER, 1,
            ResourceType.GRAIN, 1,
            ResourceType.WOOL, 1
    );
    public static final Map<ResourceType, Integer> COST_CITY = Map.of(
            ResourceType.GRAIN, 2,
            ResourceType.ORE, 3
    );

    public Settlement(Vertex location) {
        this.location = location;
        this.isCity = false;
    }

    public Vertex getLocation(){
        return this.location;
    }

    public void setLocation(Vertex location){
        this.location = location;
    }
    public void upgradeToCity(){
        this.isCity = true;
    }
}
