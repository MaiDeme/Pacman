package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public abstract class AbstractActor implements Actor{
    @Override
    public ActorType getType() {
        return null;
    }

    @Override
    public Board getBoard() {
        return null;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setPosition(int x, int y) {

    }

    @Override
    public TilePosition getCurrentTile() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setDirection(Direction direction) {

    }

    @Override
    public void setIntention(Direction direction) {

    }

    @Override
    public Direction getIntention() {
        return null;
    }

    @Override
    public boolean isBlocked() {
        return false;
    }

    @Override
    public void nextMove() {

    }

    @Override
    public void nextFrame() {

    }

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
