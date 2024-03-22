package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;

public class TestBoard implements Board {



    @Override
    public GameType getGameType() {
        return GameType.TEST;
    }

    @Override
    public void initialize() throws PacManException {


    }

}
