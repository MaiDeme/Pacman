package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Blinky extends AbstractGhost {
    TilePosition target;
    final double DEFAULT_SPEED = 0.94;
    final TilePosition scattertarget = new TilePosition(0, this.getBoard().getMaze().getWidth()-3);

    public Blinky(Board board, ActorType type) {
        super(board, type);
        setGhostPenState(GhostPenState.OUT);
        setGhostState(GhostState.SCATTER);
    }

    @Override
    public void start() {
        this.elroy = 0;
        this.currentPenState = GhostPenState.OUT;
        this.x = 112;
        this.y = 115;
        this.setGhostState(GhostState.SCATTER);
        this.Direction = fr.upsaclay.bibs.pacman.model.Direction.LEFT;
        this.speed = this.DEFAULT_SPEED;
        TilePosition depart = this.getCurrentTile();
        this.intention = getNextIntention(depart);
        this.stateCounter = 0;
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
        switch (this.getGhostState()) {
            default:
                return board.getMaze().getTilePosition(this.getBoard().getPacMan().getX(), this.getBoard().getPacMan().getY());
            case SCATTER:
                if (this.elroy == 0) {
                    return this.scattertarget;
                }else{
                    return board.getMaze().getTilePosition(this.getBoard().getPacMan().getX(), this.getBoard().getPacMan().getY());
                }
            case FRIGHTENED:
            case FRIGHTENED_END:
                return null;
            case DEAD:
                return this.getBoard().penEntry();
        }

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
            this.setIntention(this.Direction.reverse());
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
        this.elroy = elroy;

        if (this.elroy == 1){
            switch (this.getBoard().getLevel()){
                case 1 :
                    this.speed = 1;
                    break;
                case 2 :
                case 3 :
                case 4 :
                    this.speed = 1.14;
                    break;
                default:
                    this.speed = 1.26;
                    break;
            }


        } else if (this.elroy == 2) {
            switch (this.getBoard().getLevel()){
                case 1 :
                    this.speed = 1.14;
                    break;
                case 2 :
                case 3 :
                case 4 :
                    this.speed = 1.2;
                    break;
                default:
                    this.speed = 1.33;
                    break;
            }

        }
    }

    @Override
    public void nextMove() {
        super.nextMove();

        int dots = this.getBoard().getMaze().getNumberOfDots();
        switch (elroy){
            case 0 :
                switch (this.getBoard().getLevel()){
                    case 1 :
                        if (dots == 20){
                            this.setElroy(1);
                        }
                        break;
                    case 2 :
                        if (dots == 30){
                            this.setElroy(1);
                        }
                        break;
                    case 3 :
                    case 4 :
                    case 5 :
                        if (dots == 40){
                            this.setElroy(1);
                        }
                        break;
                    case 6 :
                    case 7 :;
                    case 8 :
                        if (dots == 50){
                            this.setElroy(1);
                        }
                        break;
                    case 9 :
                    case 10:
                    case 11 :
                        if (dots == 60){
                            this.setElroy(1);
                        }
                        break;
                    case 12 :
                    case 13:
                    case 14:
                        if (dots == 80){
                            this.setElroy(1);
                        }
                        break;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        if (dots == 100){
                            this.setElroy(1);
                        }
                        break;
                    default:
                        if (dots == 120){
                            this.setElroy(1);
                        }
                        break;



                }

                break;
            case 1 :
                switch (this.getBoard().getLevel()) {
                    case 1:
                        if (dots == 10) {
                            this.setElroy(2);
                        }
                        break;
                    case 2:
                        if (dots == 15) {
                            this.setElroy(2);
                        }
                        break;
                    case 3:
                    case 4:
                    case 5:
                        if (dots == 20) {
                            this.setElroy(2);
                        }
                        break;
                    case 6:
                    case 7:
                    case 8:
                        if (dots == 25) {
                            this.setElroy(2);
                        }
                        break;
                    case 9:
                    case 10:
                    case 11:
                        if (dots == 30) {
                            this.setElroy(2);
                        }
                        break;
                    case 12:
                    case 13:
                    case 14:
                        if (dots == 40) {
                            this.setElroy(2);
                        }
                        break;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        if (dots == 50) {
                            this.setElroy(2);
                        }
                        break;
                    default:
                        if (dots == 60) {
                            this.setElroy(2);
                        }
                        break;
                }
            default:
                break;
        }

    }

}
