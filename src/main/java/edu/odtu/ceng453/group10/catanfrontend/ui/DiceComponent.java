package edu.odtu.ceng453.group10.catanfrontend.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DiceComponent {

  private final HashMap<Integer, Image> map;
  public DiceComponent() {
    map = new HashMap<>();
    try {
      for(int i = 1; i <= 6; i++) {
        String name = switch (i) {
          case 1 -> "one.png";
          case 2 -> "two.png";
          case 3 -> "three.png";
          case 4 -> "four.png";
          case 5 -> "five.png";
          case 6 -> "six.png";
          default -> throw new IllegalStateException("Unexpected value: " + i);
        };
        map.put(i, new Image(new ClassPathResource("static/" + name).getInputStream()));
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  public GridPane getNewComponent() {
    GridPane pane = new GridPane(5.5, 5.5);
    int[] dice = rollDice();
    ImageView dice1 = new ImageView(map.get(dice[0]));
    ImageView dice2 = new ImageView(map.get(dice[1]));
    dice1.setFitHeight(100.0);
    dice1.setFitWidth(100.0);
    dice2.setFitHeight(100.0);
    dice2.setFitWidth(100.0);
    Button rollButton = new Button("Roll Dice");
    rollButton.setOnAction(e -> {
      int[] newDice = rollDice();
      dice1.setImage(map.get(newDice[0]));
      dice2.setImage(map.get(newDice[1]));
      //TODO: Bind dice values to GameState
    });

    pane.add(dice1, 0, 0);
    pane.add(dice2, 2, 0);
    pane.add(rollButton, 1, 1);
    return pane;
  }

  private int[] rollDice() {
    int[] dice = new int[2];
    Random rn = new Random();
    dice[0] = rn.nextInt(6) + 1;
    dice[1] = rn.nextInt(6) + 1;
    return dice;
  }

}
