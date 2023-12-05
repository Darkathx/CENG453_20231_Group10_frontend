package edu.odtu.ceng453.group10.catanfrontend.ui;

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

    });
    Button multiPlayer = new Button("Multi-player Mode");
    multiPlayer.setOnAction(e -> {
      Alert todo = new Alert(AlertType.ERROR);
      todo.setHeaderText("TODO ERROR");
      todo.setContentText("Multiplayer mode is not finished.");
      todo.showAndWait();
    });
    return new Scene(new GridPane());
  }

}
