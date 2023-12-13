package edu.odtu.ceng453.group10.catanfrontend.game;

import org.springframework.stereotype.Component;

@Component
public class GameState {
    private Board board;

    public GameState() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }
}
