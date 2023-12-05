package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.requests.LoginResponse;
import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginScreen {

  public static Scene getScene(Stage primaryStage) {

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> MainScreen.getScene(primaryStage));

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
    return new Scene(loginPane);
  }

  private static Button getLoginButton(Stage primaryStage, TextField emailField,
      PasswordField passwordField) {
    Button loginButton = new Button("Login");
    loginButton.setOnAction(e -> {
      String email = emailField.getText();
      String password = passwordField.getText();
      Request loginRequest = new Request();
      LoginResponse response = loginRequest.sendLoginRequest(email, password);
      if(response.email().isEmpty()) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Warning");
        errorAlert.setContentText("Wrong email/password.");
        errorAlert.showAndWait();
      }
      PlayScreen screen = new PlayScreen(response.email(), response.username());
      primaryStage.setScene(screen.getScene(primaryStage));
    });
    return loginButton;
  }
}
