package fr.upsaclay.bibs.pacman.control;

import java.security.cert.CertPathValidatorException.BasicReason;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.view.PacManLayout;

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

        board.StartNewBoard();
        board.setNumberOfLives(3);
        board.startActors();

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


        switch (action) {
            case UP:
                if (!board.getBoardState().equals(BoardState.STARTED)){
                    throw new ForbiddenActionException(action);
                }
                board.getPacMan().setIntention(Direction.UP);
                break;
            case DOWN:
                if (!board.getBoardState().equals(BoardState.STARTED)){
                    throw new ForbiddenActionException(action);
                }
                board.getPacMan().setIntention(Direction.DOWN);
                break;
            case LEFT:
                if (!board.getBoardState().equals(BoardState.STARTED)){
                    throw new ForbiddenActionException(action);
                }
                board.getPacMan().setIntention(Direction.LEFT);
                break;
            case RIGHT:
                if (!board.getBoardState().equals(BoardState.STARTED)){
                    throw new ForbiddenActionException(action);
                }
                board.getPacMan().setIntention(Direction.RIGHT);
                break;
            case PAUSE:
                if (board.getBoardState() != BoardState.STARTED){
                    throw new ForbiddenActionException(action);
                }

                board.pause();
                break;
            case NEXT_FRAME:
                if (!board.getBoardState().equals(BoardState.STARTED)){
                    throw new ForbiddenActionException(action);
                }
                board.nextFrame();
                switch (board.getBoardState()) {
                    case GAME_OVER:
                        break;
                    case LEVEL_OVER:
                        break;
                    case LIFE_OVER:
                        board.initializeNewLife();
                        break;
                    default:
                        break;
                }

                break;
            case NEXT_LEVEL:
                if (!board.getBoardState().equals(BoardState.LEVEL_OVER)){
                    throw new ForbiddenActionException(action);
                }
                board.initializeNewLevel(this.board.getLevel()+1);
                break;
            case NEW_GAME:
                if (!board.getBoardState().equals(BoardState.INITIAL) && !board.getBoardState().equals(BoardState.PAUSED) && !board.getBoardState().equals(BoardState.GAME_OVER)){
                    throw new ForbiddenActionException(action);
                }
                initializeNewGame();
            case NEW_LIFE:
                board.startActors();
                board.setBoardState(BoardState.INITIAL);
                break;
            case START:
                if (board.getBoardState() != BoardState.INITIAL) {
                    throw new ForbiddenActionException(action);
                }
                board.startActors();
                board.start();
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
