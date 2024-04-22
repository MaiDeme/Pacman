package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.board.Board;

public class BonusImpl extends AbstractActor implements Bonus {
    private BonusType bonusType;
    private boolean active;
    private int timer; 

    public BonusImpl(Board board, BonusType bonusType) {
        super(board, ActorType.BONUS);
        this.bonusType = bonusType;
        this.x = 105;
        this.y = 153;
        setPosition(105, 153);
        this.active = false;  
        this.timer = 9;  
    }

    @Override
    public void start() {
        
        this.active = true;
    }

    @Override
    public BonusType getBonusType() {
        return bonusType;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void nextMove() {
        
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                deactivate();
            }
        }
    }

    public void deactivate() {
        this.active = false;
    }


}
