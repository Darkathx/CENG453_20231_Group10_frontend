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
}
