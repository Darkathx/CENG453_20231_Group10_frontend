package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
import edu.odtu.ceng453.group10.catanfrontend.GameController;
import edu.odtu.ceng453.group10.catanfrontend.game.Player;
import edu.odtu.ceng453.group10.catanfrontend.requests.LoginResponse;
import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class GameClient {
  private String username;
  private GameState state;
  private BoardView boardView;
  private GameController gameController;
  private ResourcesComponent resourcesComponent;
  private ScoreboardComponent scoreboardComponent;
  private DiceComponent diceComponent;

  public GameClient(GameState gameState, DiceComponent diceComponent,
                    ResourcesComponent resourcesComponent, ScoreboardComponent scoreboardComponent,
      GameController gameController) {
    this.state = gameState;
    this.gameController = gameController;
    this.diceComponent = diceComponent;
    this.resourcesComponent = resourcesComponent;
    this.scoreboardComponent = scoreboardComponent;
    this.boardView = new BoardView(gameState, gameController);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void playSingleGame(Stage stage) {
    state.initializeSingleGame(username);
    gameController.setupInitialBoard();
    while(!isPlayerTurn()) {
      gameController.performCPUTurn();
      gameController.findLongestPath();
      boardView.updateBoardView(state);
      stage.setScene(getGameScene(stage));
    }
  }

  public Scene getGameScene(Stage gameStage) {
    Player winnerPlayer = checkWinCondition();
    if(winnerPlayer != null) {
      Request request = new Request();
      request.saveGameResult(winnerPlayer.getName(), winnerPlayer.getPoints());
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Game Over");
      alert.setContentText("Winner is " + winnerPlayer.getName() + " with " + winnerPlayer.getPoints() + " points!");
      alert.showAndWait();
      gameStage.close();
      System.exit(0);
    }
    boardView.updateBoardView(state);
    BorderPane totalPane = new BorderPane();
    BorderPane.setAlignment(boardView, Pos.CENTER);
    totalPane.setCenter(boardView);

    HBox bottomContainer = new HBox(250);
    bottomContainer.setAlignment(Pos.CENTER);
    GridPane diceContainer = diceComponent.getNewComponent(state);
    diceContainer.add(getRollButton(gameStage), 1, 1);
    bottomContainer.getChildren().addAll(diceContainer, getEndTurnButton(gameStage), resourcesComponent.getNewComponent(state));
    totalPane.setBottom(bottomContainer);

    VBox rightContainer = new VBox(100);
    rightContainer.setAlignment(Pos.CENTER);
    Text currentTurn = new Text("Current Turn: " + state.getCurrentPlayer().getName());
    Player longestPathPlayer = state.getLongestPathPlayer();
    String name = longestPathPlayer == null ? "None" : longestPathPlayer.getName();
    Text currentLongestPathInfo = new Text("Longest Path Player and Length: " + name + " " + state.getLongestPathLength());
    rightContainer.getChildren().addAll(currentTurn, currentLongestPathInfo, scoreboardComponent.getScoreboard(state));
    totalPane.setRight(rightContainer);

    Scene gameScene = new Scene(totalPane);
    gameScene.setOnKeyPressed(e -> {
      if(e.getCode() == KeyCode.ESCAPE) {
        gameStage.setScene(getEscScene(gameStage));
      }
    });

    return gameScene;
  }

  private Button getRollButton(Stage stage) {
    Button rollButton = new Button("Roll Dice");
    rollButton.setOnAction(e -> {
      int[] dice = diceComponent.rollDice();
      state.setLastDiceRoll(dice);
      state.setDiceRolled();
      // Logic to distribute resources based on dice roll
      for(Player player : state.getPlayers()) {
        player.addResourcesAccordingToDiceRoll(dice[0] + dice[1]);
      }
      // Refresh the game scene to reflect changes
      stage.setScene(getGameScene(stage));
    });
    rollButton.setDisable(!isPlayerTurn() || state.getDiceRolled());
    return rollButton;
  }

  private Button getEndTurnButton(Stage stage) {
    Button endTurnButton = new Button("End Turn");
    endTurnButton.setOnAction(e -> {
      state.setCurrentPlayerIndex((state.getCurrentPlayerIndex() + 1) % state.getPlayers().size());
      state.unsetDiceRolled();
      // Refresh the game scene for the next player
      while(!isPlayerTurn()) {
        gameController.performCPUTurn();
        gameController.findLongestPath();
        boardView.updateBoardView(state);
        stage.setScene(getGameScene(stage));
      }
    });
    endTurnButton.setDisable(!isPlayerTurn() || !state.getDiceRolled());
    return endTurnButton;
  }

  private boolean isPlayerTurn() {
    return state.getCurrentPlayer().getName().equals(username);
  }

  private Scene getEscScene(Stage gameStage) {
    GridPane escPane = new GridPane();
    escPane.setAlignment(Pos.CENTER);
    Button quitButton = new Button("Quit");
    quitButton.setOnAction(e -> gameStage.close());
    escPane.add(quitButton, 0, 0);
    return new Scene(escPane, Settings.getWidth(), Settings.getHeight());
  }


  // Additional methods as needed
  private Player checkWinCondition() {
    List<Player> players = state.getPlayers();
    Player winnerPlayer = null;
    for(Player player : players) {
      if(player.getPoints() >= 8) {
        if(winnerPlayer == null) {
          winnerPlayer = player;
        }
        else {
          if(player.getPoints() > winnerPlayer.getPoints()) {
            winnerPlayer = player;
          }
        }
      }
    }
    return winnerPlayer;
  }


}
