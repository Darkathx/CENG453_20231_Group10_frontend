package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.requests.RegisterResponse;
import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterScreen {

  public static Scene getScene(Stage primaryStage) {
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> MainScreen.getScene(primaryStage));

    GridPane registerPane = new GridPane();

    Label usernameLabel = new Label("Username: ");
    TextField usernameField = new TextField();
    Label emailLabel = new Label("Email: ");
    TextField emailField = new TextField();
    Label passwordLabel = new Label("Password: ");
    PasswordField passwordField = new PasswordField();

    Button registerButton = new Button("Register");
    registerButton.setOnAction(e -> {
      String username = usernameField.getText();
      String email = emailField.getText();
      String password = passwordField.getText();
      Request registerRequest = new Request();
      RegisterResponse response = registerRequest.sendRegisterRequest(username, email, password);
      if(response.email().isEmpty()) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Warning");
        errorAlert.setContentText("This username/email has already taken.");
        errorAlert.showAndWait();
      }
      primaryStage.setScene(MainScreen.getScene(primaryStage));
    });

    registerPane.add(usernameLabel, 0, 0);
    registerPane.add(usernameField, 1, 0);
    registerPane.add(emailLabel, 0, 1);
    registerPane.add(emailField, 1, 1);
    registerPane.add(passwordLabel, 0, 2);
    registerPane.add(passwordField, 1, 2);
    registerPane.add(registerButton, 0, 3);
    registerPane.add(backButton, 0, 4);
    return new Scene(registerPane);
  }
}
