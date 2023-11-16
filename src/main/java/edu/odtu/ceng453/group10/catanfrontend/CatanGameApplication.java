package edu.odtu.ceng453.group10.catanfrontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class CatanGameApplication extends Application {
  private ConfigurableApplicationContext applicationContext;

  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(CatanFrontendApplication.class).run();
  }

  @Override
  public void start(Stage primaryStage) {
    applicationContext.publishEvent(new StageReadyEvent(primaryStage));
  }

  static class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(Stage stage) {
      super(stage);
    }

    public Stage getStage() {
      return (Stage) getSource();
    }
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }
}
