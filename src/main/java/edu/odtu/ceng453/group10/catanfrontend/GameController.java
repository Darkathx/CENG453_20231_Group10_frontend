package edu.odtu.ceng453.group10.catanfrontend;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import org.springframework.stereotype.Component;

@Component
public class GameController {

  @FXML
  public LineChart<String, Double> game;

}
