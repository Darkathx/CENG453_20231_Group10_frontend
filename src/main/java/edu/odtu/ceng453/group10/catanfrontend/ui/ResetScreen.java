package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.requests.Request;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class ResetScreen {
  private Scene playScene;
  private Scene mainScene;

  public void setMainScene(Scene mainScene) {
    this.mainScene = mainScene;
  }

  public void setPlayScene(Scene playScene) {
    this.playScene = playScene;
  }

  public Scene getScene(Stage primaryStage) {
    GridPane resetPane = new GridPane();
    Text resetText = new Text("Email: ");
    Text passwordText = new Text("New Password: ");
    PasswordField resetField = new PasswordField();
    TextField emailField = new TextField();
    Button resetButton = new Button("Reset");
    resetButton.setOnAction(e -> {
      Request request = new Request();
      boolean check = request.sendResetRequest(emailField.getText(), resetField.getText());
      if(check) {
        primaryStage.setScene(mainScene);
      }
      else {
        resetField.clear();
        emailField.clear();
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Email is wrong");
        alert.setTitle("Error");
      }
    });
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> {
      primaryStage.setScene(playScene);
    });
    resetPane.add(resetText, 0, 0);
    resetPane.add(emailField, 0, 1);
    resetPane.add(passwordText, 1, 1);
    resetPane.add(resetField, 1, 0);
    resetPane.add(resetButton, 0, 2);
    resetPane.add(backButton, 0, 3);
    return new Scene(resetPane);
  }
}
