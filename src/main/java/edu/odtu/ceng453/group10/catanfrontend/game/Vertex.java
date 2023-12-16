package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.GameController;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Vertex {
    private static final Logger LOGGER = Logger.getLogger(Vertex.class.getName());

    private Point2D position;
    private Settlement settlement;
    private Player owner;
    private String key;
    private List<Edge> connectedEdges;

    public Vertex(Point2D position, int row, int col, int vertexIndex) {
        this.position = position;
        this.settlement = null;
        this.owner = null;
        this.connectedEdges = new ArrayList<>();
        this.key = generateKey(row, col, vertexIndex);
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
    private String generateKey(int row, int col, int vertexIndex) {
        return row + "-" + col + "-" + vertexIndex;
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
        return settlement == null;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public List<Edge> getConnectedEdges() {
        return connectedEdges;
    }

    public boolean buildSettlement(Settlement settlement){
        if(this.settlement == null){
            LOGGER.info("Build settlement" + this);
            this.settlement = settlement;
            return true;
        }
        return false;
    }
}
