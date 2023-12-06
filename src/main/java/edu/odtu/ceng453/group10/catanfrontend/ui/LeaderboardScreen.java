package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.config.LeaderboardType;
import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LeaderboardScreen {

  public static Scene getScene(Stage primaryStage) {
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> {
      primaryStage.setScene(MainScreen.getScene(primaryStage));
    });
    Button weeklyButton = new Button("Weekly Leaderboard");
    weeklyButton.setOnAction(e -> {
      Request request = new Request();
      String[] leaderboard = request.sendLeaderboardRequest(LeaderboardType.WEEKLY);
      Scene leaderboardScene = prepareLeaderboardScene(leaderboard, LeaderboardType.WEEKLY, primaryStage);
      primaryStage.setScene(leaderboardScene);
    });

    Button monthlyButton = new Button("Monthly Leaderboard");
    monthlyButton.setOnAction(e -> {
      Request request = new Request();
      String[] leaderboard = request.sendLeaderboardRequest(LeaderboardType.MONTHLY);
      Scene leaderboardScene = prepareLeaderboardScene(leaderboard, LeaderboardType.MONTHLY, primaryStage);
      primaryStage.setScene(leaderboardScene);
    });

    Button overallButton = new Button("Overall Leaderboard");
    overallButton.setOnAction(e -> {
      Request request = new Request();
      String[] leaderboard = request.sendLeaderboardRequest(LeaderboardType.OVERALL);
      Scene leaderboardScene = prepareLeaderboardScene(leaderboard, LeaderboardType.OVERALL, primaryStage);
      primaryStage.setScene(leaderboardScene);
    });

    GridPane leaderboardPane = new GridPane();
    leaderboardPane.setAlignment(Pos.CENTER);
    leaderboardPane.add(weeklyButton, 0, 0);
    leaderboardPane.add(monthlyButton, 0, 1);
    leaderboardPane.add(overallButton, 0, 2);
    leaderboardPane.add(backButton, 0, 3);
    return new Scene(leaderboardPane, Settings.getWidth(), Settings.getHeight());
  }


  private static Scene prepareLeaderboardScene(String[] leaderboard, LeaderboardType type, Stage primaryStage) {
    String label = switch(type) {
      case LeaderboardType.WEEKLY: yield "WEEKLY LEADERBOARD";
      case LeaderboardType.MONTHLY: yield "MONTHLY LEADERBOARD";
      case LeaderboardType.OVERALL: yield "OVERALL LEADERBOARD";
    };
    GridPane leaderboardPane = new GridPane();
    leaderboardPane.setAlignment(Pos.CENTER);
    leaderboardPane.add(new Label(label), 0, 0);
    for(int i = 0; i < 10; i++) {
      Label curLabel = new Label(String.valueOf(i) + ": " + leaderboard[i]);
      leaderboardPane.add(curLabel, 0, i + 1);
    }
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> {
      primaryStage.setScene(LeaderboardScreen.getScene(primaryStage));
    });
    return new Scene(leaderboardPane);
  }
}
