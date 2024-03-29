package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import edu.odtu.ceng453.group10.catanfrontend.requests.LoginResponse;
import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import java.util.Set;
import java.util.logging.Logger; // Import the Logger class
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginScreen {

  private static final Logger logger = Logger.getLogger(LoginScreen.class.getName()); // Create a logger instance for the class

  private final GameClient client;
  private Scene mainScene;
  private final PlayScreen playScreen;

  LoginScreen(GameClient client, PlayScreen playScreen) {
    this.client = client;
    this.playScreen = playScreen;
  }

  public void setMainScene(Scene mainScene) {
    this.mainScene = mainScene;
  }

  public Scene getScene(Stage primaryStage) {

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> {
      primaryStage.setScene(mainScene);
      logger.info("Back button clicked."); // Log an info message
    });

    GridPane loginPane = new GridPane();
    Label emailLabel = new Label("Email: ");
    TextField emailField = new TextField();
    Label passwordLabel = new Label("Password: ");
    PasswordField passwordField = new PasswordField();
    Button loginButton = getLoginButton(primaryStage, emailField, passwordField);

    loginPane.add(emailLabel, 0, 0);
    loginPane.add(emailField, 1, 0);
    loginPane.add(passwordLabel, 0, 1);
    loginPane.add(passwordField, 1, 1);
    loginPane.add(loginButton, 0, 2);
    loginPane.add(backButton, 0, 3);
    loginPane.setHgap(5.5);
    loginPane.setVgap(5.5);
    loginPane.setAlignment(Pos.CENTER);

    logger.info("Login screen created."); // Log an info message

    return new Scene(loginPane, Settings.getWidth(), Settings.getHeight());
  }

  private Button getLoginButton(Stage primaryStage, TextField emailField,
                                PasswordField passwordField) {
    Button loginButton = new Button("Login");
    loginButton.setOnAction(e -> {
      logger.info("Login tried"); // Log a warning message
      String email = emailField.getText();
      String password = passwordField.getText();
      logger.info("Request is sending"); // Log a warning message
      Request loginRequest = new Request();
      logger.info("Requested"); // Log a warning message

      LoginResponse response = loginRequest.sendLoginRequest(email, password);
      if (response.email().isEmpty()) {
        logger.info("Login failed for email" + email);
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Warning");
        errorAlert.setContentText("Wrong email/password.");
        errorAlert.showAndWait();
      } else {
        logger.info("login is ok");
        client.setUsername(response.username());
        primaryStage.setScene(playScreen.getScene(primaryStage));
        logger.info("User logged in: " + response.username()); // Log an info message
      }
    });
    return loginButton;
  }
}
