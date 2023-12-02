package edu.odtu.ceng453.group10.catanfrontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component
public class GameController {

  @FXML
  public LineChart<String, Double> game;

}
