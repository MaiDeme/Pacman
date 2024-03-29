package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Grid;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;



public class Pacman extends AbstractActor {
    private int stop_eat;



    public Pacman(Board board) {
        super(board, ActorType.PACMAN);
        start();
        setDirection(Direction.LEFT);
        stop_eat = 0;

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

        // si PacMan a dépassé le milieu de la tuile, il vérifie qu'il peut continuer d'avancer dans sa direction. Si ce n'est pas le cas, il arrête d'avancer, il est bloqué

        int x_depart = this.getX();
        int y_depart = this.getY();
        TilePosition depart = this.getCurrentTile();

        // d'abord on met a jour la direction (dans les cas particuliers ou c'est immmédiat si c'est pas possible rien ne change) puis on recupere la tuile d arrivee
        setIntention(this.intention);
        Tile arrivee_tuile = this.getBoard().getMaze().getNeighbourTile(depart, this.Direction);


        //On réinitialise blocked s il etait bloque auparavant
        this.blocked = false;


        //On commence par faire avancer Pacman dans sa direction puis ensuite on applique des conditions en fonction de sa position d arrivee
        if(stop_eat == 0) {
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
        }else{
            stop_eat -= 1;

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

        //Si il arrive après le milieu dune tuile a la fin du deplacement, on  verifie s il est bloque et si oui on le remet a la position de depart
        if(arrivee_tuile.isWall() && ((this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && this.getX() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_X)
                || (this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT && (this.getX()) % Maze.TILE_WIDTH < Maze.TITLE_CENTER_X)
                ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.DOWN && this.getY() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_Y)
                ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.UP && (this.getY()) % Maze.TILE_WIDTH < Maze.TITLE_CENTER_Y))) {
            this.setPosition(x_depart, y_depart);
            this.blocked = true;
        }


        //Si il arrive au début d'une case : vérifie s'il y a un dot a manger et si oui met a jour score et labyrinthe
        if ((this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && this.getX() % Maze.TILE_WIDTH == 0 )
                || (this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT && (this.getX()) % Maze.TILE_WIDTH == 7)
                ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.DOWN && this.getY() % Maze.TILE_WIDTH == 0)
                ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.UP && (this.getY()) % Maze.TILE_WIDTH == 7)) {
            TilePosition pos = this.getCurrentTile();
            int score = this.getBoard().getScore();
            if (this.getBoard().getMaze().getTile(this.getCurrentTile()) == Tile.SD) {
                this.getBoard().setScore(score + 10);
                this.getBoard().getMaze().setTile(pos.getLine(), pos.getCol(), Tile.EE);
                stop_eat = 1;

            } else if (this.getBoard().getMaze().getTile(this.getCurrentTile()) == Tile.ND) {
                this.getBoard().setScore(score + 10);
                this.getBoard().getMaze().setTile(pos.getLine(), pos.getCol(), Tile.NT);
                stop_eat = 1;

            } else if (this.getBoard().getMaze().getTile(this.getCurrentTile()) == Tile.BD) {
                this.getBoard().setScore(score + 50);
                this.getBoard().getMaze().setTile(pos.getLine(), pos.getCol(), Tile.EE);
                stop_eat = 3;

            }
        }



    }
}
