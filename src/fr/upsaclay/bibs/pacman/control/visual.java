package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.view.BoardView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;
import fr.upsaclay.bibs.pacman.model.Direction;

public class visual extends simple {

    visual(GameType gameType) {
        super(gameType);
        this.view = new BoardView("Plateau de jeu ", this.board);
    }

    @Override
    public void initialize() throws PacManException {
        // TODO

        // view.setLoopDelay(INITIAL_DELAY);
        initializeNewGame();

        view.setBoard(board);
        view.setController(this);
        view.initialize();
        view.setLayout(PacManLayout.INIT);
        view.update();
    }

    @Override
    public void initializeNewGame() throws PacManException {
        view.update();

        board = Board.createBoard(this.getGameType());

    }

    @Override
    public void receiveAction(GameAction action) {
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
                // TODO
                //view.pause();
                break;
            case RESUME:
                // TODO
                view.setLayout(PacManLayout.GAME_ON);
                // view.resume();
                break;
            case START:
                // TODO
                // view.start();
                break;
            case NEXT_FRAME:
                // TODO
                // view.nextFrame();
                break;

            default:
                break;
        }

    }

}
