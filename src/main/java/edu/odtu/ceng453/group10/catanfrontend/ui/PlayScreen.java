package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.game.GameClient;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlayScreen {
  private final String username;
  private final String email;

  PlayScreen(String username, String email) {
    this.email = email;
    this.username = username;
  }

  public Scene getScene(Stage primaryStage) {
    Button logout = new Button("Logout");
    logout.setOnAction(e -> {
      MainScreen.getScene(primaryStage);
    });

    Button singlePlayer = new Button("Single-player Mode");
    singlePlayer.setOnAction(e -> {
      Stage gameStage = new Stage();
      GameClient client = new GameClient(email, username);
      gameStage.setScene(client.getScene(gameStage));
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
    playPane.add(singlePlayer, 0, 0);
    playPane.add(multiPlayer, 0, 1);
    playPane.add(logout, 0, 2);
    return new Scene(playPane);
  }

}
