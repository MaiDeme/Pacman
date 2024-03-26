package fr.upsaclay.bibs.pacman.model.board;

        import fr.upsaclay.bibs.pacman.GameType;
        import fr.upsaclay.bibs.pacman.PacManException;
        import fr.upsaclay.bibs.pacman.model.actors.ActorType;
        import fr.upsaclay.bibs.pacman.model.actors.Pacman;
        import fr.upsaclay.bibs.pacman.model.maze.Grid;
        import fr.upsaclay.bibs.pacman.model.maze.Maze;

        import java.io.FileNotFoundException;

public class ClassicBoard extends AbstractBoard {


    public ClassicBoard() {
        super(GameType.CLASSIC);
    }

}

