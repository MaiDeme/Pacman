package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.AbstractBoard;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.view.BoardView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;
import fr.upsaclay.bibs.pacman.view.PacManView;

import javax.swing.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.max;

public class SimpleController implements Controller{


    public GameType gameType;
    public Board board;
    public PacManView view;
    public static final int INITIAL_DELAY = 17;

    public SimpleController(GameType gameType) {
        this.gameType = gameType;

    }

    @Override
    public void initialize() throws PacManException {

        Maze maze = board.getMaze();

        BoardView view = new BoardView("Plateau de jeu ", maze.getPixelWidth(), maze.getPixelHeight());*
        view.setLoopDelay(INITIAL_DELAY);

    }

    @Override
    public void initializeNewGame() throws PacManException {

        view.update();

        board = new AbstractBoard(T);

        view.setBoard(board);


        view.setController(this);
        view.initialize();
        view.setLayout(PacManLayout.INIT);
    }

    @Override
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    @Override
    public GameType getGameType() {
        return this.gameType;
    }

    @Override
    public void receiveAction(GameAction action) throws PacManException {
        switch (action) {
            case PAUSE: pause(); break;
        }
        switch (action) {
            case START:
                view.setLayout(PacManLayout.GAME_ON); break;
        }

        view.update();

    }


    private void pause(){
        view.setLayout(PacManLayout.PAUSE);
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}





