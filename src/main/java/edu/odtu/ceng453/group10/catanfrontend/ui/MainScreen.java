package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class MainScreen {
  private final LoginScreen loginScreen;
  private final RegisterScreen registerScreen;
  private final LeaderboardScreen leaderboardScreen;
  private final PlayScreen playScreen;
  private Scene scene;

  MainScreen(LoginScreen loginScreen, RegisterScreen registerScreen,
              LeaderboardScreen leaderboardScreen, PlayScreen playScreen) {
    this.loginScreen = loginScreen;
    this.registerScreen = registerScreen;
    this.leaderboardScreen = leaderboardScreen;
    this.playScreen = playScreen;
  }


  public Scene getScene(Stage primaryStage) {
    if(scene != null) return scene;

    javafx.scene.control.Button loginButton = new javafx.scene.control.Button("Login");
    loginButton.setOnAction(event -> primaryStage.setScene(loginScreen.getScene(primaryStage)));

    javafx.scene.control.Button registerButton = new javafx.scene.control.Button("Register");
    registerButton.setOnAction(event -> primaryStage.setScene(registerScreen.getScene(primaryStage)));

    Button leaderboardButton = new Button("Leaderboard");
    leaderboardButton.setOnAction(event -> primaryStage.setScene(leaderboardScreen.getScene(primaryStage)));

    Button exitButton = new Button("Exit");
    exitButton.setOnAction(event -> {
      primaryStage.close();
      Platform.exit();
    });

    GridPane mainPane = new GridPane();
    mainPane.setAlignment(Pos.CENTER);
    double hPad = Settings.getHeight() / 3, vPad = Settings.getWidth() / 3;
    mainPane.setPadding(new Insets(hPad, vPad, hPad, vPad));
    mainPane.setHgap(7);
    mainPane.setVgap(7);
    mainPane.add(loginButton, 0, 0);
    mainPane.add(registerButton, 0, 1);
    mainPane.add(leaderboardButton, 0, 2);
    mainPane.add(exitButton, 0, 3);
    scene =new Scene(mainPane, Settings.getWidth(), Settings.getHeight());
    registerScreen.setMainScene(scene);
    loginScreen.setMainScene(scene);
    leaderboardScreen.setMainScene(scene);
    playScreen.setMainScene(scene);

    return scene;
  }

}
