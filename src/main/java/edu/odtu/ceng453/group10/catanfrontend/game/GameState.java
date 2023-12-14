package edu.odtu.ceng453.group10.catanfrontend.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameState {
    private Board board;
    private List<Player> players;

    public GameState() {
        board = new Board();
        players = new ArrayList<Player>();
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
