package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.GameController;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Vertex {
    private static final Logger LOGGER = Logger.getLogger(Vertex.class.getName());

    private Point2D position;
    private Settlement settlement;
    private City city;
    private Player owner;
    private String key;
    private List<Edge> connectedEdges;
    private Set<Tile> adjacentTiles;

    public Vertex(Point2D position, int row, int col, int vertexIndex) {
        this.position = position;
        this.settlement = null;
        this.owner = null;
        this.connectedEdges = new ArrayList<>();
        this.adjacentTiles = new HashSet<>();
    }


    // Method to add a connected edge
    public void addConnectedEdge(Edge edge) {
        if (!connectedEdges.contains(edge)) {
            connectedEdges.add(edge);
        }
    }

    // Getters and setters
    public Point2D getPosition() {
        return position;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isAvailable() {
        return (settlement == null || city == null);
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public List<Edge> getConnectedEdges() {
        return connectedEdges;
    }

    public void buildSettlement(Settlement settlement){
        if(this.settlement == null){
            this.settlement = settlement;
        }
    }

    public void removeSettlement() {
        this.settlement = null;
    }

    public void buildCity(City city){
        if(this.city == null){
            this.city = city;
        }
    }

    public City getCity() {
        return city;
    }

    public void addAdjacentTile(Tile tile) {
        adjacentTiles.add(tile);
    }
    public Set<Tile> getAdjacentTiles() {
        LOGGER.info("get adjacent tile: " + adjacentTiles);
        return this.adjacentTiles;
    }

    public boolean hasSettlement() {
        return this.settlement != null;
    }

    public boolean hasCity() {
        return this.city != null;
    }
}
