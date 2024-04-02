package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Blinky extends AbstractGhost {
    TilePosition target;
    final double DEFAULT_SPEED = 0.3;

    public Blinky(Board board, ActorType type) {
        super(board, type);
    }

    @Override
    public void start() {
        this.x = 112;
        this.y = 115;
        this.Direction = fr.upsaclay.bibs.pacman.model.Direction.LEFT;
        this.speed = this.DEFAULT_SPEED;
        TilePosition depart = this.getCurrentTile();
        this.intention = getNextIntention(depart);
    }

    /**
     * Return the default speed of this type of ghost
     *
     * @return a double
     */

    public double getDefaultSpeed() {
        return this.DEFAULT_SPEED;
    }

    /**
     * Return the type of ghost it is
     *
     * @return a ghost type
     */
    @Override
    public GhostType getGhostType() {
        return GhostType.BLINKY;
    }

    /**
     * Return the target of the ghost at this current moment
     * For Blinky, it is the position of PacMan
     * The target is used by the ghost to decide its next move
     *
     * @return the current target as a tile position
     */
    @Override
    public TilePosition getTarget() {
        return board.getMaze().getTilePosition(this.getBoard().getPacMan().getX(), this.getBoard().getPacMan().getY());
    }

    /**
     * Perform all necessary actions for changing the ghost state from it current state to the new one
     *
     * @param state
     */
    @Override
    public void changeGhostState(GhostState state) {

    }

    /**
     * Return whether the ghost has a "dot counter" to decide when it gets out of the ghost pen
     *
     * @return true if the ghost uses a dot counter
     */
    @Override
    public boolean hasDotCounter() {
        return false;
    }

    /**
     * Return the dot counter of the ghost if it uses one
     *
     * @return a dot counter or null
     */
    @Override
    public Counter getDotCounter() {
        return null;
    }

    /**
     * Sets the "Elroy" value of the ghost
     * typically, 0 (not in elroy mode), 1, or 2
     *
     * @param elroy the elroy value
     */
    @Override
    public void setElroy(int elroy) {

    }
}
