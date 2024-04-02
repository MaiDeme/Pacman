package fr.upsaclay.bibs.pacman.test.step3;

import fr.upsaclay.bibs.pacman.model.actors.AbstractGhost;
import fr.upsaclay.bibs.pacman.model.actors.ActorType;
import fr.upsaclay.bibs.pacman.model.actors.GhostState;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Clyde extends AbstractGhost {
    final double DEFAULT_SPEED = 0.94;

    public Clyde(Board board, ActorType type) {
        super(board, type);
    }

    @Override
    public void start() {
        this.x = 96;
        this.y = 139;
        this.Direction = fr.upsaclay.bibs.pacman.model.Direction.LEFT;
        this.speed = this.DEFAULT_SPEED;
        TilePosition depart = this.getCurrentTile();
        this.intention = getNextIntention(depart);
    }

    /**
     * Return the type of ghost it is
     *
     * @return a ghost type
     */
    @Override
    public GhostType getGhostType() {
        return GhostType.CLYDE;
    }

    /**
     * Return the target of the ghost at this current moment
     * Sometimes, it is a fix target or it can depend on some other actors
     * of the board.
     * The target is used by the ghost to decide its next move
     *
     * @return the current target as a tile position
     */
    @Override
    public TilePosition getTarget() {
        TilePosition pacman_tile = this.getBoard().getPacMan().getCurrentTile();
        TilePosition current_tile = this.getCurrentTile();

        double dist = Math.sqrt(Math.pow(pacman_tile.getCol() - current_tile.getCol(), 2) + Math.pow(pacman_tile.getLine() - current_tile.getLine(), 2));
        TilePosition target;
        if (dist < 8) {
            target = new TilePosition(0, this.board.getMaze().getHeight()-1);
        } else {
            target = pacman_tile;
        }

        return target;
    }

    @Override
    public double getDefaultSpeed() {
        return this.DEFAULT_SPEED;
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
