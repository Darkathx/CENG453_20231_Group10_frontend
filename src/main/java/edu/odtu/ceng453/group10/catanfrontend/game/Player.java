package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Player {
    private String name;
    private ResourceCardDeck resourceCards;
    public List<Settlement> settlements;
    public List<Road> roads;

    public Player(String name, ResourceCardDeck resourceCards) {
        this.name = name;
        this.resourceCards = resourceCards;
        this.settlements = new ArrayList<>();
        this.roads = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceCardDeck getResources() {
        return resourceCards;
    }

    public void setResources(ResourceCardDeck resources) {
        this.resourceCards = resources;
    }

    public List<Settlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public boolean canBuildSettlement() {
        // Example resource check; details depend on ResourceCardDeck implementation
        return resourceCards.canDeduct(Settlement.COST);
    }

    public boolean buildSettlement(Vertex vertex) {
        if (vertex.isAvailable() && canBuildSettlement()) {
            Settlement settlement = new Settlement(vertex);
            settlements.add(settlement);
            resourceCards.deduct(Settlement.COST);
            return true;
        }
        return false;
    }

    public boolean canBuildRoad() {
        // Example resource check; details depend on ResourceCardDeck implementation
        return resourceCards.canDeduct(Road.COST);
    }

    public boolean buildRoad(Edge edge) {
        if (edge.isAvailable() && canBuildRoad()) {
            Road road = new Road(edge);
            roads.add(road);
            resourceCards.deduct(Road.COST);
            return true;
        }
        return false;
    }
    public void deductResourcesForRoad() {
        if (canBuildRoad()) {
            resourceCards.deduct(Road.COST);
        }
    }
    public void deductResourcesForSettlement() {
        if (canBuildSettlement()) {
            resourceCards.deduct(Settlement.COST);
        }
    }
    public void addRoad(Road road) {
        roads.add(road);
    }

    public void addSettlement(Settlement settlement) {
        settlements.add(settlement);
    }
}