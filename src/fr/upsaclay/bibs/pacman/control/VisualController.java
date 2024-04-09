package fr.upsaclay.bibs.pacman.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import fr.upsaclay.bibs.pacman.audio.SoundManager;
import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.view.BoardView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.view.PacManView;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;




public class VisualController extends SimpleController {

    private BoardView view;

    VisualController() {
        super();      
    }

    @Override
    public void initialize() throws PacManException {

        board = Board.createBoard(this.getGameType());

        view = new BoardView("PACMAN", board.getMaze().getPixelWidth()*BoardView.PIXELS_PER_CELLS, board.getMaze().getPixelHeight()*BoardView.PIXELS_PER_CELLS+this.board.getMaze().TILE_HEIGHT);
        view.setController(this);
        view.setBoard(board);

        view.initialize();
        view.setLoopDelay(INITIAL_DELAY);

        view.setLayout(PacManLayout.INIT);
        view.update();
    }

    @Override
    public void initializeNewGame() throws PacManException {
        if (board == null) {
            initialize();
        }
        int HS = board.getMaze().getHigh_score();
        board = Board.createBoard(this.getGameType());
        board.startActors();
        view.setBoard(this.board);
        board.getMaze().setHigh_score(HS);
        view.setLayout(PacManLayout.GAME_ON);
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
                if (board.getBoardState() == BoardState.PAUSED) {
                    throw new ForbiddenActionException(action);
                }
                view.setLayout(PacManLayout.PAUSE);
                board.pause();
                break;
            case NEXT_FRAME:
                board.nextFrame();
                switch (board.getBoardState()) {
                    case GAME_OVER:
                        view.setLayout(PacManLayout.GAME_OVER);
                        
                        break;
                    case LEVEL_OVER:
                        view.setLayout(PacManLayout.LEVEL_OVER);
                        board.initializeNewLevel(board.getLevel()+1);
                        break;
                    case LIFE_OVER:
                        view.setLayout(PacManLayout.LIFE_OVER);
                        board.initializeNewLife();
                        break;                        
                
                    default:
                        break;
                }
                break;
            case NEXT_LEVEL:
                board.setBoardState(BoardState.LEVEL_OVER);
                break;
            case NEW_GAME:
                view.setLayout(PacManLayout.GAME_ON);
                initializeNewGame();
            case NEW_LIFE:
                view.setLayout(PacManLayout.GAME_ON);
                board.startActors();
                break;
            case START:
                initializeNewGame();
                board.start();
                break;
            case RESUME:
                if (board.getBoardState() != BoardState.PAUSED) {
                    throw new ForbiddenActionException(action);
                }
                board.resume();
                view.setLayout(PacManLayout.GAME_ON);
                break;
            case QUIT:
                System.exit(0);
                break;
            case TITLE_SCREEN:
                int HS = board.getMaze().getHigh_score();
                board = Board.createBoard(this.getGameType());
                board.getMaze().setHigh_score(HS);
                board.initBoardState();
                view.setBoard(this.board);
                view.setLayout(PacManLayout.INIT);
            default:
                break;
        }
        view.update();
    }



}
