package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.Map;

public class Road {
    private Edge edge;
    public static final Map<ResourceType, Integer> COST = Map.of(
            ResourceType.BRICK, 1,
            ResourceType.LUMBER, 1
    );
    public Road(Edge edge) {
        this.edge = edge;
    }

    public Edge getLocation(){
        return this.edge;
    }

    public void setLocation(Edge edge){
        this.edge = edge;
    }

}