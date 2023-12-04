package edu.odtu.ceng453.group10.catanfrontend.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainScreen {
  private static final int width;
  private static final int height;

  static {
    width = 1920;
    height = 1080;
  }

  public static Scene getScene(Stage primaryStage) {

    javafx.scene.control.Button loginButton = new javafx.scene.control.Button("Login");
    loginButton.setOnAction(event -> primaryStage.setScene(LoginScreen.getScene(primaryStage)));

    javafx.scene.control.Button registerButton = new javafx.scene.control.Button("Register");
    registerButton.setOnAction(event -> primaryStage.setScene(RegisterScreen.getScene()));

    Button leaderboardButton = new Button("Leaderboard");
    leaderboardButton.setOnAction(event -> primaryStage.setScene(LeaderboardScreen.getScene()));

    Button exitButton = new Button("Exit");
    exitButton.setOnAction(event -> {
      primaryStage.close();
      Platform.exit();
    });

    GridPane mainPane = new GridPane();
    mainPane.setAlignment(Pos.CENTER);
    mainPane.setPadding(new Insets((double) height / 3, (double) width / 3, (double) height / 3, (double) width
        / 3));
    mainPane.setHgap(7);
    mainPane.setVgap(7);
    mainPane.add(loginButton, 0, 0);
    mainPane.add(registerButton, 0, 1);
    mainPane.add(leaderboardButton, 0, 2);
    mainPane.add(exitButton, 0, 3);

    return new Scene(mainPane);
  }
}
