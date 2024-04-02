package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Inky extends AbstractGhost {
    final double DEFAULT_SPEED = 0.94;
    final TilePosition scattertarget = new TilePosition(this.getBoard().getMaze().getHeight()-2, this.getBoard().getMaze().getWidth()-1);

    public Inky(Board board, ActorType type) {
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
        return GhostType.INKY;
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
        Actor pacman = this.getBoard().getPacMan();
        Actor blinky = this.getBoard().getGhost(GhostType.BLINKY);

        int x_offset = 0;
        int y_offset = 0;
        switch (pacman.getDirection()) {
            case DOWN -> {y_offset += 2;}
            case RIGHT -> {x_offset += 2;}
            case UP -> {y_offset -= 2;x_offset -= 2;}
            case LEFT -> {x_offset -= 2;}
        }

        TilePosition pacman_tile = pacman.getCurrentTile();

        TilePosition mid_tile = this.getBoard().getMaze().getTilePosition((pacman_tile.getCol() + x_offset) * Maze.TILE_WIDTH,
                (pacman_tile.getLine() + y_offset) * Maze.TILE_HEIGHT);

        TilePosition blinky_tile = blinky.getCurrentTile();

       TilePosition target = new TilePosition(mid_tile.getLine() + (mid_tile.getLine() - blinky_tile.getLine()),
               mid_tile.getCol() + (mid_tile.getCol() - blinky_tile.getCol()));

        return target;
    }

    @Override
    public double getDefaultSpeed() {
        return 0;
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
