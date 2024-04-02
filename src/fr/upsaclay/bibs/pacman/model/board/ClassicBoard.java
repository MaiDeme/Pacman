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
import fr.upsaclay.bibs.pacman.model.actors.Clyde;
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
        Ghost blinky = new Blinky(this, ActorType.GHOST);
        Ghost pinky = new Pinky(this, ActorType.GHOST);
        Ghost inky = new Inky(this, ActorType.GHOST);
        Ghost clyde = new Clyde (this, ActorType.GHOST);
        this.ghosts.add(blinky);
        this.ghosts.add(pinky);
        this.ghosts.add(inky);
        this.ghosts.add(clyde);
        startActors();
    }

    @Override
    public void startActors() {

        //On réinitialise la vitesse pour le cas ou le start actor se fait quand les fantomes sont sur des cases lentes
        super.startActors();
        pacman.setSpeed(1);

        for (Ghost ghost : this.ghosts) {
            ghost.start();
            ghost.setSpeed(ghost.getDefaultSpeed());
        }
    }


    @Override
    public void nextFrame(){
        this.pacman.nextFrame();

        for (Ghost g : this.ghosts){
            g.nextFrame();
        }

        if (this.getMaze().getNumberOfDots() == 0) {
            setBoardState(BoardState.LEVEL_OVER);
        } else if (this.isEaten()) {
            setBoardState(BoardState.LIFE_OVER);
        } else if (this.getNumberOfLives() == 0) {
            setBoardState(BoardState.GAME_OVER);
        }
    }

    
}