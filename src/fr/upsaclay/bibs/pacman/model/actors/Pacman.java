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
        //On commence par calculer les coordonnées de depart et d'arrivée
        double x_depart = this.getRealX();
        double y_depart = this.getRealY();

        double x_arrivee = this.x + this.getDirection().getDx() * this.getSpeed();
        double y_arrivee = this.y + this.getDirection().getDy() * this.getSpeed();

        //Ensuite on met à jour l'intention de PAcman et on récupère la tuile d'arrivée prédite
        TilePosition depart = this.getCurrentTile();
        setIntention(this.intention);
        Tile arrivee_tuile = this.getBoard().getMaze().getNeighbourTile(depart, this.Direction);

        //On vérifie si Pacman est stoppé après avoir mangé un dot ou pas
        if(stop_eat == 0){

            //On réinitialise blocked si Pacman était bloqué auparavant
            this.blocked = false;

            //Pacman n'est pas bloqué, on vérifie s'il fait un mouvement circulaire
            if(Direction == fr.upsaclay.bibs.pacman.model.Direction.UP && y_arrivee < 0){
                //Il fait un mouvement circulaire par le haut, sa nouvelle position y est la hauteur du plateau moins la différence entre l'arrivée et -1
                this.y = getBoard().getMaze().getPixelHeight()-1 - (y_arrivee +1);
            } else if(Direction == fr.upsaclay.bibs.pacman.model.Direction.LEFT && x_arrivee < 0){
                //Il fait un mouvement circulaire par la gauche, sa nouvelle position x est la largeur du plateau moins la différence entre l'arrivée et -1
                this.x = getBoard().getMaze().getPixelWidth()-1 - (x_arrivee +1);
            }else if (Direction == fr.upsaclay.bibs.pacman.model.Direction.DOWN && y_arrivee > (getBoard().getMaze().getPixelHeight()-1)) {
                //Il fait un mouvement circulaire par le bas, sa nouvelle position y est 0 + différence hauteur du plateau et arrivée
                this.y = (y_arrivee - getBoard().getMaze().getPixelHeight());
            } else if (Direction == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && x_arrivee > (getBoard().getMaze().getPixelWidth()-1)) {
                //Il fait un mouvement circulaire par la droite, sa nouvelle position x est 0 + différence largeur du plateau et arrivée
                this.x = (x_arrivee - getBoard().getMaze().getPixelWidth());
            }else {
                //Il ne fait pas de mouvement circulaire, on calcule sa position normalement en multipliant ar la vitesse
                setPosition(this.x + this.getDirection().getDx() * this.getSpeed(), this.y + this.getDirection().getDy() * this.getSpeed());
            }

            //Maintenant que l'on a sa position on peut faire des actions par rapport à sa position d'arrivée : Après milieu de tuile (bloqué), milieu de tuile(intention), début de tuile(manger)

            //Tout d'abord on vérifie s'il est bloqué à la fin de son mouvement : Si il arrive après le milieu dune tuile a la fin du deplacement, on  verifie s il est bloque et si oui on le remet a la position de depart
            if(arrivee_tuile.isWall() && ((this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && this.getX() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_X)
                    || (this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT && (this.getX()) % Maze.TILE_WIDTH < Maze.TITLE_CENTER_X)
                    ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.DOWN && this.getY() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_Y)
                    ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.UP && (this.getY()) % Maze.TILE_WIDTH < Maze.TITLE_CENTER_Y))) {
                this.setPosition(x_depart, y_depart);
                this.blocked = true;

            } else if (this.getX() % Maze.TILE_WIDTH == Maze.TITLE_CENTER_X  && this.getY() % Maze.TILE_HEIGHT == Maze.TITLE_CENTER_Y) {
                // Cas où il arrive au milieu d'une case : vérifie s'il a une intention: si on a le droit de tourner : mise à jour intention, sinon met l'intention à null
                if (this.intention != null) {
                    if (!this.getBoard().getMaze().getNeighbourTile(depart, this.intention).isWall()) {
                        this.setDirection(intention);
                        this.intention = null;
                    } else {//sinon intention = null
                        this.intention = null;
                    }
                }
            }else if ((this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.RIGHT && this.getX() % Maze.TILE_WIDTH == 0 )
                    || (this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.LEFT && (this.getX()) % Maze.TILE_WIDTH == 7)
                    ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.DOWN && this.getY() % Maze.TILE_WIDTH == 0)
                    ||(this.getDirection() == fr.upsaclay.bibs.pacman.model.Direction.UP && (this.getY()) % Maze.TILE_WIDTH == 7)) {
                //Si il arrive au début d'une case : vérifie s'il y a un dot a manger et si oui met a jour score et labyrinthe

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

        }else{
            //Pacman est bloqué après avoir mangé un dot , on le débloque de 1 frame
            stop_eat -= 1;
        }

    }
}
