package edu.odtu.ceng453.group10.catanfrontend;

import edu.odtu.ceng453.group10.catanfrontend.CatanGameApplication.StageReadyEvent;
import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
import edu.odtu.ceng453.group10.catanfrontend.ui.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import edu.odtu.ceng453.group10.catanfrontend.game.Board;
import edu.odtu.ceng453.group10.catanfrontend.game.Tile;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
  private final String applicationTitle;
  private final PlayScreen screen;
  public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, PlayScreen screen) {
    this.applicationTitle = applicationTitle;
    this.screen = screen;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
      Stage stage = event.getStage();
      stage.setScene(screen.getScene(stage));
      stage.setTitle(applicationTitle);
      stage.show();
    }

}
