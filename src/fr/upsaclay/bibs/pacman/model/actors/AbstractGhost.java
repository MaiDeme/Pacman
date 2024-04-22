package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import static java.lang.Integer.MAX_VALUE;

public abstract class AbstractGhost extends AbstractActor implements Ghost {

    protected GhostState currentState;
    protected GhostState previousState;
    protected  GhostPenState currentPenState;
    protected int FrightenedCounter;
    protected double stateCounter;
    protected int elroy;


    public AbstractGhost(Board board, ActorType type) {
        super(board, type);
        super.setSpeed(board.getLevelGhostSpeed());
        this.currentState = GhostState.SCATTER;

    }

    @Override
    public abstract void start();

    public void setPreviousGhostState(GhostState previousGhostState){
        if (!previousGhostState.equals(GhostState.FRIGHTENED) && !previousGhostState.equals(GhostState.FRIGHTENED_END)) {
            this.previousState = previousGhostState;
        }

    }

    public GhostState getPreviousGhostState(){
        return this.previousState;
    }

    @Override
    public void nextMove() {

        if (this.currentPenState.equals(GhostPenState.OUT)) {
            double x_depart = this.x;
            double y_depart = this.y;
            double x_arrivee = this.x + this.getDirection().getDx() * this.getSpeed();
            double y_arrivee = this.y + this.getDirection().getDy() * this.getSpeed();


            // on vérifie s'il fait un mouvement circulaire
            if (direction == Direction.LEFT && x_arrivee < 0) {
                //Il fait un mouvement circulaire par la gauche, sa nouvelle position x est la largeur du plateau moins la différence entre l'arrivée et -1
                this.setPosition(getBoard().getMaze().getPixelWidth() - 1 - (x_arrivee + 1), y_arrivee);
            } else if (direction == Direction.RIGHT && x_arrivee > (getBoard().getMaze().getPixelWidth() - 1)) {
                //Il fait un mouvement circulaire par la droite, sa nouvelle position x est 0 + différence largeur du plateau et arrivée
                this.setPosition((x_arrivee - getBoard().getMaze().getPixelWidth()), y_arrivee);
            } else {
                //Il ne fait pas de mouvement circulaire, on calcule sa position normalement en multipliant par la vitesse
                this.setPosition(x_arrivee, y_arrivee);
            }


        //S'il est sur une tuile à vitesse lente
        if (this.getBoard().getMaze().getTile(this.getCurrentTile()) == Tile.SL) {
            if (getSpeed() == getDefaultSpeed()) {
                this.setSpeed(this.getSlowSpeed());
            }
        } else {
            if (getSpeed() == this.getSlowSpeed()) {
                this.setSpeed(getDefaultSpeed());
            }
        }

            // Quand il rejoint le centre d'une tuile :  il calcule sa nouvelle intention ou direction
            if (this.getX() % Maze.TILE_WIDTH == Maze.TITLE_CENTER_X
                    && this.getY() % Maze.TILE_HEIGHT == Maze.TITLE_CENTER_Y) {
                this.direction = this.intention;

                if (this.getGhostState().equals(GhostState.FRIGHTENED_END) || this.getGhostState().equals(GhostState.FRIGHTENED)) {
                    if (this.getBoard().getMaze().IsIntersection(this.getCurrentTile(), this.getDirection())) {
                        Direction dir = this.getDirection().reverse();

                        while (dir.reverse().equals(this.getDirection())
                                || this.getBoard().getMaze().getNeighbourTile(this.getCurrentTile(), dir).isWall()
                                || (dir.equals(Direction.UP) && !this.getBoard().getMaze().getTile(this.getCurrentTile()).ghostCanGoUp())) {
                            dir = this.getBoard().getRandomDirection();
                        }
                        this.setIntention(dir);
                        this.direction = this.intention;
                    }
                } else {
                    //il applique son intention et met donc à jour sa direction
                    this.intention = getNextIntention(this.getCurrentTile());
                }
            }
        }

        // S'il est mort et devant l'enclos il y entre
        if (this.currentState.equals(GhostState.DEAD)) {
            if (this.getX() == board.outPenXPosition() && this.getY() == board.outPenYPosition()) {
                this.setGhostPenState(GhostPenState.GET_IN);
                this.setGhostState(this.previousState);
            }
        }

         if (this.currentPenState.equals(GhostPenState.GET_IN)) {
             this.setGhostState(this.previousState);
            if (this.getY() == board.penGhostYPosition(this.getGhostType())) {
                if (this.getX() == board.penGhostXPosition(this.getGhostType())) {
                    this.setGhostPenState(GhostPenState.IN);

                } else {
                    if (this.getX() - board.penGhostXPosition(this.getGhostType()) > 0) {
                        setDirection(Direction.LEFT);
                    } else {
                        setDirection(Direction.RIGHT);
                    }
                    double x_arrivee = this.x + this.getDirection().getDx() * this.getSpeed();
                    double y_arrivee = this.y + this.getDirection().getDy() * this.getSpeed();
                    setPosition(x_arrivee, y_arrivee);
                }
            } else {
                setDirection(Direction.DOWN);
                double x_arrivee = this.x + this.getDirection().getDx() * this.getSpeed();
                double y_arrivee = this.y + this.getDirection().getDy() * this.getSpeed();
                setPosition(x_arrivee, y_arrivee);
            }
        }

        if (this.currentPenState.equals(GhostPenState.IN)) {
            // version très simple, si on est dedans on sort

            setGhostPenState(GhostPenState.GET_OUT);
        }
        if (this.currentPenState.equals(GhostPenState.GET_OUT)){
            if (this.getX() == board.outPenXPosition()) {
                if (this.getY() == board.outPenYPosition()) {
                    this.setGhostPenState(GhostPenState.OUT);
                    setSpeed(this.getDefaultSpeed());
                    setDirection(getOutOfPenDirection());
                } else {
                    setDirection(Direction.UP);
                }
            } else {
                if (this.getX() - board.outPenXPosition() > 0) {
                    setDirection(Direction.LEFT);
                } else {
                    setDirection(Direction.RIGHT);
                }
            }
            double x_arrivee = this.x + this.getDirection().getDx() * this.getSpeed();
            double y_arrivee = this.y + this.getDirection().getDy() * this.getSpeed();
            setPosition(x_arrivee, y_arrivee);
        }
    }
    
        
    


