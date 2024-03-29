package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.view.BoardView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;
import fr.upsaclay.bibs.pacman.view.PacManView;
import fr.upsaclay.bibs.pacman.model.Direction;

public class SimpleController implements Controller {

    public GameType gameType;
    public Board board;
    public PacManView view;
    public static final int INITIAL_DELAY = 17;

    public SimpleController() {
        super();
    }

    @Override
    public void initialize() throws PacManException {
        initializeNewGame();

    }

    @Override
    public void initializeNewGame() throws PacManException {

        board = Board.createBoard(this.getGameType());

    }

    @Override
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    @Override
    public GameType getGameType() {
        return this.gameType;
    }
    @Override
    public void receiveAction(GameAction action) throws PacManException{

        //si le jeu n'est pas started
        if (board.getBoardState()== BoardState.INITIAL){
            //si on appuie sur start
            if (action==GameAction.START){
                board.initialize();
            }else{
                //sinon impossible de faire autre chose
                throw new ForbiddenActionException(action);
            }
        }else {
            switch (action) {
                case START:
                    throw new ForbiddenActionException(action);
                case UP:
                    board.getPacMan().setDirection(Direction.UP);
                    break;
                case DOWN:
                    board.getPacMan().setDirection(Direction.DOWN);
                    break;
                case LEFT:
                    board.getPacMan().setDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    board.getPacMan().setDirection(Direction.RIGHT);
                    break;
                case PAUSE:
                    break;
                case NEXT_FRAME:
                    board.nextFrame();
                    break;
                case NEXT_LEVEL:
                    break;
                case NEW_GAME:
                    break;
                case NEW_LIFE:
                    break;
                case RESUME:
                default:
                    break;
            }
        }

    }


    @Override
    public Board getBoard() {
        return this.board;
    }
}
