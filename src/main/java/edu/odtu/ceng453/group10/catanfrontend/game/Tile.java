package edu.odtu.ceng453.group10.catanfrontend.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;

import java.util.List;

public class Tile {
    private TileType tileType;
    private ResourceType resourceType; // This can be null for DESERT
    private Integer number; // Use Integer to allow null for desert
    private Polygon hexagon;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private int row;
    private int col;
    private static final double GAP = 20;
    static final double RADIUS = 50; // radius of the hexagon
    static final double APOTHEM = RADIUS * Math.sqrt(3) / 2; // distance from center to mid-edge


    public Point2D getCenterPosition() {
        // Calculate the x and y position based on row and column
        double x = col * (RADIUS * 1.5 + GAP);
        double y = row * (2 * APOTHEM);

        // Offset every other row
        if (row % 2 != 0) {
            x += RADIUS * 3 / 4;
        }

        return new Point2D(x, y);
    }

    public Tile(TileType tileType, ResourceType resourceType, Integer number, int row, int col) {
        this.tileType = tileType;
        this.resourceType = resourceType; // This can be null if tileType is DESERT
        this.number = number;
        this.hexagon = createHexagon();
        this.row = row;
        this.col = col;
    }

    private Polygon createHexagon() {
        Polygon hex = new Polygon();
        double radius = 50; // example size
        for (int i = 0; i < 6; i++) {
            hex.getPoints().addAll(
                    radius * Math.cos(i * 2 * Math.PI / 6 + Math.PI / 6) + radius,
                    radius * Math.sin(i * 2 * Math.PI / 6 + Math.PI / 6) + radius
            );

        }
        hex.setFill(getColorForTileType(tileType));
        return hex;
    }

    private Color getColorForTileType(TileType type) {
        switch (type) {
            case DESERT:
                return Color.SANDYBROWN;
            case HILL:
                return Color.DARKRED;
            case MOUNTAIN:
                return Color.DARKGRAY;
            case FOREST:
                return Color.FORESTGREEN;
            case FIELD:
                return Color.GOLD;
            case PASTURE:
                return Color.PALEGREEN;
            default:
                return Color.WHITE;
        }
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Polygon getHexagon() {
        return hexagon;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }
    public int setRow(int row){
        return this.row = row;
    }
    public int setCol(int col){
        return this.col = col;
    }
}
