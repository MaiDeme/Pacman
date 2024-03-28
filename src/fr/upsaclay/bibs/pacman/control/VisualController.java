package fr.upsaclay.bibs.pacman.control;

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
        view.setLayout(PacManLayout.GAME_ON);
        //board.initialize();
        }

    @Override
    public void receiveAction(GameAction action) throws PacManException{
        switch (action) {
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
                view.setLayout(PacManLayout.PAUSE);
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
            case START:
                initializeNewGame();
                break;

            case RESUME:
                view.setLayout(PacManLayout.GAME_ON);
                break;
            case QUIT:
                System.exit(0);
                break;
            default:
                break;
        }
        view.update();
    }

}
