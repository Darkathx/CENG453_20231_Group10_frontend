package edu.odtu.ceng453.group10.catanfrontend.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayScreen {
  private final GameClient client;
  private Scene mainScene;

  PlayScreen(GameClient client) {
    this.client = client;
  }

  public void setMainScene(Scene mainScene) {
    this.mainScene = mainScene;
  }

  public Scene getScene(Stage primaryStage) {
    Button logout = new Button("Logout");
    logout.setOnAction(e -> {
      primaryStage.setScene(mainScene);
    });

    Button singlePlayer = new Button("Single-player Mode");
    singlePlayer.setOnAction(e -> {
      Stage gameStage = new Stage();
      gameStage.setScene(client.getGameScene(gameStage));
      gameStage.show();
    });
    Button multiPlayer = new Button("Multi-player Mode");
    multiPlayer.setOnAction(e -> {
      Alert todo = new Alert(AlertType.ERROR);
      todo.setHeaderText("TODO ERROR");
      todo.setContentText("Multiplayer mode is not finished.");
      todo.showAndWait();
    });
    GridPane playPane = new GridPane();
    playPane.setAlignment(Pos.CENTER);
    playPane.add(singlePlayer, 0, 0);
    playPane.add(multiPlayer, 0, 1);
    playPane.add(logout, 0, 2);
    return new Scene(playPane);
  }

}
