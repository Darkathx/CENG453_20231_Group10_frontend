package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.requests.LoginResponse;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameClient {
  private final String email;
  private final String username;
  private String[] usernames;

  public GameClient(String email, String username) {
    this.email = email;
    this.username = username;
  }

  public Scene getScene(Stage gameStage) {
    BorderPane totalPane = new BorderPane();
    Scene gameScene = new Scene(totalPane);
    return gameScene;
  }


}
