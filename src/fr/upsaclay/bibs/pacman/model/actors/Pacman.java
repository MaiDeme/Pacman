package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Pacman extends AbstractActor{


    public Pacman(Board board){
        super(board, ActorType.PACMAN);
        start();
    }



    /**
     * Start the actor at the beginning of the game
     * Perform all necessary action to start the actor at the beginning of the game
     * (this often require to initialize some internal paramaters of the actor)
     */
    @Override
    public void start() {
        if(this.getBoard().getGameType() == GameType.CLASSIC){
            this.x = 112;
            this.y = 211;
        } else {
            this.x = 35;
            this.y = 75;
        }
        this.currentDirection = Direction.LEFT;
        this.intention = null;

    }

    @Override
    public void setIntention(Direction direction) {
        if(this.currentDirection.reverse() == direction){
            this.currentDirection = direction;
            this.intention = null;
        } else {
            this.intention = direction;
        }
    }
    @Override
    public void nextMove() {


        // Au milieu d'une case : verifie s'il a l'intention de tourner et s'il peut le faire il tourne
        TilePosition tile = this.getCurrentTile();
        if(tile.getLine() == Maze.TITLE_CENTER_Y && tile.getCol() == Maze.TITLE_CENTER_X){
            Tile nextTileFromIntention = this.getBoard().getMaze().getNeighbourTile(tile, this.intention);
            if(! nextTileFromIntention.isWall()){
                this.currentDirection = this.intention;
                this.intention = null;
            }
        }

        // Pacman pas blocké : il avance
        if(! this.isBlocked()) {
            this.x += this.currentDirection.getDx();
            this.y += this.currentDirection.getDy();
        }

    }
}
