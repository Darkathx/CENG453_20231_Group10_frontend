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
    public List<City> cities;
    private int points;
    private Color playerColor;
    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());


    public Player(String name, ResourceCardDeck resourceCards, Color color) {
        this.name = name;
        this.resourceCards = resourceCards;
        this.resourceCards.addResource(ResourceType.BRICK, 3);
        this.resourceCards.addResource(ResourceType.LUMBER, 3);
        this.resourceCards.addResource(ResourceType.GRAIN, 1);
        this.resourceCards.addResource(ResourceType.WOOL, 1);
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

    public void decrPoints(int points) {
        this.points -= points;
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
                    resourceCards.addResource(tile.getResourceType(),1);
                }
            }
        }
        if(cities != null){
            for(City city : cities) {
                for(Tile tile : city.getLocation().getAdjacentTiles()) {
                    if(tile.getNumber() == diceRoll) {
                        resourceCards.addResource(tile.getResourceType(), 2);
                    }

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

    public List<City> getCities() {
        return cities;
    }

    public boolean canBuildSettlement() {
        // Example resource check; details depend on ResourceCardDeck implementation
        return resourceCards.canDeduct(Settlement.COST);
    }

    public boolean buildSettlement(Settlement settlement) {
        if (settlement.getLocation().isAvailable() && canBuildSettlement()) {
            settlements.add(settlement);
            resourceCards.deduct(Settlement.COST);
            this.addPoints(1);
            return true;
        }
        return false;
    }

    public void addSettlement(Settlement settlement) {
        settlements.add(settlement);
    }

    public boolean canBuildRoad() {
        // Example resource check; details depend on ResourceCardDeck implementation
        return resourceCards.canDeduct(Road.COST);
    }
    public boolean canUpgradeToCity() {
        // Example resource check; details depend on ResourceCardDeck implementation
        return resourceCards.canDeduct(City.COST);
    }

    public boolean buildRoad(Road road) {
        if (road.getLocation().isAvailable() && canBuildRoad()) {
            roads.add(road);
            resourceCards.deduct(Road.COST);
            return true;
        }
        return false;
    }

    public boolean isAI() {
        // Example check for an AI player, modify as needed
        return this.name.startsWith("AI");
    }
    public boolean upgradeToCity(Settlement settlement, City city){
        Vertex vertex = settlement.getLocation();
        if(canUpgradeToCity() && vertex.hasSettlement() && !vertex.hasCity()){
            vertex.removeSettlement();
            vertex.buildCity(city);
            resourceCards.deduct(City.COST);
            this.addPoints(1);
            return true;
        }
        return false;
    }

    public boolean costlessUpgrade(Settlement settlement, City city) {
        Vertex vertex = settlement.getLocation();
        vertex.removeSettlement();
        vertex.buildCity(city);
        return true;
    }
    public void addResource(ResourceType resourceType, int amount) {
        this.resourceCards.addResource(resourceType, amount);
    }
}