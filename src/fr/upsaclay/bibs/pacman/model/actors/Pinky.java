package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.Direction;


public class Pinky extends AbstractGhost{

    TilePosition target;
    final TilePosition scattertarget = new TilePosition(0, 3);
    public Counter dotCounter=new Counter(0);


    public Pinky(Board board, ActorType type) {
        super(board, type);
        setGhostPenState(GhostPenState.IN);

    }

    @Override
    public void start() {
        this.elroy = 0;
        this.currentPenState = GhostPenState.IN;
        this.x = 112;
        this.y = 139;
        this.setGhostState(GhostState.SCATTER);
        this.setGhostPenState(GhostPenState.IN);
        this.direction = Direction.LEFT;
        this.speed = getDefaultSpeed();
        TilePosition depart = this.getCurrentTile();
        this.intention = getNextIntention(depart);
        this.stateCounter = 0;
    }


    @Override
    public double getDefaultSpeed() {
        return board.getLevelGhostSpeed();
    }


    @Override
    public GhostType getGhostType() {
        return GhostType.PINKY;
    }

    
    @Override
    public TilePosition getTarget() {

        Actor pacman = this.getBoard().getPacMan();
        switch (this.getGhostState()) {
            case CHASE :
                if (pacman.getDirection() == Direction.UP) {
                    return new TilePosition(pacman.getX() + 4 * Direction.LEFT.getDx(), pacman.getY() + 4 * Direction.UP.getDy());
                }
                return new TilePosition(pacman.getX() + 4 * pacman.getDirection().getDx(), pacman.getY() + 4 * pacman.getDirection().getDy());
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
   public void changeGhostState(GhostState state) {
       GhostState actualState = this.getGhostState();

       //On s'occupe de changer leur intention, la target étant changée automatiquement en fonction de leur état dans la fonction get target
       if ((actualState == GhostState.CHASE || actualState == GhostState.SCATTER) && !actualState.equals(state)) {
           this.setIntention(this.direction.reverse());
       }

       //Ensuite on change leur état
       this.setGhostState(state);

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
        this.elroy = 0;
    }


}
