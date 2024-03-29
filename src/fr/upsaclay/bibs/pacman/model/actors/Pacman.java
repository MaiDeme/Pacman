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
        start();
        setDirection(Direction.LEFT);
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
        if (direction == null) {
            return;
        }
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

        int x_depart = this.getX();
        int y_depart = this.y;
        TilePosition depart = this.getCurrentTile();
        // d'abord on met a jour la direction dans les cas particuliers ou c'est immmédiat
        // si c'est pas possible rien ne change
        setIntention(this.intention);
        Tile arrivee_tuile = this.getBoard().getMaze().getNeighbourTile(depart, this.Direction);



        // si Pacman n'est pas bloqué il avance dans sa direction qu'il soit avant ou après le milieu de la tuile
        if (!arrivee_tuile.isWall() || (!(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && this.getX() % Maze.TILE_WIDTH == 7)
                    && !(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT && (1+ this.getX()) % Maze.TILE_WIDTH == 0)
                    && !(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.DOWN && this.getY() % Maze.TILE_WIDTH == 7)
                    && !(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.UP && (this.getY()+1) % Maze.TILE_WIDTH == 0)

        )) {
            this.blocked = false;

            if (Direction == Direction.UP && this.y==0){
                this.y = getBoard().getMaze().getPixelHeight()-1;
            }else if (Direction == Direction.DOWN && this.y==getBoard().getMaze().getPixelHeight()-1){
                this.y = 0;
            }else if (Direction == Direction.LEFT && this.x==0){
                this.x = getBoard().getMaze().getPixelWidth()-1;
            }else if (Direction == Direction.RIGHT && this.x==getBoard().getMaze().getPixelWidth()-1){
                this.x = 0;
            }else{
                setPosition(this.x + this.getDirection().getDx(), this.y + this.getDirection().getDy());
            }
        }else{

        }



        // si il arrive au milieu d'une tuile a la fin du deplacement
        if (this.getX() % Maze.TILE_WIDTH == Maze.TITLE_CENTER_X
                && this.getY() % Maze.TILE_HEIGHT == Maze.TITLE_CENTER_Y) {
                    //si intention n'est pas null
            if (this.intention != null) {
                // si on a le droit de tourner
                if (!this.getBoard().getMaze().getNeighbourTile(depart, this.intention).isWall()) {
                    // on met à jour la direction
                    this.setDirection(intention);
                    this.intention = null;
                } else {
                    //sinon intention = null
                    this.intention = null;

                }

            }
        }

        if(x_depart == this.getX() && this.getY() == y_depart && arrivee_tuile.isWall() == true){
            if(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && this.getX() % Maze.TILE_WIDTH == 7
                || this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT && (1+ this.getX()) % Maze.TILE_WIDTH == 0
                ||this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.DOWN && this.getY() % Maze.TILE_WIDTH == 7
                ||this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.UP && (this.getY()+1) % Maze.TILE_WIDTH == 0){

                    this.blocked = true;
                }
            }


    }
}
