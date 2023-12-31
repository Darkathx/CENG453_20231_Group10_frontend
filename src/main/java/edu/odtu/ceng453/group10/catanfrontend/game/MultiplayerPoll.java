package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.GameController;
import edu.odtu.ceng453.group10.catanfrontend.requests.GameResponse;
import edu.odtu.ceng453.group10.catanfrontend.requests.GameStateResponse;
import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import edu.odtu.ceng453.group10.catanfrontend.ui.GameClient;

public class MultiplayerPoll implements Runnable {
  private final GameState gameState;
  private final GameClient gameClient;
  private final GameController gameController;
  private final GameMulti multi;

  public MultiplayerPoll(GameState gameState, GameClient gameClient, GameController gameController, GameMulti multi) {
    this.gameState = gameState;
    this.gameClient = gameClient;
    this.gameController = gameController;
    this.multi = multi;
  }

  @Override
  public void run() {
    Request request = new Request();
    while(true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      GameStateResponse response = request.getGameStateRequest(multi.getGameStateId());
      if(response != null) {

      }
    }

  }
}
