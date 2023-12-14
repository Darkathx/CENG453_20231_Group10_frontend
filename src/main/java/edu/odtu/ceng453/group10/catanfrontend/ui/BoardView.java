package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.game.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Point2D;
import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.GameController;

public class BoardView extends Pane {
    private static final double RADIUS = 50; // Tile radius
    private static final double APOTHEM = RADIUS * Math.sqrt(3) / 2; // Height from center to side
    private static final double GAP = 20; // Gap between tiles
    private static final int[] rowLengths = {3, 4, 5, 4, 3}; // Hexagons per row
    private static final double VERTEX_RADIUS = 15; // Clickable area radius for vertices
    private static final double EDGE_STROKE_WIDTH = 10; // Clickable area width for edges
    private static final double SETTLEMENT_SIZE = 10; // Visual size for settlements
    private static final double ROAD_WIDTH = 5;

    GameController gameController;

    public BoardView(Tile[] tiles) {
        // The full width needed to display the widest row with gaps
        double maxWidth = 5 * (RADIUS * 3 / 2) + (4 * GAP); // 4 gaps for 5 hexagons

        // Calculate the starting Y position to center the board vertically with gaps
        double totalHeight = (5 * (APOTHEM * 2)); // 4 gaps for 5 rows

        double startY = (Settings.getHeight() - totalHeight) / 2;

        int tileIndex = 0;
        for (int row = 0; row < rowLengths.length; row++) {
            // Calculate the width of the current row with gaps
            double currentRowWidth = rowLengths[row] * (RADIUS * 3 / 2) + ((rowLengths[row] - 1) * GAP);

            // Calculate the starting X position to center the current row with gaps
            double startX = (Settings.getWidth() - maxWidth) / 2 + (maxWidth - currentRowWidth) / 2;

            for (int col = 0; col < rowLengths[row]; col++) {
                // Calculate the position for each tile with gap
                double x = startX + col * (RADIUS * 3 / 2 + GAP);
                double y = startY + row * (APOTHEM * 2);

                Tile tile = tiles[tileIndex++];
                tile.getHexagon().setTranslateX(x);
                tile.getHexagon().setTranslateY(y);

                // Add the number text if available
                if (tile.getNumber() != null) {
                    Text numberText = new Text(tile.getNumber().toString());
                    numberText.setTextAlignment(TextAlignment.CENTER);
                    numberText.setTranslateX(x);
                    numberText.setTranslateY(y + APOTHEM / 2); // Adjust for the center of the hexagon
                    this.getChildren().add(numberText);
                }

                this.getChildren().add(tile.getHexagon());
            }
        }
    }
    // Inside the BoardView class

    // Method to draw a visual representation of a settlement on a vertex
    private void drawSettlement(Vertex vertex) {
        Circle settlementGraphic = new Circle(vertex.getPosition().getX(), vertex.getPosition().getY(), SETTLEMENT_SIZE);
        settlementGraphic.setFill(Color.WHITE); // Change color as needed
        this.getChildren().add(settlementGraphic); // Add to the pane
    }

    // Method to draw a visual representation of a road on an edge
    private void drawRoad(Edge edge) {
        Line roadGraphic = new Line();
        roadGraphic.setStartX(edge.getVertex1().getPosition().getX());
        roadGraphic.setStartY(edge.getVertex1().getPosition().getY());
        roadGraphic.setEndX(edge.getVertex2().getPosition().getX());
        roadGraphic.setEndY(edge.getVertex2().getPosition().getY());
        roadGraphic.setStroke(Color.GRAY); // Change color as needed
        roadGraphic.setStrokeWidth(ROAD_WIDTH);
        this.getChildren().add(roadGraphic); // Add to the pane
    }

    // Call this method to make vertices and edges interactive and drawable
    public void drawInteractiveComponents() {
        // Assuming vertices and edges are stored in lists within BoardView
        for (Player player: gameController.players){
            for (Settlement settlement: player.settlements) {
                Circle vertexGraphic = new Circle(settlement.getLocation().getPosition().getX(), settlement.getLocation().getPosition().getY(), VERTEX_RADIUS);
                vertexGraphic.setFill(Color.TRANSPARENT);
                vertexGraphic.setStroke(Color.BLACK); // Set the stroke color for visibility
                vertexGraphic.setOnMouseClicked(event -> handleVertexClick(player, settlement.getLocation()));
                this.getChildren().add(vertexGraphic);
            }

            for (Road road : player.roads) {
                Line edgeGraphic = new Line();
                edgeGraphic.setStartX(road.getLocation().getVertex1().getPosition().getX());
                edgeGraphic.setStartY(road.getLocation().getVertex1().getPosition().getY());
                edgeGraphic.setEndX(road.getLocation().getVertex2().getPosition().getX());
                edgeGraphic.setEndY(road.getLocation().getVertex2().getPosition().getY());
                edgeGraphic.setStroke(Color.TRANSPARENT); // Initially make the edge invisible
                edgeGraphic.setStrokeWidth(EDGE_STROKE_WIDTH);
                edgeGraphic.setOnMouseClicked(event -> handleEdgeClick(player, road.getLocation()));
                this.getChildren().add(edgeGraphic);
            }
        }

    }

    private void handleVertexClick(Player player, Vertex vertex) {
        if (vertex.isAvailable()) {
            gameController.buildSettlement(player, vertex);
            // Update UI to show the new settlement
            drawSettlement(vertex);
        }
    }

    private void handleEdgeClick(Player player, Edge edge) {
        if (edge.isAvailable()) {
            gameController.buildRoad(player, edge);
            // Update UI to show the new road
            drawRoad(edge);
        }
    }
}