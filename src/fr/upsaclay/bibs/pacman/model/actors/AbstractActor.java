package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public abstract class AbstractActor implements Actor{

    protected int x;
    protected int y;
    protected Board board;
    protected Direction currentDirection;
    protected Direction intention;

    public ActorType type;

    public AbstractActor(Board board,  ActorType type){
        this.type = type;
        this.board = board;
    }


    @Override
    public ActorType getType() {
        return this.type;
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public TilePosition getCurrentTile() {
        return board.getMaze().getTilePosition(this.x, this.y);
    }

    @Override
    public abstract void start();

    @Override
    public Direction getDirection() {
        return this.currentDirection;
    }

    @Override
    public void setDirection(Direction direction) {

        this.currentDirection = direction;
    }

    @Override
    public void setIntention(Direction direction) {
        this.intention = direction;
    }

    @Override
    public Direction getIntention() {
        return this.intention;
    }

    @Override
    public boolean isBlocked() {
        Tile nextTile = this.board.getMaze().getNeighbourTile(this.getCurrentTile(), this.currentDirection);
        return nextTile.isWall();
    }

    @Override
    public abstract void nextMove() ;

    @Override
    public void nextFrame() {
        this.nextMove();
    }

//----------------------------------------------------------------------------------------------------------------------
    // Step 2
    @Override
    public void setSpeed(double speed) {

    }

    @Override
    public double getSpeed() {
        return 0;
    }

    @Override
    public double getRealX() {
        return 0;
    }

    @Override
    public double getRealY() {
        return 0;
    }

    @Override
    public void setStopTime(int nbFrames) {
    }
}
