package edu.odtu.ceng453.group10.catanfrontend.ui;

import edu.odtu.ceng453.group10.catanfrontend.game.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import edu.odtu.ceng453.group10.catanfrontend.config.Settings;

public class BoardView extends Pane {
    private static final double RADIUS = 50; // Tile radius
    private static final double APOTHEM = RADIUS * Math.sqrt(3) / 2; // Height from center to side
    private static final double GAP = 20; // Gap between tiles
    private static final int[] rowLengths = {3, 4, 5, 4, 3}; // Hexagons per row

    public BoardView(Tile[] tiles) {
        // The full width needed to display the widest row with gaps
        double maxWidth = 5 * (RADIUS * 3 / 2) + (4 * GAP); // 4 gaps for 5 hexagons

        // Calculate the starting Y position to center the board vertically with gaps
        double totalHeight = (5 * (APOTHEM * 2)); // 4 gaps for 5 rows

        double startY = (Settings.getHeight() - totalHeight) / 2;

        int tileIndex = 0;
        for (int row = 0; row < rowLengths.length; row++) {
            // Calculate the width of the current row with gaps
            double currentRowWidth = rowLengths[row] * (RADIUS * 3 / 2) + ((rowLengths[row] - 1) * GAP);

            // Calculate the starting X position to center the current row with gaps
            double startX = (Settings.getWidth() - maxWidth) / 2 + (maxWidth - currentRowWidth) / 2;

            for (int col = 0; col < rowLengths[row]; col++) {
                // Calculate the position for each tile with gap
                double x = startX + col * (RADIUS * 3 / 2 + GAP);
                double y = startY + row * (APOTHEM * 2);

                Tile tile = tiles[tileIndex++];
                tile.getHexagon().setTranslateX(x);
                tile.getHexagon().setTranslateY(y);

                // Add the number text if available
                if (tile.getNumber() != null) {
                    Text numberText = new Text(tile.getNumber().toString());
                    numberText.setTextAlignment(TextAlignment.CENTER);
                    numberText.setTranslateX(x);
                    numberText.setTranslateY(y + APOTHEM / 2); // Adjust for the center of the hexagon
                    this.getChildren().add(numberText);
                }

                this.getChildren().add(tile.getHexagon());
            }
        }
    }
}
