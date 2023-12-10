package edu.odtu.ceng453.group10.catanfrontend.ui;

import java.util.Arrays;
import java.util.Collections;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class ScoreboardComponent {
  private GridPane pane;

  public GridPane getScoreboard(String[] usernames, int[] scores) {
    pane = new GridPane();
    pane.setAlignment(Pos.CENTER_LEFT);
    pane.setHgap(10.0);
    pane.setStyle("-fx-grid-lines-visible: true;");
    for(int i = 3; i > -1; i--) {
      Text username = new Text(usernames[i]);
      Text score = new Text(String.valueOf(scores[i]));
      pane.add(username, 0, 3 - i);
      pane.add(score, 1, 3 - i);
    }
    return pane;
  }
}
