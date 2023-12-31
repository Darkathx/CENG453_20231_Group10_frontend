package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.GameController;
import javafx.geometry.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import edu.odtu.ceng453.group10.catanfrontend.config.Settings;
import java.util.logging.Logger;

import edu.odtu.ceng453.group10.catanfrontend.game.Tile;


public class Board {
    private static List<Tile> tiles;
    private static final Logger LOGGER = Logger.getLogger(Board.class.getName());

    private final int[] rowLengths = {3, 4, 5, 4, 3}; // Hexagons per row
    private Map<String, Vertex> vertexMap = new HashMap<>();
    private Map<String, Edge> edgeMap = new HashMap<>();


    public Board() {
        tiles = initializeTiles();
        initializeNetwork();
        LOGGER.info("Number of vertices: " + vertexMap.size());
    }
    private List<Tile> initializeTiles() {
        List<Tile> tilesList = new ArrayList<>();
        createTiles(tilesList);
        assignNumbersToTiles(tilesList);
        setTilePositions(tilesList);
        this.setTiles(tilesList);
        return tilesList;
    }
    private void createTiles(List<Tile> tilesList){
        // Create one desert tile without a resource type or number
        tilesList.add(new Tile(TileType.DESERT, null, null, 0, 0));

        // Add other types of tiles
        addTilesOfType(tilesList, TileType.HILL, 3, ResourceType.BRICK);
        addTilesOfType(tilesList, TileType.MOUNTAIN, 3, ResourceType.ORE);
        addTilesOfType(tilesList, TileType.FIELD, 4, ResourceType.GRAIN);
        addTilesOfType(tilesList, TileType.PASTURE, 4, ResourceType.WOOL);
        addTilesOfType(tilesList, TileType.FOREST, 4, ResourceType.LUMBER);

        // Shuffle the tiles, ensuring the desert is in the center
        Collections.shuffle(tilesList);
        moveDesertToCenter(tilesList);
    }

    private void setTilePositions(List<Tile> tilesList) {
        // Set positions of tiles based on row and column
        double maxWidth = 10*tilesList.get(0).getApothem();
        double totalHeight = 8* tilesList.get(0).getRadius();
        double startY = (Settings.getHeight() - totalHeight) / 2;
        int tileIndex = 0;
        for (int row = 0; row < rowLengths.length; row++) {
            double currentRowWidth = rowLengths[row] * (tilesList.get(0).getApothem()*2);
            double startX = (Settings.getWidth() - maxWidth) / 2 + (maxWidth - currentRowWidth) / 2;
            for (int col = 0; col < rowLengths[row]; col++) {
                Tile tile = tilesList.get(tileIndex++);
                double x = startX + col * tile.getApothem()*2;
                double y = startY + row * (tile.getRadius() * 3 / 2);
                Point2D center = new Point2D(x + tile.getRadius(), y + tile.getRadius());
                tile.setCenter(center);
                tile.setCol(col);
                tile.setRow(row);
                tile.getHexagon().setTranslateX(x);
                tile.getHexagon().setTranslateY(y);
            }
        }
        setTiles(tilesList);
    }
    private void addTilesOfType(List<Tile> tilesList, TileType type, int count, ResourceType resource) {
        for (int i = 0; i < count; i++) {
            tilesList.add(new Tile(type, resource, 0,0 , 0)); // Initialize with dummy number 0
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
        List<Integer> numbers = new ArrayList<>(List.of(2, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 12));
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
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
    private void initializeNetwork() {
        for (Tile tile : tiles) {
            // Iterate over each vertex index for a hexagon (6 sides)
            for (int i = 0; i < 6; i++) {
                Vertex vertex1 = findOrCreateVertexAt(tile, i);
                Vertex vertex2 = findOrCreateVertexAt(tile, (i + 1) % 6);
                Edge edge = findOrCreateEdgeBetween(vertex1, vertex2);
                tile.addVertex(vertex1);
                tile.addEdge(edge);

                // Connect the vertex to its adjacent edges
                vertex1.addConnectedEdge(edge);
                vertex2.addConnectedEdge(edge);
            }
        }

    }

    private void connectVerticesWithEdges() {
        for (Vertex vertex : vertexMap.values()) {
            for (Edge edge : edgeMap.values()) {
                if (edge.involves(vertex)) {
                    vertex.addConnectedEdge(edge);
                }
            }
        }
    }
    private Point2D calculateVertexPosition(Tile tile, int vertexIndex) {
        Point2D tileCenter = tile.getCenterPosition();
        double angleRad = vertexIndex*2 * Math.PI / 6 +Math.PI/6;
        double x = tileCenter.getX() + (Tile.RADIUS) * Math.cos(angleRad);
        double y = tileCenter.getY() + (Tile.RADIUS) * Math.sin(angleRad);
        return new Point2D(x, y);
    }
    private String generateVertexKey(int row, int col, int vertexIndex) {
        return row + "-" + col + "-" + vertexIndex;
    }
    private Vertex findOrCreateVertexAt(Tile tile, int vertexIndex) {
        String vertexKey = Math.round((calculateVertexPosition(tile, vertexIndex).getX())) + " " +Math.round((calculateVertexPosition(tile, vertexIndex).getY()));
        if (vertexMap.containsKey(vertexKey)) {
            Vertex existingVertex = vertexMap.get(vertexKey);
            existingVertex.addAdjacentTile(tile);
            return existingVertex;
        } else {
            Point2D position = calculateVertexPosition(tile, vertexIndex);
            Vertex newVertex = new Vertex(position, tile.getRow(), tile.getCol(), vertexIndex);
            newVertex.addAdjacentTile(tile);
            newVertex.setKey(vertexKey);
            LOGGER.info("Created new vertex: " + newVertex.getKey());
            vertexMap.put(vertexKey, newVertex);
            return newVertex;
        }
    }

    public Vertex getVertex(String key) {
        return vertexMap.get(key);
    }

    public Edge getEdge(String key, String key2) {
        String edgeKey = key.compareTo(key2) < 0 ? key + "-" + key2 : key2 + "-" + key;
        return edgeMap.get(edgeKey);
    }


    private Edge findOrCreateEdgeBetween(Vertex vertex1, Vertex vertex2) {
        String vertex1Key = vertex1.getKey(); // Assuming Vertex class has a method to get a unique key
        String vertex2Key = vertex2.getKey(); // Same as above

        // Create a unique key for the edge based on vertex keys
        String edgeKey = vertex1Key.compareTo(vertex2Key) < 0 ? vertex1Key + "-" + vertex2Key : vertex2Key + "-" + vertex1Key;

        // Check if the edge already exists in the map
        if (edgeMap.containsKey(edgeKey)) {
            return edgeMap.get(edgeKey);
        }

        // Create a new edge if it doesn't exist and add it to the map
        Edge newEdge = new Edge(vertex1, vertex2);
        edgeMap.put(edgeKey, newEdge);
        return newEdge;
    }

    public List<Vertex> getAvailableVertices() {
        List<Vertex> availableVertices = vertexMap.values().stream()
                .filter(Vertex::isAvailable)
                .collect(Collectors.toList());

        return availableVertices;
    }

    public List<Edge> getConnectedAvailableEdges(Vertex vertex) {
        return vertex.getConnectedEdges().stream()
                .filter(Edge::isAvailable)
                .collect(Collectors.toList());
    }

}
