package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.view.BoardView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;
import fr.upsaclay.bibs.pacman.view.PacManView;


public class simple implements Controller{


    public GameType gameType;
    public Board board;
    public PacManView view;
    public static final int INITIAL_DELAY = 17;

    public simple(GameType gameType) {
        this.gameType = gameType;
        this.board = Board.createBoard(gameType);
        ;

    }

    @Override
    public void initialize() throws PacManException {

        BoardView view = new BoardView("Plateau de jeu ", this.board);
        view.setLoopDelay(INITIAL_DELAY);

    }

    @Override
    public void initializeNewGame() throws PacManException {

        view.update();

        board = Board.createBoard(this.getGameType());

        view.setBoard(board);


        view.setController(this);
        view.initialize();
        view.setLayout(PacManLayout.INIT);
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
        switch (action) {
            case PAUSE: pause(); break;
        }
        switch (action) {
            case START:
                view.setLayout(PacManLayout.GAME_ON); break;
        }

        view.update();

    }


    private void pause(){
        view.setLayout(PacManLayout.PAUSE);
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}





