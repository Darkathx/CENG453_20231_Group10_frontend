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
import java.util.List;

public class BoardView extends Pane {
    private static final double RADIUS = 50;
    private static final double APOTHEM = RADIUS * Math.sqrt(3) / 2;
    private static final double GAP = 20;
    private static final int[] rowLengths = {3, 4, 5, 4, 3};
    private static final double VERTEX_RADIUS = 15;
    private static final double EDGE_STROKE_WIDTH = 10;
    private static final double SETTLEMENT_SIZE = 10;
    private static final double ROAD_WIDTH = 5;

    private GameController gameController;

    public BoardView(GameState gameState, GameController gameController) {
        this.gameController = gameController;
        drawBoard(gameState);
        drawInteractiveComponents(gameState);
    }

    private void drawBoard(GameState state) {
        List<Tile> tiles = state.getBoard().getTiles();
        for (Tile tile : tiles) {
            this.getChildren().add(tile.getHexagon());
            if (tile.getNumber() != null) {
                Text numberText = createNumberText(tile);
                this.getChildren().add(numberText);
            }
        }
    }

    private Text createNumberText(Tile tile) {
        Point2D center = tile.getCenterPosition();
        Text numberText = new Text(tile.getNumber().toString());
        numberText.setTextAlignment(TextAlignment.CENTER);
        numberText.setTranslateX(center.getX());
        numberText.setTranslateY(center.getY());
        return numberText;
    }

    public void drawInteractiveComponents(GameState gameState) {
        // Draw available settlements and roads
        gameState.getBoard().getAvailableVertices().forEach(vertex -> drawVertex(vertex));
        gameState.getBoard().getTiles().forEach(tile -> tile.getEdges().forEach(this::drawEdge));
    }

    private void drawVertex(Vertex vertex) {
        Circle vertexGraphic = new Circle(vertex.getPosition().getX(), vertex.getPosition().getY(), VERTEX_RADIUS, Color.TRANSPARENT);
        vertexGraphic.setStroke(Color.BLACK);
        vertexGraphic.setOnMouseClicked(event -> handleVertexClick(vertex));
        this.getChildren().add(vertexGraphic);
    }

    private void drawEdge(Edge edge) {
        Line edgeGraphic = new Line(edge.getVertex1().getPosition().getX(), edge.getVertex1().getPosition().getY(),
                edge.getVertex2().getPosition().getX(), edge.getVertex2().getPosition().getY());
        edgeGraphic.setStroke(Color.TRANSPARENT);
        edgeGraphic.setStrokeWidth(EDGE_STROKE_WIDTH);
        edgeGraphic.setOnMouseClicked(event -> handleEdgeClick(edge));
        this.getChildren().add(edgeGraphic);
    }

    private void handleVertexClick(Vertex vertex) {
        Player currentPlayer = gameController.getCurrentPlayer();
        if (vertex.isAvailable() && currentPlayer.canBuildSettlement()) {
            gameController.buildSettlement(currentPlayer, vertex);
            drawSettlement(vertex, currentPlayer);
        }
    }

    private void handleEdgeClick(Edge edge) {
        Player currentPlayer = gameController.getCurrentPlayer();
        if (edge.isAvailable() && currentPlayer.canBuildRoad()) {
            gameController.buildRoad(currentPlayer, edge);
            drawRoad(edge, currentPlayer);
        }
    }

    private void drawSettlement(Vertex vertex, Player player) {
        Circle settlementGraphic = new Circle(vertex.getPosition().getX(), vertex.getPosition().getY(), SETTLEMENT_SIZE, player.getPlayerColor());
        this.getChildren().add(settlementGraphic);
    }

    private void drawRoad(Edge edge, Player player) {
        Line roadGraphic = new Line(edge.getVertex1().getPosition().getX(), edge.getVertex1().getPosition().getY(),
                edge.getVertex2().getPosition().getX(), edge.getVertex2().getPosition().getY());
        roadGraphic.setStroke(player.getPlayerColor());
        roadGraphic.setStrokeWidth(ROAD_WIDTH);
        this.getChildren().add(roadGraphic);
    }

    public void updateBoardView(GameState gameState) {
        this.getChildren().clear();
        drawBoard(gameState);
        drawInteractiveComponents(gameState);
    }
}
