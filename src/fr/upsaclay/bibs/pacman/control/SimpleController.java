package fr.upsaclay.bibs.pacman.control;

import java.security.cert.CertPathValidatorException.BasicReason;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.Direction;

public class SimpleController implements Controller {

    public GameType gameType;
    public Board board;
    public static final int INITIAL_DELAY = 17;

    public SimpleController() {
        super();
    }

    @Override
    public void initialize() throws PacManException {
        board = Board.createBoard(this.getGameType());

    }

    @Override
    public void initializeNewGame() throws PacManException {
        if (board == null) {
            initialize();
        }
        int HS = board.getMaze().getHigh_score();
        board = Board.createBoard(this.getGameType());
        board.startActors();
        board.getMaze().setHigh_score(HS);
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
        if (board.getBoardState()==BoardState.INITIAL && action!=GameAction.START ){
            throw new ForbiddenActionException(action);
        }
        if (board.getBoardState() == BoardState.PAUSED && action != GameAction.RESUME && action != GameAction.TITLE_SCREEN && action != GameAction.QUIT && action != GameAction.NEW_GAME) {
            throw new ForbiddenActionException(action);
        }

        switch (action) {
            case UP:
                board.getPacMan().setIntention(Direction.UP);
                break;
            case DOWN:
                board.getPacMan().setIntention(Direction.DOWN);
                break;
            case LEFT:
                board.getPacMan().setIntention(Direction.LEFT);
                break;
            case RIGHT:
                board.getPacMan().setIntention(Direction.RIGHT);
                break;
            case PAUSE:
                if (board.getBoardState() != BoardState.STARTED) {
                    throw new ForbiddenActionException(action);
                }
                board.pause();
                break;
            case NEXT_FRAME:
                board.nextFrame();
                break;
            case NEXT_LEVEL:
                break;
            case NEW_GAME:
                initializeNewGame();
            case NEW_LIFE:
                break;
            case START:
                if (board.getBoardState() == BoardState.STARTED) {
                    throw new ForbiddenActionException(action);
                }
                initializeNewGame();
                break;
            case RESUME:
                if (board.getBoardState() != BoardState.PAUSED) {
                    throw new ForbiddenActionException(action);
                }
                board.resume();
                break;
            case QUIT:
                System.exit(0);
                break;
            case TITLE_SCREEN:
                int HS = board.getMaze().getHigh_score();
                board = Board.createBoard(this.getGameType());
                board.getMaze().setHigh_score(HS);
                
            default:
                break;
        }
    }


    @Override
    public Board getBoard() {
        return this.board;
    }
}
