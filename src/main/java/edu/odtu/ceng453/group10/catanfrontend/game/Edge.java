package edu.odtu.ceng453.group10.catanfrontend.game;

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
}