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

    public SimpleController(GameType gameType) {
        this.gameType = gameType;
        this.board = Board.createBoard(gameType);

    }

    @Override
    public void initialize() throws PacManException {

        // BoardView view = new BoardView("Plateau de jeu ", this.board);
        // view.setLoopDelay(INITIAL_DELAY);
        initializeNewGame();

    }

    @Override
    public void initializeNewGame() throws PacManException {

        // view.update();

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
    public void receiveAction(GameAction action) throws PacManException {
        if (board.getBoardState()!=BoardState.STARTED){
            if (action==GameAction.START){
                board.initialize();
            }else{
                throw new ForbiddenActionException(action);
            }
        }else if (action==GameAction.START){
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
                break;
            case RESUME:
                break;
            case NEXT_FRAME:
                break;
        }
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
