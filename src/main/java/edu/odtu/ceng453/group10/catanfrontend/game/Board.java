package edu.odtu.ceng453.group10.catanfrontend.game;

import javafx.geometry.Point2D;
import java.util.*;
import java.util.stream.Collectors;

import edu.odtu.ceng453.group10.catanfrontend.game.Tile;


public class Board {
    private final List<Tile> tiles;
    private final int[] rowLengths = {3, 4, 5, 4, 3}; // Hexagons per row
    private Map<String, Vertex> vertexMap = new HashMap<>();


    public Board() {
        tiles = initializeTiles();
        initializeNetwork();
    }

    private List<Tile> initializeTiles() {
        List<Tile> tilesList = new ArrayList<>();

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

        // Assign numbers to the tiles
        assignNumbersToTiles(tilesList);

        int tileIndex = 0;
        for (int row = 0; row < rowLengths.length; row++) {
            for (int col = 0; col < rowLengths[row]; col++) {
                Tile tile = tilesList.get(tileIndex++);
                tile.setRow(row);
                tile.setCol(col);
            }
        }

                return tilesList;
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

    private void initializeNetwork() {
        int tileIndex = 0;
        for (int row = 0; row < rowLengths.length; row++) {
            for (int col = 0; col < rowLengths[row]; col++) {
                Tile tile = tiles.get(tileIndex++);
                List<Vertex> tileVertices = new ArrayList<>();
                List<Edge> tileEdges = new ArrayList<>();

                // Assuming each tile has 6 vertices and 6 edges
                for (int i = 0; i < 6; i++) {
                    Vertex vertex = findOrCreateVertexAt(tile, i);
                    tileVertices.add(vertex);

                    Edge edge = findOrCreateEdgeBetween(tile, i, (i + 1) % 6);
                    tileEdges.add(edge);
                }

                tile.setVertices(tileVertices);
                tile.setEdges(tileEdges);
            }
        }
        for (Tile tile : tiles) {
            List<Vertex> tileVertices = tile.getVertices();
            for (int i = 0; i < tileVertices.size(); i++) {
                Vertex currentVertex = tileVertices.get(i);

                // Connect the vertex to its adjacent edges
                Edge edge1 = findOrCreateEdgeBetween(tile, i, (i + 1) % 6);
                Edge edge2 = findOrCreateEdgeBetween(tile, i, (i + 6 - 1) % 6);
                currentVertex.addConnectedEdge(edge1);
                currentVertex.addConnectedEdge(edge2);
            }
        }
    }

    private Point2D calculateVertexPosition(Tile tile, int vertexIndex) {
        Point2D tileCenter = tile.getCenterPosition();
        double angleRad = 2 * Math.PI / 6 * vertexIndex;
        double x = tileCenter.getX() + Tile.RADIUS * Math.cos(angleRad);
        double y = tileCenter.getY() + Tile.RADIUS * Math.sin(angleRad);
        return new Point2D(x, y);
    }

    private Vertex findOrCreateVertexAt(Tile tile, int vertexIndex) {
        // Create a unique key based on the tile's position and vertex index
        String vertexKey = tile.getRow() + "-" + tile.getCol() + "-" + vertexIndex;

        // Check if the vertex already exists
        if (vertexMap.containsKey(vertexKey)) {
            return vertexMap.get(vertexKey);
        }
        Point2D pos =  calculateVertexPosition(tile, vertexIndex);
        // Create a new vertex if it doesn't exist
        Vertex newVertex = new Vertex(pos);
        vertexMap.put(vertexKey, newVertex);
        return newVertex;
    }

    private Map<String, Edge> edgeMap = new HashMap<>();

    private Edge findOrCreateEdgeBetween(Tile tile, int vertex1Index, int vertex2Index) {
        // Create unique keys for the vertices
        String vertex1Key = tile.getRow() + "-" + tile.getCol() + "-" + vertex1Index;
        String vertex2Key = tile.getRow() + "-" + tile.getCol() + "-" + vertex2Index;

        // Create a unique key for the edge
        String[] edgeVertices = {vertex1Key, vertex2Key};
        Arrays.sort(edgeVertices); // Ensures the key is the same regardless of vertex order
        String edgeKey = edgeVertices[0] + "-" + edgeVertices[1];

        // Check if the edge already exists
        if (edgeMap.containsKey(edgeKey)) {
            return edgeMap.get(edgeKey);
        }

        // Create a new edge if it doesn't exist
        Edge newEdge = new Edge(vertexMap.get(vertex1Key), vertexMap.get(vertex2Key));
        edgeMap.put(edgeKey, newEdge);
        return newEdge;
    }
    public List<Vertex> getAvailableVertices() {
        return vertexMap.values().stream()
                .filter(Vertex::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Edge> getConnectedAvailableEdges(Vertex vertex) {
        return vertex.getConnectedEdges().stream()
                .filter(Edge::isAvailable)
                .collect(Collectors.toList());
    }
}
