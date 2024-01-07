package edu.odtu.ceng453.group10.catanfrontend;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import edu.odtu.ceng453.group10.catanfrontend.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

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
      Vertex settlementVertex = getAvailableVertex();
      this.buildSettlement(player, settlementVertex);
      player.addResource(ResourceType.BRICK, 1);
      player.addResource(ResourceType.LUMBER, 1);
      player.addResource(ResourceType.GRAIN, 1);
      player.addResource(ResourceType.WOOL, 1);

      // Select a random edge connected to the settlement vertex for the initial road
      Edge roadEdge = getRandomAvailableEdge(settlementVertex);
      this.buildRoad(player, roadEdge);
      player.addResource(ResourceType.BRICK, 1);
      player.addResource(ResourceType.LUMBER, 1);
    }
  }

  public void setupMultiBoard() {
    List<Player> players = gameState.getPlayers();
    for(int i = 0; i < 2; i++) {
      Player player = players.get(i);
      Vertex settlementVertex = getRandomAvailableVertexForPlayer(player);
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

  public void findLongestPath() {
    List<Player> players = gameState.getPlayers();
    int max = 0;
    Player maxPlayer = null;
    for(Player player : players) {
      List<Road> roads = player.getRoads();
      for(Road road : roads) {
        Set<Edge> edges = new HashSet<>();
        int length = findLongestPathHelper(player, edges, road.getLocation(), 0);
        if(length >= 5 && length > max) {
          max = length;
          maxPlayer = player;

        }
      }
    }
    if(maxPlayer != null) {
      Player former = gameState.getLongestPathPlayer();
      if(former != null) {
        former.decrPoints(max);
      }
      maxPlayer.addPoints(2);
      gameState.setLongestPathPlayer(maxPlayer);
    }
  }

  private int findLongestPathHelper(Player player, Set<Edge> path, Edge edge, int length) {
    if(edge.getOwner() == null || edge.getOwner() != player || path.contains(edge)) {
      return length;
    }
    path.add(edge);
    int maxLength = length + 1;
    Set<Edge> adjacentEdges = edge.getAdjacentEdges();
    for(Edge adjacentEdge : adjacentEdges) {
      int newLength = findLongestPathHelper(player, path, adjacentEdge, maxLength);
      if(newLength > maxLength) {
        maxLength = newLength;
      }
    }
    path.remove(edge);
    return maxLength;
  }


  public boolean buildSettlement(Player player, Vertex vertex) {
    if (vertex.isAvailable()) {
      Settlement settlement = new Settlement(vertex);
      vertex.buildSettlement(settlement);
      vertex.setOwner(player);// Directly build without checking resources
      //player.getSettlements().add(settlement);
      player.buildSettlement(settlement);
      player.addResourceForSettlement(settlement);

      return true;
    }
    return false;
  }

  public boolean buildRoad(Player player, Edge edge) {
    if (edge.isAvailable()) {
      Road road = new Road(edge);
      player.buildRoad(road);
      edge.buildRoad(road);
      edge.setOwner(player);
      //edge.buildRoad(road); // Directly build without checking resources
      //player.getRoads().add(road);
      return true;
    }
    return false;
  }
  public boolean upgradeSettlementToCity(Player player, Settlement settlement) {
    City city = new City(settlement.getLocation());
    player.upgradeToCity(settlement, city);
    settlement.getLocation().buildCity(city);
    return player.upgradeToCity(settlement, city);
  }
  private List<Vertex> getAllAvailableVertices() {
    List<Vertex> availableVertices = new ArrayList<>(gameState.getBoard().getAvailableVertices());
    List<Vertex> verticesToRemove = new ArrayList<>();

    for (Vertex vertex : availableVertices) {
      if(vertex.hasSettlement()){
        for (Edge edge : vertex.getConnectedEdges()) {
          verticesToRemove.add(edge.getVertex1());
          verticesToRemove.add(edge.getVertex2());
        }
      }
    }

    availableVertices.removeAll(verticesToRemove);
    availableVertices.removeIf(Vertex::hasSettlement);

    return availableVertices.isEmpty() ? null : availableVertices;
  }

  private Vertex getAvailableVertex() {
    List<Vertex> availableVertices = getAllAvailableVertices();
    return availableVertices == null ? null : availableVertices.get(random.nextInt(availableVertices.size()));
  }

  private Edge getRandomAvailableEdge(Vertex vertex) {
    List<Edge> connectedEdges = gameState.getBoard().getConnectedAvailableEdges(vertex);
    connectedEdges.removeIf(Edge -> !Edge.isAvailable());
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
      if (randomEdge != null)
        buildRoad(currentPlayer, randomEdge);
    }

    if (currentPlayer.canBuildSettlement()) {
      // Build settlement at a valid location
      Vertex randomVertex = getRandomAvailableVertexForPlayer(currentPlayer);
      if (randomVertex != null)
        buildSettlement(currentPlayer, randomVertex);
    }

    if (currentPlayer.canUpgradeToCity()) {
      // Upgrade a random settlement to a city
      Settlement settlementToUpgrade = getRandomSettlement(currentPlayer);
      upgradeSettlementToCity(currentPlayer, settlementToUpgrade);
    }

    // End the CPU's turn
    nextTurn();
  }

  private int rollDice(Player player) {
    // Simulate dice roll - this is just a placeholder
    return random.nextInt(6) + 1 + random.nextInt(6) + 1;
  }
  public void distributeResources(int diceRoll) {
    List<Tile> tiles = gameState.getBoard().getTiles();
    for (Tile tile : tiles) {
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


  public List<Edge> getAvailableEdgesForPlayer(Player player) {
    List<Edge> availableEdges = new ArrayList<>();
    for (Road road : player.getRoads()) {
      for (Edge edge : road.getLocation().getVertex1().getConnectedEdges()) {
        if (edge.isAvailable()) {
          availableEdges.add(edge);
        }
      }
      for (Edge edge : road.getLocation().getVertex2().getConnectedEdges()) {
        if (edge.isAvailable()) {
          availableEdges.add(edge);
        }
      }
    }
    for (Settlement settlement : player.getSettlements()) {
      for (Edge edge : settlement.getLocation().getConnectedEdges()) {
        if (edge.isAvailable()) {
          availableEdges.add(edge);
        }
      }
    }
    if (!availableEdges.isEmpty()) {
      return availableEdges;
    }
    return null; // Or handle this case appropriately
  }

  private Edge getRandomAvailableEdgeForPlayer(Player player){
    List<Edge> allAvailableEdges = getAvailableEdgesForPlayer(player);
    return allAvailableEdges == null ? null : allAvailableEdges.get(random.nextInt(allAvailableEdges.size()));
  }

  public List<Vertex> getAvailableVerticesForPlayer(Player player) {
    List<Vertex> allAvailableVertices = getAllAvailableVertices();
    List<Vertex> availableVertices = new ArrayList<>();
    for (Road road : player.getRoads()) {
        if(allAvailableVertices.contains(road.getLocation().getVertex1()))
          availableVertices.add(road.getLocation().getVertex1());
        if(allAvailableVertices.contains(road.getLocation().getVertex2()))
          availableVertices.add(road.getLocation().getVertex2());
      }

    if (!availableVertices.isEmpty()) {
      return availableVertices;
    }
    return null; // Or handle this case appropriately
  }
  private Vertex getRandomAvailableVertexForPlayer(Player player){
    List<Vertex> allAvailableVertices = getAvailableVerticesForPlayer(player);
    return allAvailableVertices == null ? null : allAvailableVertices.get(random.nextInt(allAvailableVertices.size()));
  }
  private Settlement getRandomSettlement(Player player) {
    List<Settlement> settlements = player.getSettlements();
    if (!settlements.isEmpty()) {
      return settlements.get(random.nextInt(settlements.size()));
    }
    return null; // Or handle this case appropriately
  }



}
