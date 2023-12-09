package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.game.ResourceType;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ResourcesComponent {

  public GridPane getNewComponent(String[] usernames, int[][] resources) {
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER_RIGHT);
    pane.setStyle("-fx-grid-lines-visible: true;");
    int k = 1;
    for(ResourceType resource : ResourceType.values()) {
      Text resourceText = new Text(resource.toString());
      pane.add(resourceText, k++, 0);
    }
    for(int i = 0; i < 4; i++) {
      Text username = new Text(usernames[i]);
      pane.add(username, 0, i+1);
      for(int j = 0; j < 5; j++) {
        Text resource = new Text(String.valueOf(resources[i][j]));
        pane.add(resource, j+1, i+1);
      }
    }
    return pane;
  }
}
