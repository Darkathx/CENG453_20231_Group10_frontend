package edu.odtu.ceng453.group10.catanfrontend;

import edu.odtu.ceng453.group10.catanfrontend.CatanGameApplication.StageReadyEvent;
import edu.odtu.ceng453.group10.catanfrontend.ui.MainScreen;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
  private final String applicationTitle;

  public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle) {
    this.applicationTitle = applicationTitle;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
      Stage stage = event.getStage();
      stage.setScene(MainScreen.getScene(stage));
      stage.setTitle(applicationTitle);
      stage.show();
    }

}
