package edu.odtu.ceng453.group10.catanfrontend.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Tile {
    private TileType tileType;
    private ResourceType resourceType; // This can be null for DESERT
    private Integer number; // Use Integer to allow null for desert
    private Polygon hexagon;

    public Tile(TileType tileType, ResourceType resourceType, Integer number) {
        this.tileType = tileType;
        this.resourceType = resourceType; // This can be null if tileType is DESERT
        this.number = number;
        this.hexagon = createHexagon();
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
            case DESERT: return Color.SANDYBROWN;
            case HILL: return Color.DARKRED;
            case MOUNTAIN: return Color.DARKGRAY;
            case FOREST: return Color.FORESTGREEN;
            case FIELD: return Color.GOLD;
            case PASTURE: return Color.PALEGREEN;
            default: return Color.WHITE;
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
}
