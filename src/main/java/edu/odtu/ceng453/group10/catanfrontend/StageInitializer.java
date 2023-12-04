package edu.odtu.ceng453.group10.catanfrontend;

import edu.odtu.ceng453.group10.catanfrontend.CatanGameApplication.StageReadyEvent;
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
  @Value("classpath:/fxml/game.fxml")
  private Resource gameResource;
  private final String applicationTitle;
  private final ApplicationContext applicationContext;

  public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                              ApplicationContext applicationContext) {
    this.applicationTitle = applicationTitle;
    this.applicationContext = applicationContext;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(gameResource.getURL());
      fxmlLoader.setControllerFactory(applicationContext::getBean);
      Parent parent = fxmlLoader.load();

      Stage stage = event.getStage();
      stage.setScene(new Scene(parent, 800, 600));
      stage.setTitle(applicationTitle);
      stage.show();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

}
