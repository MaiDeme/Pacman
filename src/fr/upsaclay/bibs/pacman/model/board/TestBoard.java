package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.actors.ActorType;
import fr.upsaclay.bibs.pacman.model.actors.Pacman;
import fr.upsaclay.bibs.pacman.model.maze.Grid;
import fr.upsaclay.bibs.pacman.model.maze.Maze;

import java.io.FileNotFoundException;

public class TestBoard extends AbstractBoard {


    public TestBoard() {
        super(GameType.TEST);
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public void initialize() throws PacManException {
        super.initialize();
        startActors();
    }

}
