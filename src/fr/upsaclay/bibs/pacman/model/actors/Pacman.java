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


        TilePosition depart = this.getCurrentTile();

        // d'abord on met a jour la direction dans les cas particuliers ou c'est immmédiat si c'est pas possible rien ne change


        //Ensuite on definie la tuile d'arrivee qui dépend des conditions d'intention
        Tile arrivee_tuile = this.getBoard().getMaze().getNeighbourTile(depart, this.Direction);
        setIntention(this.intention);

        this.blocked = true;
        //En terme de positions : 3 cas a gerer qui dependent de sa direction : Verticale ou Horizontale

        if (this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT || this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT) {
            //cas ou il bouge horizontalement
            //Cas ou il est bloqué (a passe le milieu de tuile + prochine tuile est un mur
            if ((this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && this.getX() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_X
                    || this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT && this.getX() % Maze.TILE_WIDTH < Maze.TITLE_CENTER_X)
                    && arrivee_tuile.isWall()) {
                this.blocked = true;
            } else if ((this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.DOWN && this.getX() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_X
                    || this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.UP && this.getX() % Maze.TILE_WIDTH < Maze.TITLE_CENTER_X)
                    && arrivee_tuile.isWall()) {
                this.blocked = true;

            } else {
                //Dans ce cas soit la prochaine tuile n'est pas un mur soit pacman n a pas depasse le milieu de la tuile

                //On commence par réinitialisé blocked si il etait precedemment bloqué
                this.blocked = false;

                if (Direction == Direction.UP && this.y == 0) {
                    this.y = getBoard().getMaze().getPixelHeight() - 1;
                } else if (Direction == Direction.DOWN && this.y == getBoard().getMaze().getPixelHeight() - 1) {
                    this.y = 0;
                } else if (Direction == Direction.LEFT && this.x == 0) {
                    this.x = getBoard().getMaze().getPixelWidth() - 1;
                } else if (Direction == Direction.RIGHT && this.x == getBoard().getMaze().getPixelWidth() - 1) {
                    this.x = 0;
                } else {
                    setPosition(this.x + this.getDirection().getDx(), this.y + this.getDirection().getDy());
                }
            }
        }


        //Maintenat qu'il s'est déplacé on peut regarder s'il faut mettre à jour la direction pour le tour d apres
        //On commence par verifier qu'il soit au milieu de la tuile
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



    }
}
