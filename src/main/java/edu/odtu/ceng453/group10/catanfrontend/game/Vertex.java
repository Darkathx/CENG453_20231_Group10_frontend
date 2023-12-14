package edu.odtu.ceng453.group10.catanfrontend.game;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private Point2D position;
    private Settlement settlement;
    private Player owner;
    private List<Edge> connectedEdges;

    public Vertex(Point2D position) {
        this.position = position;
        this.settlement = null;
        this.owner = null;
        this.connectedEdges = new ArrayList<>();
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

    // Additional methods as necessary...
}
