package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.requests.BuildingType;
import edu.odtu.ceng453.group10.catanfrontend.requests.Buildings;
import edu.odtu.ceng453.group10.catanfrontend.requests.GameStateResponse;
import edu.odtu.ceng453.group10.catanfrontend.requests.Resources;
import java.util.ArrayList;
import java.util.List;

public class GameMulti {
  private String gameId;
  private String gameStateId;

  public GameMulti(String gameId, String gameStateId) {
    this.gameId = gameId;
    this.gameStateId = gameStateId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public void setGameStateId(String gameStateId) {
    this.gameStateId = gameStateId;
  }

  public String getGameId() {
    return gameId;
  }

  public String getGameStateId() {
    return gameStateId;
  }

  public void updateGameState(GameStateResponse response, GameState state) {
    setGameId(response.gameId());
    setGameStateId(response.id());
    state.setCurrentPlayerIndex(response.playerTurn());
    state.setLastDiceRoll(new int[]{response.dice1(), response.dice2()});
    updateResources(response, state);
    updateBuildings(response, state);
  }

  public GameStateResponse prepareGameStateResponse(GameState state) {
    return new GameStateResponse(
        gameStateId,
        gameId,
        state.getCurrentPlayerIndex(),
        state.getLastDiceRoll()[0],
        state.getLastDiceRoll()[1],
        prepareResources(state),
        prepareBuildings(state)
    );
  }

  private List<Resources> prepareResources(GameState state) {
    List<Player> players = state.getPlayers();
    List<Resources> resources = new ArrayList<>();
    for(Player player : players) {
      ResourceCardDeck deck = player.getResources();
      resources.add(new Resources(
          null,
          gameStateId,
          deck.getResourceCount(ResourceType.BRICK),
          deck.getResourceCount(ResourceType.LUMBER),
          deck.getResourceCount(ResourceType.WOOL),
          deck.getResourceCount(ResourceType.GRAIN),
          deck.getResourceCount(ResourceType.ORE)
      ));
    }
    return resources;
  }

  private List<Buildings> prepareBuildings(GameState state) {
    List<Player> players = state.getPlayers();
    List<Buildings> buildings = new ArrayList<>();
    int i = 0;
    for(Player player: players) {
      List<Settlement> settlements = player.getSettlements();
      List<Road> roads = player.getRoads();
      List<City> cities = player.getCities();
      for(Settlement settlement : settlements) {
        buildings.add(new Buildings(
            null,
            gameStateId,
            i,
            BuildingType.SETTLEMENT,
            settlement.getLocation().getKey(),
            null
        ));
      }
      for(Road road : roads) {
        buildings.add(new Buildings(
            null,
            gameStateId,
            i,
            BuildingType.ROAD,
            road.getLocation().getVertex1().getKey(),
            road.getLocation().getVertex2().getKey()
        ));
      }
      for(City city : cities) {
        buildings.add(new Buildings(
            null,
            gameStateId,
            i,
            BuildingType.CITY,
            city.getLocation().getKey(),
            null
        ));
      }
      i++;
    }
    return buildings;
  }

  private void updateResources(GameStateResponse response, GameState state) {
    List<Player> players = state.getPlayers();
    List<Resources> resources = response.resources();
    for(int i = 0; i < players.size(); i++) {
      Player player = players.get(i);
      ResourceCardDeck deck = player.getResources();
      Resources resource = resources.get(i);
      deck.setResource(ResourceType.BRICK, resource.brick());
      deck.setResource(ResourceType.LUMBER, resource.lumber());
      deck.setResource(ResourceType.WOOL, resource.wool());
      deck.setResource(ResourceType.GRAIN, resource.grain());
      deck.setResource(ResourceType.ORE, resource.ore());
    }
  }


  //TODO: FINISH THIS
  private void updateBuildings(GameStateResponse response, GameState state) {
    List<Player> players = state.getPlayers();
    List<Buildings> buildings = response.buildings();
    Board board = state.getBoard();
    Vertex vertex;
    for(Buildings building : buildings) {
      Player player = players.get(building.user());
      switch(building.type()) {
        case BuildingType.SETTLEMENT:
          vertex = board.getVertex(building.vertexKey1());
          if(vertex.hasSettlement()) break;
          Settlement settlement = new Settlement(vertex);
          player.addSettlement(settlement);
          vertex.buildSettlement(settlement);
          break;
        case BuildingType.ROAD:
          Edge edge = board.getEdge(building.vertexKey1(), building.vertexKey2());
          if(!edge.isAvailable()) break;
          edge.buildRoad(new Road(edge));
          break;
        case BuildingType.CITY:
          vertex = board.getVertex(building.vertexKey1());
          if(vertex.hasCity()) break;
          City city = new City(vertex);
          player.costlessUpgrade(vertex.getSettlement(), city);
          break;
      }
    }
  }

}
