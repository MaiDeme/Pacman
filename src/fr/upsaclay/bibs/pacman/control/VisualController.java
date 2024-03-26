package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.view.BoardView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.view.PacManView;
import fr.upsaclay.bibs.pacman.model.board.BoardState;




public class VisualController extends SimpleController {

    public static final int WIDTH = 160;
    public static final int HEIGHT = 140;
    private BoardView view;

    VisualController() {
        super();
    }



    @Override
    public void initialize() throws PacManException {
        view = new BoardView("Plateau de jeu ", WIDTH, HEIGHT);
        initializeNewGame();
    }

    @Override
    public void initializeNewGame() throws PacManException {
        board = Board.createBoard(this.getGameType());
        receiveAction(GameAction.START);

        view.setController(this);
        view.initialize();
        view.setLoopDelay(INITIAL_DELAY);
        view.setBoard(board);
        view.setLayout(PacManLayout.INIT);
        view.update();


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
                case RESUME:
                    view.setLayout(PacManLayout.GAME_ON);
                    break;
                default:
                    break;
            }
        }
        view.update();

    }
}
