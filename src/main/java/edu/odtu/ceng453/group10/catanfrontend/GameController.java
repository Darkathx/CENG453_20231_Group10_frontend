package edu.odtu.ceng453.group10.catanfrontend;

import javafx.scene.chart.LineChart;
import org.springframework.stereotype.Component;
import edu.odtu.ceng453.group10.catanfrontend.game.*;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

@Component
public class GameController {
  private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());

  private GameState gameState;
  private Random random = new Random();

  public GameController(GameState gameState) {
    this.gameState = gameState;
    setupInitialBoard();
  }

  public void setupInitialBoard() {
    LOGGER.info("Setting up the initial board.");

    for (Player player : gameState.getPlayers()) {
      Vertex settlementVertex = getRandomAvailableVertex();
      Settlement settlement = new Settlement(settlementVertex);
      settlementVertex.buildSettlement(settlement); // Directly build without checking resources
      player.getSettlements().add(settlement);

      // Select a random edge connected to the settlement vertex for the initial road
      Edge roadEdge = getRandomAvailableEdge(settlementVertex);
      Road road = new Road(roadEdge);
      roadEdge.buildRoad(road); // Directly build without checking resources
      player.getRoads().add(road);
    }
  }


  public boolean buildSettlement(Player player, Vertex vertex) {
    if (vertex.isAvailable()) {
      Settlement settlement = new Settlement(vertex);
      boolean added = gameState.getBoard().addSettlement(vertex, settlement);
      if(added){
        player.buildSettlement(settlement);
      }
      return added;
    }
    return false;
  }

  public boolean buildRoad(Player player, Edge edge) {
    if (edge.isAvailable()) {
      Road road = new Road(edge);
      boolean added = gameState.getBoard().addRoad(edge, road);
      if(added){
        player.buildRoad(road);
      }
      return added;
    }
    return false;
  }

  private Vertex getRandomAvailableVertex() {
    List<Vertex> availableVertices = gameState.getBoard().getAvailableVertices();
    return availableVertices.get(random.nextInt(availableVertices.size()));
  }

  private Edge getRandomAvailableEdge(Vertex vertex) {
    List<Edge> connectedEdges = gameState.getBoard().getConnectedAvailableEdges(vertex);
    return connectedEdges.get(random.nextInt(connectedEdges.size()));
  }
  public Player getCurrentPlayer() {
    return gameState.getPlayers().get(gameState.getCurrentPlayerIndex());
  }
  public void nextTurn() {
    int currentPlayerIndex = gameState.getCurrentPlayerIndex();
    currentPlayerIndex = (currentPlayerIndex + 1) % gameState.getPlayers().size();
    gameState.setCurrentPlayerIndex(currentPlayerIndex);
  }
}
