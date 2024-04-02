package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public abstract class AbstractGhost extends AbstractActor implements Ghost {

    protected GhostState currentState;
    protected  GhostPenState currentPenState;

    public AbstractGhost(Board board, ActorType type) {
        super(board, type);
    }

    @Override
    public abstract void start();

    @Override
    public void nextMove() {


        //TilePosition depart = this.getCurrentTile();
        this.setPosition(this.x + this.getDirection().getDx() * this.getSpeed(), this.y + this.getDirection().getDy() * this.getSpeed());

        // Quand il rejoint le centre d'une tuile :
        if (this.getX() % Maze.TILE_WIDTH == Maze.TITLE_CENTER_X
                && this.getY() % Maze.TILE_HEIGHT == Maze.TITLE_CENTER_Y) {
            //il applique son intention et met donc à jour sa direction
            this.Direction = this.intention;
            //il calcule sa nouvelle intention.
            this.intention = getNextIntention(this.getCurrentTile());//

        }


    }

    public fr.upsaclay.bibs.pacman.model.Direction getNextIntention(TilePosition depart) {
        // Pour Blinky la target est la position de PacMan

        //Il choisit la tuile possible qui le rapproche le plus de sa tuile cible (selon la distance euclidienne)
        //Pour cela, il regarde où il peut aller à partir de la prochaine tuile sachant qu'il n'a pas le droit de revenir en arrière ni de traverser les murs

        TilePosition target = this.getTarget();

        // Liste avec les 4 directions dans l'ordre de préférence des fantômes
        fr.upsaclay.bibs.pacman.model.Direction[] directions = {fr.upsaclay.bibs.pacman.model.Direction.UP, fr.upsaclay.bibs.pacman.model.Direction.LEFT,
                fr.upsaclay.bibs.pacman.model.Direction.DOWN, fr.upsaclay.bibs.pacman.model.Direction.RIGHT};
        //Liste vide des distances de cases par rapport à la cible
        double[] dist = new double[4];
        int i = 0;

        // On calcule la distance entre les differentes tuiles possibles et la tuile target

        TilePosition next_tuile = this.getBoard().getMaze().getNeighbourTilePosition(depart, this.Direction);

        //On parcoure la liste des directions
        for (fr.upsaclay.bibs.pacman.model.Direction dir : directions) {
            if ((dir == this.Direction.reverse()) // le fantôme essaye de faire demi-tour
                || (this.getBoard().getMaze().getTile(next_tuile) == Tile.NT && dir == Direction.UP) // il est sur une case où il ne peut pas aller vers le haut
                || (this.getBoard().getMaze().getNeighbourTile(next_tuile, dir).isWall()) ) { // la prochaine case est un mur
                dist[i] = Double.MAX_VALUE;
            } else {
                TilePosition next_next_tuile = this.getBoard().getMaze().getNeighbourTilePosition(next_tuile, dir);
                double dist_to_target = Math.sqrt((next_next_tuile.getCol() - target.getCol()) ^ 2 + (next_next_tuile.getLine() - target.getLine()) ^ 2);
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
    public void setOutOfPenDirection(fr.upsaclay.bibs.pacman.model.Direction dir) {

    }

    /**
     * Return the direction taken by the ghost when getting out of the pen
     *
     * @return a direction
     */
    @Override
    public fr.upsaclay.bibs.pacman.model.Direction getOutOfPenDirection() {
        return null;
    }

    /**
     * If out of pen : sends the intention to reverse direction
     * If in pen or getting in or out of pen : set the out of pen direction to right
     */
    @Override
    public void reverseDirectionIntention() {

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
        return 0;
    }
}
