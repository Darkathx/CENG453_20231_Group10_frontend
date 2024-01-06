package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.GameController;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;


@Component
public class GameState {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private int[] lastDiceRoll;
    private boolean diceRolled;
    private int longestPathLength;
    private Player longestPathPlayer;


    public GameState() {
        board = new Board();
        players = new ArrayList<Player>();
        lastDiceRoll = new int[2];
        lastDiceRoll[0] = 1;
        lastDiceRoll[1] = 1;
        diceRolled = false;
        longestPathLength = 0;
        longestPathPlayer = null;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getLongestPathPlayer() {
        return longestPathPlayer;
    }

    public void setLongestPathPlayer(Player player) {
        this.longestPathPlayer = player;
    }

    public int[] getLastDiceRoll() {
        return lastDiceRoll;
    }

    public void setLastDiceRoll(int[] lastDiceRoll) {
        this.lastDiceRoll = lastDiceRoll;
    }

    public boolean getDiceRolled() {
        return diceRolled;
    }

    public void setDiceRolled() {
        this.diceRolled = true;
    }

    public void unsetDiceRolled() {
        this.diceRolled = false;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    public static final Color[] PLAYER_COLORS = {
            Color.RED, Color.BLUE, Color.PURPLE, Color.MAGENTA
    };
    public void initializeSingleGame(String username) {
        players.add(new Player(username, new ResourceCardDeck(), PLAYER_COLORS[0]));

        players.add(new Player("AI1", new ResourceCardDeck(),PLAYER_COLORS[1]));
        players.add(new Player("AI2", new ResourceCardDeck(), PLAYER_COLORS[2]));
        players.add(new Player("AI3", new ResourceCardDeck(), PLAYER_COLORS[3]));
        randomStartingPlayer();
    }
    private void randomStartingPlayer() {
        currentPlayerIndex = (int) (Math.random() * 4);
    }

    public int getLongestPathLength() {
        return longestPathLength;
    }

    public void setLongestPathLength(int longestPathLength, Player player) {
        this.longestPathLength = longestPathLength;
        this.longestPathPlayer = player;
    }
}