    public Direction getNextIntention(TilePosition depart) {

        if (this.getGhostState().equals(GhostState.FRIGHTENED_END) || this.getGhostState().equals(GhostState.FRIGHTENED)){
            return null;
        }

        //Il choisit la tuile possible qui le rapproche le plus de sa tuile cible (selon la distance euclidienne)
        //Pour cela, il regarde où il peut aller à partir de la prochaine tuile sachant qu'il n'a pas le droit de revenir en arrière ni de traverser les murs
        TilePosition target = this.getTarget();

        // Liste avec les 4 directions dans l'ordre de préférence des fantômes
        Direction[] directions = {Direction.UP, Direction.LEFT,
                Direction.DOWN, Direction.RIGHT};
        //Liste vide des distances de cases par rapport à la cible
        double[] dist = new double[4];
        int i = 0;

        // On calcule la distance entre les differentes tuiles possibles et la tuile target

        TilePosition next_tuile = this.getBoard().getMaze().getNeighbourTilePosition(depart, this.direction);

        //On parcoure la liste des directions
        for (Direction dir : directions) {
            if ((dir.equals(this.direction.reverse())) // le fantôme essaye de faire demi-tour
                || (!this.getBoard().getMaze().getTile(next_tuile).ghostCanGoUp() && dir == Direction.UP) // il est sur une case où il ne peut pas aller vers le haut
                || (this.getBoard().getMaze().getNeighbourTile(next_tuile, dir).isWall()) ) { // la prochaine case est un mur
                dist[i] = Double.MAX_VALUE;
            } else {
                TilePosition next_next_tuile = this.getBoard().getMaze().getNeighbourTilePosition(next_tuile, dir);
                double dist_to_target = Math.sqrt(Math.pow(next_next_tuile.getCol() - target.getCol(), 2) + Math.pow(next_next_tuile.getLine() - target.getLine(), 2));
                dist[i] = dist_to_target;
            }
            i++;
        }

        // On cherche la plus petite distance pour choisir la prochaine intention
        int min = 0;
        for (i = 1; i < 4; i++) {
            if (dist[i] < dist[min]) {
                min = i;
            }
        }
            return directions[min];

    }

