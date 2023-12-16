package edu.odtu.ceng453.group10.catanfrontend.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameState {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private int[] lastDiceRoll;
    private boolean diceRolled;

    public GameState() {
        board = new Board();
        players = new ArrayList<Player>();
        lastDiceRoll = new int[2];
        lastDiceRoll[0] = 1;
        lastDiceRoll[1] = 1;
        diceRolled = false;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
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
    public void initializeSingleGame(String username) {
        players.add(new Player(username, new ResourceCardDeck()));
        players.add(new Player("AI1", new ResourceCardDeck()));
        players.add(new Player("AI2", new ResourceCardDeck()));
        players.add(new Player("AI3", new ResourceCardDeck()));
        randomStartingPlayer();
    }
    private void randomStartingPlayer() {
        currentPlayerIndex = (int) (Math.random() * 4);
    }
}
