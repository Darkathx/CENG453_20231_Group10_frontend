package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.game.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Point2D;
import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.GameController;
import java.util.List;

public class BoardView extends Pane {
    private static final int[] rowLengths = {3, 4, 5, 4, 3};
    private static final double VERTEX_RADIUS = 15;
    private static final double EDGE_STROKE_WIDTH = 10;

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
            if (tile.getTileType() != null) {
                Text tileText = createTileTypeText(tile);
                this.getChildren().add(tileText);
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
    private Text createTileTypeText(Tile tile) {
        Point2D center = tile.getCenterPosition();
        Text numberText = new Text(tile.getTileType().toString());
        numberText.setTextAlignment(TextAlignment.LEFT);
        numberText.setFont(Font.font(9));
        numberText.setTranslateX(center.getX()-tile.getRadius()/6);
        numberText.setTranslateY(center.getY()-tile.getRadius()/4);
        return numberText;
    }

    public void drawInteractiveComponents(GameState gameState) {
        // Draw available settlements and roads
        for(Player player : gameState.getPlayers()){
            drawEdge(player.getRoads().getFirst().getLocation(), player);
            drawVertex(player.getSettlements().getFirst().getLocation(), player);
        }
        for(Tile tile : gameState.getBoard().getTiles()){
            for(Vertex vertex : tile.getVertices()){
                if(vertex.isAvailable()){
                    drawClickableVertex(vertex);
                }
            }
            for(Edge edge : tile.getEdges()){
                if(edge.isAvailable()){
                    drawClickableEdge(edge);
                }
            }
        }
    }

    private void drawVertex(Vertex vertex, Player player) {
        Circle vertexGraphic = new Circle(vertex.getPosition().getX(), vertex.getPosition().getY(), VERTEX_RADIUS, player.getPlayerColor());
        vertexGraphic.setStroke(player.getPlayerColor());
        vertexGraphic.setOnMouseClicked(event -> handleVertexClick(vertex));
        this.getChildren().add(vertexGraphic);
    }

    private void drawEdge(Edge edge, Player player) {
        Line edgeGraphic = new Line(edge.getVertex1().getPosition().getX(), edge.getVertex1().getPosition().getY(),
                edge.getVertex2().getPosition().getX(), edge.getVertex2().getPosition().getY());
        edgeGraphic.setStroke(player.getPlayerColor());
        edgeGraphic.setStrokeWidth(EDGE_STROKE_WIDTH/2);
        edgeGraphic.setOnMouseClicked(event -> handleEdgeClick(edge));
        this.getChildren().add(edgeGraphic);
    }

    private void handleVertexClick(Vertex vertex) {
        Player currentPlayer = gameController.getCurrentPlayer();
        if (vertex.isAvailable() && currentPlayer.canBuildSettlement()) {
            gameController.buildSettlement(currentPlayer, vertex);
            drawVertex(vertex, currentPlayer);
        }
    }

    private void handleEdgeClick(Edge edge) {
        Player currentPlayer = gameController.getCurrentPlayer();
        if (edge.isAvailable() && currentPlayer.canBuildRoad()) {
            gameController.buildRoad(currentPlayer, edge);
            drawEdge(edge, currentPlayer);
        }
    }



    public void updateBoardView(GameState gameState) {
        this.getChildren().clear();
        drawBoard(gameState);
        drawInteractiveComponents(gameState);
    }
    private void drawClickableVertex(Vertex vertex) {
        Circle vertexArea = new Circle(vertex.getPosition().getX(), vertex.getPosition().getY(), VERTEX_RADIUS, Color.TRANSPARENT);
        vertexArea.setStroke(Color.GRAY); // For visibility
        vertexArea.setStrokeWidth(2);
        vertexArea.setOnMouseEntered(event -> vertexArea.setStroke(Color.BLUE)); // Change color on hover
        vertexArea.setOnMouseExited(event -> vertexArea.setStroke(Color.GRAY)); // Revert color when not hovering
        this.getChildren().add(vertexArea);
        vertexArea.setOnMouseClicked(event -> handleVertexClick(vertex));

    }
    private void drawClickableEdge(Edge edge) {


        // Draw a small square label at the center of the edge
        Point2D center = edge.getCenterVertex();
        Rectangle label = new Rectangle(center.getX() - 5, center.getY() - 5, 10, 10);
        label.setFill(Color.TRANSPARENT);
        label.setStroke(Color.BLACK);
        label.setStrokeWidth(2);
        label.setOnMouseEntered(event -> label.setStroke(Color.BLUE));
        label.setOnMouseExited(event -> label.setStroke(Color.GRAY));
        label.setOnMouseClicked(event -> handleEdgeClick(edge));
        this.getChildren().add(label);
    }


}
