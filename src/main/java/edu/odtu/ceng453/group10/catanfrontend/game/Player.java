package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.paint.Color;
import java.util.logging.Logger;

public class Player {
    private String name;
    private ResourceCardDeck resourceCards;
    public List<Settlement> settlements;
    public List<Road> roads;
    private int points;
    private Color playerColor;
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());


    public Player(String name, ResourceCardDeck resourceCards, Color color) {
        this.name = name;
        this.resourceCards = resourceCards;
        this.settlements = new ArrayList<>();
        this.roads = new ArrayList<>();
        this.points = 0;
        this.playerColor = color;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }
    public Color getPlayerColor() {
        return playerColor;
    }

    public int addPoints(int points) {
        this.points += points;
        return this.points;
    }

    public ResourceCardDeck getResources() {
        return resourceCards;
    }

    public void setResources(ResourceCardDeck resources) {
        this.resourceCards = resources;
    }
    public void addResourceForSettlement(Settlement settlement){
        Set<Tile> adjacentTiles = settlement.getLocation().getAdjacentTiles();
        for(Tile tile: adjacentTiles){
            if(tile.getResourceType() != null){
                resourceCards.addResource(tile.getResourceType(),1);
            }
        }
    }

    public void addResourceForCity(Settlement settlement){ // TODO: change settlement with city
        Set<Tile> adjacentTiles = settlement.getLocation().getAdjacentTiles();
        for(Tile tile: adjacentTiles){
            if(tile.getResourceType() != null){
                resourceCards.addResource(tile.getResourceType(),2);
            }
        }
    }

    public void addResourcesAccordingToDiceRoll(int diceRoll){
        if(diceRoll == 7){
            return;
        }
        for(Settlement settlement: settlements){
            for(Tile tile: settlement.getLocation().getAdjacentTiles()){
                if(tile.getNumber() == diceRoll){
                    if(settlement.isCity())
                        resourceCards.addResource(tile.getResourceType(),2);
                    else
                        resourceCards.addResource(tile.getResourceType(),1);
                }
            }
        }
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

    public boolean buildSettlement(Settlement settlement) {
        if (settlement.getLocation().isAvailable() && canBuildSettlement()) {
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

    public boolean buildRoad(Road road) {
        if (road.getLocation().isAvailable() && canBuildRoad()) {
            roads.add(road);
            resourceCards.deduct(Road.COST);
            return true;
        }
        return false;
    }

    public boolean buildCity(Settlement settlement) {
        if (settlement.getLocation().isAvailable() && canBuildCity()) {
            settlement.setCity();
            resourceCards.deduct(Settlement.CITY_COST);
            return true;
        }
        return false;
    }

    public boolean canBuildCity() {
        // Example resource check; details depend on ResourceCardDeck implementation
        return resourceCards.canDeduct(Settlement.CITY_COST);
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
}