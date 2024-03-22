package fr.upsaclay.bibs.pacman.model.board;
import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.maze.Maze;

import java.io.FileNotFoundException;

public class ClassicBoard extends AbstractBoard {
    public ClassicBoard() {
        super(GameType.CLASSIC);
    }

    @Override
    public GameType getGameType(){
        return GameType.CLASSIC;
    }

    @Override
    public void initialize() throws PacManException {
        try {
            this.maze = Maze.loadFromFile("maze.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
