package fr.upsaclay.bibs.pacman.model.board;

        import fr.upsaclay.bibs.pacman.GameType;
        import fr.upsaclay.bibs.pacman.PacManException;
        import fr.upsaclay.bibs.pacman.model.actors.ActorType;
import fr.upsaclay.bibs.pacman.model.actors.Blinky;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.actors.Inky;
import fr.upsaclay.bibs.pacman.model.actors.Pacman;
import fr.upsaclay.bibs.pacman.model.actors.Pinky;
import fr.upsaclay.bibs.pacman.model.maze.Grid;
        import fr.upsaclay.bibs.pacman.model.maze.Maze;

        import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ClassicBoard extends AbstractBoard {


    public ClassicBoard() {
        super(GameType.CLASSIC);
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void initialize() throws PacManException {
        super.initialize();
        //this.ghosts = new ArrayList<Ghost>();
        Ghost blinky = new Blinky(this, ActorType.GHOST );
        Ghost pinky = new Pinky(this, ActorType.GHOST);
        Ghost inky = new Inky(this, ActorType.GHOST);
        this.ghosts.add(blinky);
        this.ghosts.add(pinky);
        this.ghosts.add(inky);
        startActors();
    }

    @Override
    public void startActors() {
        super.startActors();


        for (Ghost ghost : this.ghosts) {
            ghost.start();
        }
    }


    @Override
    public void nextFrame(){
        this.pacman.nextFrame();

        for (Ghost g : this.ghosts){
            g.nextFrame();
        }

        this.setBoardState();

    }
}
