package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
import edu.odtu.ceng453.group10.catanfrontend.game.Player;
import edu.odtu.ceng453.group10.catanfrontend.game.ResourceType;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

@Component
public class ResourcesComponent {

  public GridPane getNewComponent(GameState state) {
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER_RIGHT);
    pane.setStyle("-fx-grid-lines-visible: true;");
    int k = 1;
    for(ResourceType resource : ResourceType.values()) {
      Text resourceText = new Text(resource.toString());
      pane.add(resourceText, k++, 0);
    }
    List<Player> players = state.getPlayers();
    for(int i = 0; i < 4; i++) {
      int j = 0;
      Text username = new Text(players.get(i).getName());
      pane.add(username, 0, i+1);
      for(ResourceType resource : ResourceType.values()) {
        Text resourceAmount = new Text(String.valueOf(players.get(i).getResources().getResourceCount(resource)));
        pane.add(resourceAmount, ++j, i+1);
      }
    }
    return pane;
  }
}
