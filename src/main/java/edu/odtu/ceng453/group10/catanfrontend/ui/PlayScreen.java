package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
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
  private final ResetScreen resetScreen;
  private Scene mainScene;

  PlayScreen(GameClient client, ResetScreen resetScreen) {
    this.client = client;
    this.resetScreen = resetScreen;
  }

  public void setMainScene(Scene mainScene) {
    this.mainScene = mainScene;
  }

  public Scene getScene(Stage primaryStage) {
    Button logout = new Button("Logout");
    logout.setOnAction(e -> {
      primaryStage.setScene(mainScene);
    });

    Button resetPassword = new Button("Reset Password");
    resetPassword.setOnAction(e -> {
      primaryStage.setScene(resetScreen.getScene(primaryStage));
    });

    Button singlePlayer = new Button("Single-player Mode");
    singlePlayer.setOnAction(e -> {
      Stage gameStage = new Stage();
      client.playSingleGame(gameStage);
      gameStage.show();
    });
    Button multiPlayer = new Button("Multi-player Mode");
    multiPlayer.setOnAction(e -> {
      Stage gameStage = new Stage();
      client.playMultiGame(gameStage);
      gameStage.show();
    });
    GridPane playPane = new GridPane();
    playPane.setAlignment(Pos.CENTER);
    playPane.add(singlePlayer, 0, 0);
    playPane.add(multiPlayer, 0, 1);
    playPane.add(logout, 0, 2);
    Scene scene = new Scene(playPane);
    resetScreen.setPlayScene(scene);
    resetScreen.setMainScene(mainScene);
    return scene;
  }

}
