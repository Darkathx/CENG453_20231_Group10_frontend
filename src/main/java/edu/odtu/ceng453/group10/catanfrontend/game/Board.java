package edu.odtu.ceng453.group10.catanfrontend.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final List<Tile> tiles;

    public Board() {
        tiles = initializeTiles();
    }

    private List<Tile> initializeTiles() {
        List<Tile> tilesList = new ArrayList<>();

        // Create one desert tile without a resource type or number
        tilesList.add(new Tile(TileType.DESERT, null, null));

        // Add other types of tiles
        addTilesOfType(tilesList, TileType.HILL, 3, ResourceType.BRICK);
        addTilesOfType(tilesList, TileType.MOUNTAIN, 3, ResourceType.ORE);
        addTilesOfType(tilesList, TileType.FIELD, 4, ResourceType.GRAIN);
        addTilesOfType(tilesList, TileType.PASTURE, 4, ResourceType.WOOL);
        addTilesOfType(tilesList, TileType.FOREST, 4, ResourceType.LUMBER);

        // Shuffle the tiles, ensuring the desert is in the center
        Collections.shuffle(tilesList);
        moveDesertToCenter(tilesList);

        // Assign numbers to the tiles
        assignNumbersToTiles(tilesList);

        return tilesList;
    }

    private void addTilesOfType(List<Tile> tilesList, TileType type, int count, ResourceType resource) {
        for (int i = 0; i < count; i++) {
            tilesList.add(new Tile(type, resource, 0)); // Initialize with dummy number 0
        }
    }

    private void moveDesertToCenter(List<Tile> tilesList) {
        for (int i = 0; i < tilesList.size(); i++) {
            if (tilesList.get(i).getTileType() == TileType.DESERT) {
                Tile desert = tilesList.remove(i);
                tilesList.add(9, desert); // Place desert at the center (index 9)
                break;
            }
        }
    }

    private void assignNumbersToTiles(List<Tile> tilesList) {
        List<Integer> numbers = new ArrayList<>(List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 12));
        Collections.shuffle(numbers);

        int numberIndex = 0;
        for (Tile tile : tilesList) {
            if (tile.getTileType() != TileType.DESERT) {
                tile.setNumber(numbers.get(numberIndex));
                numberIndex++;
                if (numberIndex >= numbers.size()) {
                    break; // Prevent going out of bounds
                }
            }
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
