package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Edge {
    private Vertex vertex1;
    private Vertex vertex2;
    private Player owner;
    private Road road;

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.owner = null;
        this.road = null;
    }

    // Getters and setters
    public Vertex getVertex1() {
        return vertex1;
    }

    public void setVertex1(Vertex vertex1) {
        this.vertex1 = vertex1;
    }
    public boolean involves(Vertex vertex) {
        return vertex.equals(vertex1) || vertex.equals(vertex2);
    }
    public Vertex getVertex2() {
        return vertex2;
    }

    public void setVertex2(Vertex vertex2) {
        this.vertex2 = vertex2;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public boolean isAvailable() {
        return road == null;
    }

    public boolean isConnectedTo(Vertex vertex) {
        return vertex.equals(vertex1) || vertex.equals(vertex2);
    }
    public boolean buildRoad(Road road){
        if(this.road == null){
            this.road = road;
            return true;
        }
        return false;
    }
    public List<Tile> getAdjacentTiles() {
        List<Tile> adjacentTiles = new ArrayList<>();
        if (vertex1 != null) {
            adjacentTiles.addAll(vertex1.getAdjacentTiles());
        }
        if (vertex2 != null) {
            adjacentTiles.addAll(vertex2.getAdjacentTiles());
        }
        return adjacentTiles;
    }

    public Set<Edge> getAdjacentEdges() {
        Set<Edge> adjacentEdges = new HashSet<>();
        if (vertex1 != null) {
            adjacentEdges.addAll(vertex1.getConnectedEdges());
        }
        if (vertex2 != null) {
            adjacentEdges.addAll(vertex2.getConnectedEdges());
        }
        return adjacentEdges;
    }
}
