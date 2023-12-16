package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
import edu.odtu.ceng453.group10.catanfrontend.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
                    ResourcesComponent resourcesComponent, ScoreboardComponent scoreboardComponent) {
    this.state = gameState;
    this.gameController = new GameController(gameState);
    this.diceComponent = diceComponent;
    this.resourcesComponent = resourcesComponent;
    this.scoreboardComponent = scoreboardComponent;
    this.boardView = new BoardView(gameState, gameController);
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void playSingleGame(Stage stage) {
    state.initializeSingleGame(username);
    gameController.setupInitialBoard();
    boardView.updateBoardView(state);
    stage.setScene(getGameScene(stage));
  }

  public Scene getGameScene(Stage gameStage) {
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

  private Button getRollButton(Stage stage) {
    Button rollButton = new Button("Roll Dice");
    rollButton.setOnAction(e -> {
      int[] dice = diceComponent.rollDice();
      state.setLastDiceRoll(dice);
      // Logic to distribute resources based on dice roll
      // Refresh the game scene to reflect changes
      stage.setScene(getGameScene(stage));
    });
    rollButton.setDisable(!isPlayerTurn());
    return rollButton;
  }

  private Button getEndTurnButton(Stage stage) {
    Button endTurnButton = new Button("End Turn");
    endTurnButton.setOnAction(e -> {
      state.setCurrentPlayerIndex((state.getCurrentPlayerIndex() + 1) % state.getPlayers().size());
      state.unsetDiceRolled();
      // Refresh the game scene for the next player
      stage.setScene(getGameScene(stage));
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
}
