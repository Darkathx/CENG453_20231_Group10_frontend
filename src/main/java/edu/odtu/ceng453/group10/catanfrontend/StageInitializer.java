package edu.odtu.ceng453.group10.catanfrontend;

import edu.odtu.ceng453.group10.catanfrontend.CatanGameApplication.StageReadyEvent;
import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
import edu.odtu.ceng453.group10.catanfrontend.ui.MainScreen;
import edu.odtu.ceng453.group10.catanfrontend.ui.ResourcesComponent;
import edu.odtu.ceng453.group10.catanfrontend.ui.ScoreboardComponent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import edu.odtu.ceng453.group10.catanfrontend.game.Board;
import edu.odtu.ceng453.group10.catanfrontend.ui.BoardView;
import edu.odtu.ceng453.group10.catanfrontend.game.Tile;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
  private final String applicationTitle;
  private final MainScreen screen;
  private final GameState gameState;
  public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, MainScreen screen, GameState gameState) {
    this.applicationTitle = applicationTitle;
    this.screen = screen;
    this.gameState = gameState;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
      Stage stage = event.getStage();
      Board board = gameState.getBoard(); // Get the board from GameState
      Tile[] tilesArray = board.getTiles().toArray(new Tile[0]);
      BoardView boardView = new BoardView(tilesArray); // Create BoardView with tiles

      Scene scene = new Scene(boardView, 800, 600); // Set the scene size as needed
      stage.setScene(scene);
      stage.setTitle(applicationTitle);
      stage.show();
    }

}
