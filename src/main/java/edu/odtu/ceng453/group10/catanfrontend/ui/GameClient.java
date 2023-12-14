package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
import edu.odtu.ceng453.group10.catanfrontend.requests.LoginResponse;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class GameClient {
  private String email;
  private String username;
  private String[] usernames;
  private GameState state;

  public GameClient(GameState state) {
    this.state = state;
  }

  public void setEmailAndUsername(String email, String username) {
    this.email = email;
    this.username = username;
  }

  public Scene getGameScene(Stage gameStage) {
    BorderPane totalPane = new BorderPane();
    Scene gameScene = new Scene(totalPane);
    prepareGameScene(state, totalPane);
    gameScene.setOnKeyPressed(e -> {
      if(e.getCode() == KeyCode.ESCAPE) {
        gameStage.setScene(getEscScene(gameStage));
      }
    });
    return gameScene;
  }

  private void prepareGameScene(GameState state, BorderPane pane) {

  }

  private Scene getEscScene(Stage gameStage) {
    GridPane escPane = new GridPane();
    escPane.setAlignment(Pos.CENTER);
    escPane.setOnKeyPressed(e -> getGameScene(gameStage));
    Button quitButton = new Button("Quit");
    quitButton.setOnAction(e -> {
      gameStage.close();
    });
    escPane.add(quitButton, 0, 0);
    return new Scene(escPane, Settings.getWidth(), Settings.getHeight());
  }


}