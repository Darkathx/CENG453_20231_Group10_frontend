package edu.odtu.ceng453.group10.catanfrontend;

import javafx.scene.chart.LineChart;
import org.springframework.stereotype.Component;
import edu.odtu.ceng453.group10.catanfrontend.game.*;

import java.util.ArrayList;
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
  }

  public void setupInitialBoard() {
    LOGGER.info("Setting up the initial board.");

    for (Player player : gameState.getPlayers()) {
      Vertex settlementVertex = getRandomAvailableVertex();
      Settlement settlement = new Settlement(settlementVertex);
      settlementVertex.buildSettlement(settlement);
      settlementVertex.setOwner(player);// Directly build without checking resources
      player.getSettlements().add(settlement);
      player.addResourceForSettlement(settlement);

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
      player.buildSettlement(settlement);
      vertex.buildSettlement(settlement);
      vertex.setOwner(player);
      return true;
    }
    return false;
  }

  public boolean buildCity(Player player, Vertex vertex) {
    if (!vertex.isAvailable() && vertex.getOwner() == player) {
      return player.upgradeToCity(vertex.getSettlement());
    }
    return false;
  }

  public boolean buildRoad(Player player, Edge edge) {
    if (edge.isAvailable()) {
      Road road = new Road(edge);
      player.buildRoad(road);
      edge.buildRoad(road);
      edge.setOwner(player);
      return true;
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
  public boolean upgradeSettlementToCity(Player player, Settlement settlement) {
    return player.upgradeToCity(settlement);
  }

  public void performCPUTurn() {
    Player currentPlayer = getCurrentPlayer();
    if (!currentPlayer.isAI()) {
      return;
    }

    LOGGER.info("CPU player's turn: " + currentPlayer.getName());

    // CPU logic goes here
    // 1. Roll the dice and collect resources
    int rolledDice = gameState.getLastDiceRoll()[0]+gameState.getLastDiceRoll()[1];
    distributeResources(rolledDice);

    // 2. Check and perform actions in order of priority
    if (currentPlayer.canBuildRoad()) {
      // Build road at a random available position
      Edge randomEdge = getRandomAvailableEdgeForPlayer(currentPlayer);
      buildRoad(currentPlayer, randomEdge);
    }

    if (currentPlayer.canBuildSettlement()) {
      // Build settlement at a valid location
      Vertex randomVertex = getRandomAvailableVertexForPlayer(currentPlayer);
      buildSettlement(currentPlayer, randomVertex);
    }

    if (currentPlayer.canUpgradeToCity()) {
      // Upgrade a random settlement to a city
      Settlement settlementToUpgrade = getRandomSettlement(currentPlayer);
      upgradeSettlementToCity(currentPlayer, settlementToUpgrade);
    }

    // End the CPU's turn
    nextTurn();
    performCPUTurn();
  }

  private int rollDice(Player player) {
    // Simulate dice roll - this is just a placeholder
    return random.nextInt(6) + 1 + random.nextInt(6) + 1;
  }
  public void distributeResources(int diceRoll) {
    List<Tile> tiles = gameState.getBoard().getTiles();
    for (Tile tile : tiles) {
      LOGGER.info("tile number: " + tile.getNumber());
      if (tile.getNumber() != null && tile.getNumber() == diceRoll && tile.getResourceType() != null) {
        distributeResourcesFromTile(tile);
      }
    }
  }

  private void distributeResourcesFromTile(Tile tile) {
    List<Vertex> vertices = tile.getVertices();
    for (Vertex vertex : vertices) {
      if (vertex.hasSettlement() || vertex.hasCity()) {
        Player owner = vertex.getOwner();
        if (owner != null) {
          ResourceType resource = tile.getResourceType();
          int amount = vertex.hasCity() ? 2 : 1; // Assuming cities yield double resources
          owner.getResources().addResource(resource, amount);
        }
      }
    }
  }


  private Edge getRandomAvailableEdgeForPlayer(Player player) {
    List<Edge> availableEdges = new ArrayList<>();
    for (Road road : player.getRoads()) {
      for (Edge edge : road.getLocation().getVertex1().getConnectedEdges()) {
        if (edge.isAvailable()) {
          availableEdges.add(edge);
        }
      }
    }
    if (!availableEdges.isEmpty()) {
      return availableEdges.get(random.nextInt(availableEdges.size()));
    }
    return null; // Or handle this case appropriately
  }


  private Vertex getRandomAvailableVertexForPlayer(Player player) {
    List<Vertex> availableVertices = new ArrayList<>();
    for (Road road : player.getRoads()) {
      addVertexIfSuitable(road.getLocation().getVertex1(), availableVertices);
      addVertexIfSuitable(road.getLocation().getVertex2(), availableVertices);
    }
    if (!availableVertices.isEmpty()) {
      return availableVertices.get(random.nextInt(availableVertices.size()));
    }
    return null; // Or handle this case appropriately
  }

  private void addVertexIfSuitable(Vertex vertex, List<Vertex> availableVertices) {
    if (vertex.isAvailable()) {
      availableVertices.add(vertex);
    }
  }


  private Settlement getRandomSettlement(Player player) {
    List<Settlement> settlements = player.getSettlements();
    if (!settlements.isEmpty()) {
      return settlements.get(random.nextInt(settlements.size()));
    }
    return null; // Or handle this case appropriately
  }



}
