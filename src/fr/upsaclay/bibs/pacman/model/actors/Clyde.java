package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.model.Direction;

public class Clyde extends AbstractGhost {
    final TilePosition scattertarget = new TilePosition(this.getBoard().getMaze().getHeight()-2, 0);
    private Counter dotCounter = new Counter(60);

    public Clyde(Board board, ActorType type) {
        super(board, type);
        setGhostPenState(GhostPenState.IN);
        setGhostState(GhostState.SCATTER);

    }

    @Override
    public void start() {
        this.elroy = 0;
        this.currentPenState = GhostPenState.IN;
        this.x = 128;
        this.y = 139;
        this.setGhostState(GhostState.SCATTER);
        this.setGhostPenState(GhostPenState.IN);
        this.direction = Direction.LEFT;
        this.speed=getDefaultSpeed();
        TilePosition depart = this.getCurrentTile();
        this.intention = getNextIntention(depart);
        this.stateCounter = 0;
        
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

        switch (this.getGhostState()) {
            case CHASE:
                TilePosition pacman_tile = this.getBoard().getPacMan().getCurrentTile();
                TilePosition current_tile = this.getCurrentTile();

                double dist = Math.sqrt(Math.pow(pacman_tile.getCol() - current_tile.getCol(), 2) + Math.pow(pacman_tile.getLine() - current_tile.getLine(), 2));
                TilePosition target;
                if (dist < 8) {
                    target = new TilePosition(0, this.board.getMaze().getHeight() - 1);
                } else {
                    target = pacman_tile;
                }

                return target;
            case SCATTER:
                return this.scattertarget;
            case FRIGHTENED:
            case FRIGHTENED_END:
                return null;
            default:
                return this.getBoard().penEntry();
        }
    }

    @Override
    public double getDefaultSpeed() {
        return board.getLevelGhostSpeed();
    }

    /**
     * Perform all necessary actions for changing the ghost state from it current state to the new one
     *
     * @param state
     */
    @Override
    public void changeGhostState(GhostState state) {
        GhostState actualState = this.getGhostState();

        //On s'occupe de changer leur intention, la target étant changée automatiquement en fonction de leur état dans la fonction get target
        if ((actualState == GhostState.CHASE || actualState == GhostState.SCATTER) && !actualState.equals(state)) {
            this.setIntention(this.direction.reverse());
        }

        //Ensuite on change leur état
        this.setGhostState(state);
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
        this.elroy = 0;
    }
}
