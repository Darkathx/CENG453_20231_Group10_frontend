package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
import edu.odtu.ceng453.group10.catanfrontend.game.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class ScoreboardComponent {
  private GridPane pane;

  public GridPane getScoreboard(GameState state) {
    pane = new GridPane();
    pane.setAlignment(Pos.CENTER_LEFT);
    pane.setHgap(10.0);
    pane.setStyle("-fx-grid-lines-visible: true;");
    ArrayList<Player> players = new ArrayList<>(state.getPlayers());
    for(int i = 3; i > -1; i--) {
      Text username = new Text(players.get(i).getName());
      Text score = new Text(String.valueOf(players.get(i).getPoints()));
      pane.add(username, 0, 3 - i);
      pane.add(score, 1, 3 - i);
    }
    return pane;
  }
}
