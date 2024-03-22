package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Grid;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Pacman extends AbstractActor {

    public Pacman(Board board) {
        super(board, ActorType.PACMAN);
        setDirection(fr.upsaclay.bibs.pacman.model.Direction.LEFT);
        start();
    }

    /**
     * Start the actor at the beginning of the game
     * Perform all necessary action to start the actor at the beginning of the game
     * (this often require to initialize some internal paramaters of the actor)
     */
    @Override
    public void start() {
        if (this.getBoard().getGameType() == GameType.CLASSIC) {
            this.x = 112;
            this.y = 211;
        } else {
            this.x = 35;
            this.y = 75;
        }

    }

    @Override
    public void setIntention(Direction direction) {
        if (this.Direction.reverse() == direction) {
            this.Direction = direction;
            this.intention = null;
        } else if (this.isBlocked()
                && !this.getBoard().getMaze().getNeighbourTile(this.getCurrentTile(), direction).isWall()) {
            this.Direction = direction;
            this.intention = null;
        } else {
            this.intention = direction;
        }
    }

    @Override
    public void nextMove() {
        // Si PacMan se trouve avant le milieu d'une tuile, il continue d'avancer dans
        // sa direction
        // si PacMan est au milieu de la tuile, il vérifie s'il a une "intention" et met
        // à jour sa direction
        // si PacMan a dépassé le milieu de la tuile, il vérifie qu'il peut continuer
        // d'avancer dans sa direction. Si ce n'est pas le cas, il arrête d'avancer, il
        // est bloqué
        TilePosition depart = this.getCurrentTile();

        //Pacman est avant le milieu de la tuile
        if (this.getX() % Maze.TILE_WIDTH < Maze.TITLE_CENTER_X && this.getY() % Maze.TILE_HEIGHT < Maze.TITLE_CENTER_Y){
            setPosition(x+this.getDirection().getDx(), y+this.getDirection().getDy());
            return;

        // Cas ou Pacman est au milieu de la tuile
        }else if (this.getX() %Maze.TILE_WIDTH == Maze.TITLE_CENTER_X && this.getY() % Maze.TILE_HEIGHT == Maze.TITLE_CENTER_Y) {
            if (this.intention != null) { // si Pacman a une intention

                // Cas où a le droit de tourner
                if (!this.getBoard().getMaze().getNeighbourTile(depart, this.intention).isWall()) {
                    //on avance et on met à jour la direction
                    setPosition(this.x + getDirection().getDx(), this.y + getDirection().getDy());
                    this.setDirection(intention);
                }else{
                    setPosition(this.x + getDirection().getDx(), this.y + getDirection().getDy());
                }
            }else{
                setPosition(this.x + getDirection().getDx(), this.y + getDirection().getDy());
            }

            this.intention = null;
            return;

        } else { // Pacman est après le milieu de la tuile
            if (!this.getBoard().getMaze().getNeighbourTile(depart, this.Direction).isWall()) {
                setPosition(this.x + getDirection().getDx(), this.y + getDirection().getDy());
            }
        }
    }
}
