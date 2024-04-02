package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.audio.SoundManager;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import fr.upsaclay.bibs.pacman.audio.SoundManager;

public abstract class AbstractActor implements Actor{

    protected double x;
    protected double y;
    protected Board board;
    protected Direction Direction;
    protected Direction intention;
    protected boolean blocked;
    protected double speed;
    protected int stopTime;
    String test;
    private SoundManager soundManager;


    public ActorType type;

    public AbstractActor(Board board,  ActorType type){
        this.type = type;
        this.board = board;
        this.blocked = false;
        this.speed = 1;
        this.stopTime = 0;
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

        return (int) Math.floor(this.x);

    }

    @Override
    public int getY() {
        return (int) Math.floor(this.y);
    }

    @Override
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public TilePosition getCurrentTile() {
        return board.getMaze().getTilePosition(this.getX(), this.getY());
    }

    @Override
    public abstract void start();

    @Override
    public Direction getDirection() {
        return this.Direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.Direction = direction;

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
        return this.blocked;
    }

    @Override
    public abstract void nextMove() ;

    public String getTest(){
        return this.test;
    }
    @Override
    public void nextFrame() {
        this.nextMove();

    }

//----------------------------------------------------------------------------------------------------------------------
    // Step 2
    @Override
    public void setSpeed(double speed) {
        this.speed = speed;

    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public double getRealX() {
        return this.x;
    }

    @Override
    public double getRealY() {
        return this.y;
    }

    @Override
    public void setStopTime(int nbFrames) {
        this.stopTime = nbFrames;
    }
}
