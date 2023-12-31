package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.GameController;
import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import edu.odtu.ceng453.group10.catanfrontend.ui.GameClient;

public class MultiplayerPoll implements Runnable{
  private GameState gameState;
  private GameClient gameClient;
  private GameController gameController;

  public MultiplayerPoll(GameState gameState, GameClient gameClient, GameController gameController) {
    this.gameState = gameState;
    this.gameClient = gameClient;
    this.gameController = gameController;
  }

  @Override
  public void run() {
    Request request = new Request();
    GameMulti multi = null;

  }
}
