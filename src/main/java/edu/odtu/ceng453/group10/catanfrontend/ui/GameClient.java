package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
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
import edu.odtu.ceng453.group10.catanfrontend.game.Board;
import edu.odtu.ceng453.group10.catanfrontend.game.Tile;

@Component
public class GameClient {
  private String username;
  private Board board;
  private BoardView boardView;
  private GameState state;
  private ResourcesComponent resourcesComponent;
  private ScoreboardComponent scoreboardComponent;
  private DiceComponent diceComponent;

  public GameClient(GameState state, DiceComponent diceComponent,
          ResourcesComponent resourcesComponent, ScoreboardComponent scoreboardComponent) {
    this.state = state;
    this.board = state.getBoard(); // Get the board from the GameState
    this.boardView = new BoardView(board.getTiles().toArray(new Tile[0])); // Create BoardView
    this.diceComponent = diceComponent;
    this.resourcesComponent = resourcesComponent;
    this.scoreboardComponent = scoreboardComponent;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void playSingleGame(Stage stage) {
    state.initializeSingleGame(username);
    stage.setScene(getGameScene(stage));
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
    BorderPane totalPane = new BorderPane();
    BorderPane.setAlignment(boardView, Pos.CENTER);
    totalPane.setCenter(boardView);
    HBox bottomContainer = new HBox();
    GridPane diceContainer = diceComponent.getNewComponent(state);
    diceContainer.add(getRollButton(gameStage), 1, 1);
    bottomContainer.getChildren().addAll(diceContainer, resourcesComponent.getNewComponent(state));
    totalPane.setBottom(bottomContainer);
    VBox rightContainer = new VBox();
    Text currentTurn = new Text("Current Turn: " + state.getCurrentPlayer().getName());
    rightContainer.getChildren().addAll(currentTurn, scoreboardComponent.getScoreboard(state));
    totalPane.setRight(rightContainer);
    totalPane.setTop(getEndTurnButton(gameStage));
    Scene gameScene = new Scene(totalPane);
    gameScene.setOnKeyPressed(e -> {
      if(e.getCode() == KeyCode.ESCAPE) {
        gameStage.setScene(getEscScene(gameStage));
      }
    });
    return gameScene;
  }

  public BoardView getBoardView() {
    return boardView;
  }

  private Button getRollButton(Stage stage) {
    Button rollButton = new Button("Roll Dice");
    rollButton.setOnAction(e -> {
      int[] dice = diceComponent.rollDice();
      state.setLastDiceRoll(dice);
      //TODO: distribute resources
      stage.setScene(getGameScene(stage));
    });
    if(state.getCurrentPlayer().getName().equals(state.getPlayers().get(0).getName())) {
      rollButton.setDisable(false);
    }
    else {
      rollButton.setDisable(true);
    }
    return rollButton;
  }

  private Button getEndTurnButton(Stage stage) {
    Button endTurnButton = new Button("End Turn");
    endTurnButton.setOnAction(e -> {
      state.setCurrentPlayerIndex((state.getCurrentPlayerIndex() + 1) % state.getPlayers().size());
      stage.setScene(getGameScene(stage));
      //TODO: AI PLAYS HERE
    });
    if(state.getCurrentPlayer().getName().equals(state.getPlayers().get(0).getName()) && state.getDiceRolled()) {
      endTurnButton.setDisable(false);
    }
    else {
      endTurnButton.setDisable(true);
    }
    return endTurnButton;
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
