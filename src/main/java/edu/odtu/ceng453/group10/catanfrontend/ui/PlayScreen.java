package edu.odtu.ceng453.group10.catanfrontend.ui;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlayScreen {
  public static Scene getScene(Stage primaryStage) {
    return new Scene(new GridPane());
  }

}