    @Override
    public void nextFrame() {

        this.nextMove();
        switch (this.getGhostState()){
            case FRIGHTENED:
            case FRIGHTENED_END:

                this.setFrightenedCounter(this.getFrightenedCounter() +1);

                if (this.getFrightenedCounter() == this.getBoard().getFrightenedtime() * 60 ) {
                        this.changeGhostState(GhostState.FRIGHTENED_END);
                } else if (this.getFrightenedCounter() >= this.getBoard().getFrightenedtime() * 60 + this.getBoard().getNbFlashes() * 30) {
                    this.changeGhostState(this.getPreviousGhostState());
                    this.setFrightenedCounter(0);
                    this.getBoard().setEatGhost(0);

                }
                break;

            case SCATTER:
            case CHASE:
                this.setStateCounter((int) (this.getStateCounter() +1));
                double time = this.getStateCounter()/60;

                int level = this.getBoard().getLevel();

                if (level == 1) {
                    if (time == 7) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 27) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time == 34) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 54) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time == 59) {
                        this.changeGhostState(GhostState.CHASE);

                    } else if (time == 79) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time == 84) {
                        this.changeGhostState(GhostState.CHASE);
                        this.setStateCounter(MAX_VALUE);
                    }

                } else if (level > 1 && level < 5) {
                    if (time == 7) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 27) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time == 34) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 54) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time == 59) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 1092) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time*60 == (1092*60 + 1)) {
                        this.changeGhostState(GhostState.CHASE);
                        this.setStateCounter(MAX_VALUE);
                    }

                } else if (level >= 5){
                    if (time == 5) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 25) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time == 30) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 50) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time == 55) {
                        this.changeGhostState(GhostState.CHASE);
                    } else if (time == 1092) {
                        this.changeGhostState(GhostState.SCATTER);
                    } else if (time*60 == (1092*60 + 1)) {
                        this.changeGhostState(GhostState.CHASE);
                        this.setStateCounter(MAX_VALUE);

                    }

                }
                break;
            default:
                break;
        }
    }


    /**
     * Return the type of ghost it is
     *
     * @return a ghost type
     */
    @Override
    public abstract GhostType getGhostType();

    /**
     * Return the target of the ghost at this current moment
     * Sometimes, it is a fix target or it can depend on some other actors
     * of the board.
     * The target is used by the ghost to decide its next move
     *
     * @return the current target as a tile position
     */
    @Override
    public abstract TilePosition getTarget();

    public double getDefaultSpeed () {
        switch (this.getBoard().getLevel()) {

            case 1 :
                return 0.94;
            case 2 :
            case 3 :
            case 4 :
                return 1.07;
            default:
                return 1.2;

        }

    }

    /**
     * Sets the Ghost state, which defines in particular its target and moves
     *
     * @param state
     */
    @Override
    public void setGhostState(GhostState state) {
        this.currentState = state;
    }

    /**
     * Perform all necessary actions for changing the ghost state from it current state to the new one
     *
     * @param state
     */
    @Override
    public abstract void changeGhostState(GhostState state);

    /**
     * Return the current ghost state
     *
     * @return a ghost state
     */
    @Override
    public GhostState getGhostState() {
        return this.currentState;
    }

    /**
     * Sets the ghost pen state,
     * i.e. whether the ghost is in the pen, out or getting in / getting out
     *
     * @param state
     */
    @Override
    public void setGhostPenState(GhostPenState state) {
        this.currentPenState = state;
    }

    /**
     * Return the current ghost pen state
     *
     * @return the ghost pe state
     */
    @Override
    public GhostPenState getGhostPenState() {
        return this.currentPenState;
    }

    /**
     * Sets the direction that the ghost should take when its gets out of the ghost pen
     *
     * @param dir a direction
     */
    @Override
    public void setOutOfPenDirection(Direction dir) {
        this.setDirection(getOutOfPenDirection());
    }

    /**
     * Return the direction taken by the ghost when getting out of the pen
     *
     * @return a direction
     */
    @Override
    public Direction getOutOfPenDirection() {
        // je fais simple pour l'instant, en vrai il est possible qu'il aille à droite
        return Direction.LEFT;
    }

    /**
     * If out of pen : sends the intention to reverse direction
     * If in pen or getting in or out of pen : set the out of pen direction to right
     */
    @Override
    public void reverseDirectionIntention() {
        if (this.getGhostPenState().equals(GhostPenState.OUT)){
            this.setIntention(this.direction.reverse());
        }else{
            this.setIntention(fr.upsaclay.bibs.pacman.model.Direction.RIGHT);
        }
    }

    /**
     * Return whether the ghost has a "dot counter" to decide when it gets out of the ghost pen
     *
     * @return true if the ghost uses a dot counter
     */
    @Override
    public abstract boolean hasDotCounter();

    /**
     * Return the dot counter of the ghost if it uses one
     *
     * @return a dot counter or null
     */
    @Override
    public abstract Counter getDotCounter();

    /**
     * Sets the "Elroy" value of the ghost
     * typically, 0 (not in elroy mode), 1, or 2
     *
     * @param elroy the elroy value
     */
    @Override
    public abstract void setElroy(int elroy);

    /**
     * Return the "Elroy" value of the ghost
     *
     * @return the Elroy value
     */
    @Override
    public int getElroy() {
        return this.elroy;
    }


    public void setFrightenedCounter(int nb){
        this.FrightenedCounter = nb;
    }
    public int getFrightenedCounter(){
        return this.FrightenedCounter;
    }
    public void setStateCounter(int nb){
        this.stateCounter = nb;
    }
    public double getStateCounter(){
        return this.stateCounter;
    }

    public double getSlowSpeed(){
        switch (this.getBoard().getLevel()) {

            case 1 :
                return 0.5;
            case 2 :
            case 3 :
            case 4 :
                return 0.57;
            default:
                return 0.63;

        }

    }

}
