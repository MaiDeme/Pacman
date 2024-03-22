package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Grid;
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
        this.Direction = Direction.LEFT;

    }

    @Override
    public void setIntention(Direction direction) {
        if(this.Direction.reverse() == direction) {
            this.Direction = direction;
            this.intention = null;
        } else if (this.isBlocked() && !this.getBoard().getMaze().getNeighbourTile(this.getCurrentTile(), direction).isWall()) {
            this.Direction = direction;
            this.intention = null;
        } else {
            this.intention = direction;
        }
    }
    @Override
    public void nextMove() {

        TilePosition depart = this.getCurrentTile();

        // Cas ou l intention nest pas nulle.
        if (this.intention != null) {
            Tile nextTileFromIntention = this.getBoard().getMaze().getNeighbourTile(depart, this.intention);

            //Verifie en milieu de tuile
            if ((this.getX()+1)%3 == 1 && (this.getY()+1)%3 ==1 ){

                //Cas où a le droit de tourner
                if (!nextTileFromIntention.isWall()){

                    this.intention.getDx();

                    this.intention.getDy();

                }else{
                    this.intention = null;
                }

            }
        }else{
            this.x += this.Direction.getDx();

            this.y += this.Direction.getDy();

        }




    }
}
