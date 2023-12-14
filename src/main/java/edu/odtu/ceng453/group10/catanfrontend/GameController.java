package edu.odtu.ceng453.group10.catanfrontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import edu.odtu.ceng453.group10.catanfrontend.game.*;
import java.util.List;
import java.util.Random;

@Component
public class GameController {

  @FXML
  public LineChart<String, Double> game;

  private Board board;
  public List<Player> players;
  private Random random;

  // Call this method at the start of the game to place initial settlements and roads
  public void setupInitialBoard() {
    for (Player player : players) {
      // Choose a random vertex for the settlement
      Vertex settlementVertex = getRandomAvailableVertex();
      buildSettlement(player, settlementVertex);

      // Choose a random edge connected to the settlement vertex for the road
      Edge roadEdge = getRandomAvailableEdge(settlementVertex);
      buildRoad(player, roadEdge);
    }
  }

  // Method to allow a player to build a settlement
  public boolean buildSettlement(Player player, Vertex vertex) {
    if (vertex.isAvailable() && player.canBuildSettlement()) {
      Settlement settlement = new Settlement(vertex);
      player.deductResourcesForSettlement();
      player.addSettlement(settlement);
      return true;
    }
    return false;
  }

  // Method to allow a player to build a road
  public boolean buildRoad(Player player, Edge edge) {
    if (edge.isAvailable() && player.canBuildRoad()) {
      Road road = new Road(edge);
      player.deductResourcesForRoad();
      player.addRoad(road);
      return true;
    }
    return false;
  }

  private Vertex getRandomAvailableVertex() {
    List<Vertex> availableVertices = board.getAvailableVertices();
    return availableVertices.get(random.nextInt(availableVertices.size()));
  }

  private Edge getRandomAvailableEdge(Vertex vertex) {
    List<Edge> connectedEdges = board.getConnectedAvailableEdges(vertex);
    return connectedEdges.get(random.nextInt(connectedEdges.size()));
  }
}
