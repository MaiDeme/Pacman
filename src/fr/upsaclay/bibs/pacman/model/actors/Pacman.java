package fr.upsaclay.bibs.pacman.model.actors;

import javax.sound.sampled.Clip;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.AbstractBoard;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.audio.SoundManager;
import fr.upsaclay.bibs.pacman.model.actors.BonusImpl;



public class Pacman extends AbstractActor {
    private int dotsEaten;
    private SoundManager soundManager;

    public Pacman(Board board) {
        super(board, ActorType.PACMAN);
        setDirection(Direction.LEFT);
        this.dotsEaten = 0;
        
    
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
        } else if (this.getBoard().getGameType() == GameType.TEST) {
            this.x = 35;
            this.y = 75;
        }
        setSpeed(board.getLevelPacManSpeed());

        this.setDirection(Direction.LEFT);
    }

    @Override
    public void setIntention(Direction direction) {
        if (direction == null) {
            return;
        }
        if (this.direction.reverse() == direction) {
            this.direction = direction;
            this.intention = null;
        } else if (this.isBlocked()
                && !this.getBoard().getMaze().getNeighbourTile(this.getCurrentTile(), direction).isWall()) {
            this.direction = direction;
            this.intention = null;
        } else {
            this.intention = direction;
        }
    }

    public void applySnapping() {
        // pour recentrer les actors
        // TODO a mettre dans abstract actors
        if (this.direction == Direction.UP || this.direction == Direction.DOWN) {
            x -= getRealX() % Maze.TILE_WIDTH - Maze.TITLE_CENTER_X;
        } else {
            y -= getRealY() % Maze.TILE_HEIGHT - Maze.TITLE_CENTER_Y;
        }

    }

    @Override
    public void setDirection(Direction direction) {
        super.setDirection(direction);
        applySnapping();
    }

    public boolean isMidTile() {
        // on verifie si pacman est a proximité du milieu d'une tuile
        // met en place le cornering si on a 3 pixel d'ecart
        double ecart = getSpeed() / 2 + 3.0;
        boolean iscenteredx = Math.abs(getRealX() % Maze.TILE_WIDTH - Maze.TITLE_CENTER_X) < ecart;
        boolean iscenteredy = Math.abs(getRealY() % Maze.TILE_WIDTH - Maze.TITLE_CENTER_Y) < ecart;
        return iscenteredx && iscenteredy;
    }

    @Override
    public void nextMove() {
        // On commence par calculer les coordonnées de depart et d'arrivée
        double x_depart = this.getRealX();
        double y_depart = this.getRealY();

        double x_arrivee = this.x + this.getDirection().getDx() * this.getSpeed();
        double y_arrivee = this.y + this.getDirection().getDy() * this.getSpeed();

        // Ensuite on récupère la tuile d'arrivée prédite et celle de départ
        TilePosition depart = this.getCurrentTile();
        Tile arrivee_tuile = this.getBoard().getMaze().getNeighbourTile(depart, this.direction);
    
        int tileX = 20;  // Remplacer 16 par la largeur réelle de vos tuiles
        int tileY = 13;  // Remplacer 16 par la hauteur réelle de vos tuiles
        TilePosition bonusPosition = new TilePosition(tileX, tileY);
        TilePosition pacmanTile = this.getCurrentTile();




        //On vérifie si Pacman est stoppé après avoir mangé un dot ou pas  if(this.stopTime == 0){
            // On réinitialise blocked si Pacman était bloqué auparavant
            this.blocked = false;

        // Pacman n'est pas bloqué, on vérifie s'il fait un mouvement circulaire
        if (direction == Direction.UP && y_arrivee < 0) {
            // Il fait un mouvement circulaire par le haut, sa nouvelle position y est la
            // hauteur du plateau moins la différence entre l'arrivée et -1
            this.y = getBoard().getMaze().getPixelHeight() - 1 - (y_arrivee + 1);
        } else if (direction == Direction.LEFT && x_arrivee < 0) {
            // Il fait un mouvement circulaire par la gauche, sa nouvelle position x est la
            // largeur du plateau moins la différence entre l'arrivée et -1
            this.x = getBoard().getMaze().getPixelWidth() - 1 - (x_arrivee + 1);
        } else if (direction == Direction.DOWN && y_arrivee > (getBoard().getMaze().getPixelHeight() - 1)) {
            // Il fait un mouvement circulaire par le bas, sa nouvelle position y est 0 +
            // différence hauteur du plateau et arrivée
            this.y = (y_arrivee - getBoard().getMaze().getPixelHeight());
        } else if (direction == Direction.RIGHT && x_arrivee > (getBoard().getMaze().getPixelWidth() - 1)) {
            // Il fait un mouvement circulaire par la droite, sa nouvelle position x est 0 +
            // différence largeur du plateau et arrivée
            this.x = (x_arrivee - getBoard().getMaze().getPixelWidth());
        } else {
            // Il ne fait pas de mouvement circulaire, on calcule sa position normalement en
            // multipliant par la vitesse
            this.setPosition(this.x + this.getDirection().getDx() * this.getSpeed(),
                    this.y + this.getDirection().getDy() * this.getSpeed());
        }

        //Maintenant que l'on a sa position on peut faire des actions par rapport à sa position d'arrivée : Après milieu de tuile (bloqué), milieu de tuile(intention)
        TilePosition pos = this.getCurrentTile();
        Tile tile = this.getBoard().getMaze().getTile(this.getCurrentTile());
        int score = this.getBoard().getScore();

                        // Vérifie si Pacman est sur la case du bonus
                        if (pacmanTile.equals(bonusPosition)) {
                            Bonus bonus = this.getBoard().getCurrentBonus();
                            if (bonus != null && bonus.isActive()) {
                                this.getBoard().setScore(this.getBoard().getScore() + bonus.getBonusType().getValue()); 
                                bonus.deactivate(); 
                                this.getBoard().removeBonus(); 
                                getBoard().playFruitEatSound();
                            }
                        }
        //D'abord on peut verifier si on est sur une case avec un dot

        if (tile.hasDot()){
            this.dotsEaten++;
            getBoard().playDotEatSound();
            switch (tile){
                case BD :
                    this.getBoard().setScore(score + 50);
                    setStopTime(3);
                    this.getBoard().getMaze().setTile(pos.getLine(), pos.getCol(), Tile.EE);

                            for (Ghost g : this.getBoard().getGhosts()) {

                                if (!g.getGhostState().equals(GhostState.DEAD)) {
                                    g.setPreviousGhostState(g.getGhostState());
                                    g.changeGhostState(GhostState.FRIGHTENED);
                                    this.getBoard().setEatGhost(0);
                                    g.setFrightenedCounter(0);

                                    
                                }
                            }
                            break;

                case SD:
                    this.getBoard().setScore(score + 10);
                    setStopTime(1);
                    this.getBoard().getMaze().setTile(pos.getLine(), pos.getCol(), Tile.EE);
                    break;
                case ND:
                    this.getBoard().setScore(score + 10);
                    setStopTime(1);
                    this.getBoard().getMaze().setTile(pos.getLine(), pos.getCol(), Tile.NT);
                    break;
            }

            this.getBoard().getMaze().setHigh_score(this.getBoard().getScore());
            // On vérifie si on a mangé assez de dots pour activer le bonus
            if (this.dotsEaten >= 7){
                // On active le bonus
                this.board.setBonusOnBoard();
                // On réinitialise le compteur de dots mangés
                dotsEaten = 0;
            }
        }

        // Ensuite on vérifie s'il est bloqué à la fin de son mouvement : Si il arrive
        // après le milieu dune tuile a la fin du deplacement, on verifie s il est
        // bloque et si oui on le remet a la position de depart
        if (arrivee_tuile.isWall() && ((this.getDirection() == Direction.RIGHT
                && this.getX() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_X)
                || (this.getDirection() == Direction.LEFT && (this.getX()) % Maze.TILE_WIDTH < Maze.TITLE_CENTER_X)
                || (this.getDirection() == Direction.DOWN && this.getY() % Maze.TILE_WIDTH > Maze.TITLE_CENTER_Y)
                || (this.getDirection() == Direction.UP && (this.getY()) % Maze.TILE_WIDTH < Maze.TITLE_CENTER_Y))) {
            this.setPosition(x_depart, y_depart);
            this.blocked = true;

        } else if (isMidTile()) {
            // Cas où il arrive au milieu d'une case : vérifie s'il a une intention: si on a
            // le droit de tourner : mise à jour intention, sinon met l'intention à null
            if (this.intention != null) {
                if (!this.getBoard().getMaze().getNeighbourTile(depart, this.intention).isWall()) {
                    this.setDirection(intention);
                    this.setPosition(x_arrivee, y_arrivee);
                    this.intention = null;
                } else {// sinon intention = null
                    this.intention = null;
                }
            }
        }
    }
}
