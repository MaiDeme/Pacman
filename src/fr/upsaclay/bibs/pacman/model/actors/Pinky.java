package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;


public class Pinky extends AbstractGhost{

    TilePosition target;
    final double DEFAULT_SPEED = 0.94;
    public Counter dotCounter=new Counter(0);


    public Pinky(Board board, ActorType type) {
        super(board, type);
        setGhostPenState(GhostPenState.IN);

    }

    @Override
    public void start() {
        this.x = 112;
        this.y = 139;
        this.Direction = fr.upsaclay.bibs.pacman.model.Direction.LEFT;
        this.speed = this.DEFAULT_SPEED;
        TilePosition depart = this.getCurrentTile();
        this.intention = getNextIntention(depart);
    }


    @Override
    public double getDefaultSpeed() {
        return this.DEFAULT_SPEED;
    }


    @Override
    public GhostType getGhostType() {
        return GhostType.PINKY;
    }

    
    @Override
    public TilePosition getTarget() {

        Actor pacman = this.getBoard().getPacMan();
        
        if (pacman.getDirection() == Direction.UP) {
            return new TilePosition(pacman.getX()+ 4*Direction.LEFT.getDx(), pacman.getY() + 4*Direction.UP.getDy());
        }
        return new TilePosition(pacman.getX() + 4*pacman.getDirection().getDx(), pacman.getY() + 4*pacman.getDirection().getDy());




    }

    
   @Override
   public void changeGhostState(GhostState state) {

   }


   @Override
   public boolean hasDotCounter() {
        return true;
   }

    @Override
    public Counter getDotCounter() {
        return dotCounter;
    }

    @Override
    public void setElroy(int elroy) {

    }










}
