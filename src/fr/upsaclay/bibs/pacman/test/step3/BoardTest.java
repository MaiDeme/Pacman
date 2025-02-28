package fr.upsaclay.bibs.pacman.test.step3;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.*;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.test.Configurations;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Long.MAX_VALUE;
import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    /*********************************************************************/
    /**                    Step 1 Tests                                 **/
    /**              should still work                                  **/
    /*********************************************************************/


    @Test
    public void testTestBoardCreation() {
        Board testBoard = Board.createBoard(GameType.TEST);
        assertEquals(testBoard.getGameType(), GameType.TEST);
    }

    @Test
    public void testClassicBoardCreation() {
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        assertEquals(classicBoard.getGameType(), GameType.CLASSIC);
    }

    @Test
    public void testTestBoardInitialization() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        /** test that PacMan is not null after initialization **/
        assertNotNull(testBoard.getPacMan());
        /** test that PacMan is an initial position **/
        assertEquals(testBoard.getPacMan().getX(), 35);
        assertEquals(testBoard.getPacMan().getY(), 75);
        assertEquals(testBoard.getPacMan().getCurrentTile(), new TilePosition(9,4));
        /** test that the maze is not null after initialization **/
        assertNotNull(testBoard.getMaze());
        assertEquals(testBoard.getMaze().getWidth(),9);
        assertEquals(testBoard.getMaze().getHeight(), 17);
    }

    @Test
    public void testClassicBoardInitialization() throws PacManException {
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        classicBoard.initialize();
        /** test that PacMan is not null after initialization **/
        assertNotNull(classicBoard.getPacMan());
        assertEquals(classicBoard.getPacMan().getType(), ActorType.PACMAN);
        /** test that PacMan is an initial position **/
        assertEquals(classicBoard.getPacMan().getX(), 112);
        assertEquals(classicBoard.getPacMan().getY(), 211);
        assertEquals(classicBoard.getPacMan().getCurrentTile(), new TilePosition(26, 14));
        /** test that the maze is not null after initialization **/
        assertNotNull(classicBoard.getMaze());
        assertEquals(classicBoard.getMaze().getWidth(),28);
        assertEquals(classicBoard.getMaze().getHeight(), 36);
    }

    @Test
    public void testStartActor() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        /** test that PacMan has a direction when the game starts **/
        assertEquals(testBoard.getPacMan().getDirection(), Direction.LEFT);
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        classicBoard.initialize();
        classicBoard.startActors();
        assertEquals(classicBoard.getPacMan().getDirection(), Direction.LEFT);
        assertNull(classicBoard.getPacMan().getIntention());
    }

    @Test
    public void testNextFrameMoving() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        TilePosition pos = pacman.getCurrentTile(); // initial tile position
        testBoard.nextFrame(); // PacMan moves one step to the left
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        assertEquals(pacman.getCurrentTile(), pos); // the tile is still the same
        // We move 3 more pixels to the left
        testBoard.nextFrame();
        testBoard.nextFrame();
        testBoard.nextFrame();
        TilePosition newPos = new TilePosition(pos.getLine(), pos.getCol() - 1);
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y);
        assertEquals(pacman.getCurrentTile(), newPos); // new tile
    }



    /*********************************************************************/
    /**                    Step 2 Tests                                 **/
    /**                                                                 **/
    /*********************************************************************/



    @Test
    public void testInitialScore() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        assertEquals(testBoard.getScore(), 0);
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        classicBoard.initialize();
        assertEquals(classicBoard.getScore(), 0);
    }

    /**
     * Test pacman / board behaviour when eating a regular dot
     * the score must be increased by 10
     * the dot should dispaear
     * pacman should be stopped for 1 frame
     * @throws PacManException
     */
    @Test
    public void testEatDot() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int nbdots = maze.getNumberOfDots();
        // Pacman is in (9,4)
        // There is a small dot in (9,2)
        assertEquals(maze.getTile(9,2), Tile.SD);
        // We move to the left
        testBoard.nextFrame();
        // The dot is still here, the score is zero
        assertEquals(maze.getTile(9,2), Tile.SD);
        assertEquals(testBoard.getScore(), 0);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,2))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        // we have reached the tile
        assertEquals(maze.getTile(9,2), Tile.EE); // Pacman has eater the dot
        assertEquals(testBoard.getScore(), 10);
        assertEquals(maze.getNumberOfDots(), nbdots - 1);
        // Pacman stops for 1 frame
        int x = pacman.getX();
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        // then moves again
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x-1);
    }

    /**
     * Test pacman / board behaviour when eating a dot on a "no up turn" tile (ND tile)
     * the score must be increased by 10
     * the dot should dispaear (the tile is replaced by NT)
     * pacman should be stopped for 1 frame
     * @throws PacManException
     */
    @Test
    public void testEatDotNoUpTurn() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        testBoard.startActors();
        pacman.setDirection(Direction.RIGHT);
        Maze maze = testBoard.getMaze();
        int nbdots = maze.getNumberOfDots();
        // Pacman is in (9,4)
        // There is a small dot of type ND in (9,6)
        assertEquals(maze.getTile(9,6), Tile.ND);
        // We move to the right
        testBoard.nextFrame();
        // The dot is still here, the score is zero
        assertEquals(maze.getTile(9,6), Tile.ND);
        assertEquals(testBoard.getScore(), 0);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,6))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        // we have reached the tile
        assertEquals(maze.getTile(9,6), Tile.NT); // Pacman has eaten the dot
        assertEquals(testBoard.getScore(), 10);
        assertEquals(maze.getNumberOfDots(), nbdots - 1);
        // Pacman stops for 1 frame
        int x = pacman.getX();
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        // then moves again
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x+1);
    }

    /**
     * We test eating a big dot
     * Similar as small dot but score is increased by 50
     * pacman is stopped for 3 frames
     * @throws PacManException
     */
    @Test
    public void testEatBigDot() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        // Pacman is in (9,4)
        // We put a big dot in (9,2)
        maze.setTile(9,2, Tile.BD);
        assertEquals(maze.getTile(9,2), Tile.BD);
        int nbdots = maze.getNumberOfDots();
        // We move to the left
        testBoard.nextFrame();
        // The dot is still here, the score is zero
        assertEquals(maze.getTile(9,2), Tile.BD);
        assertEquals(testBoard.getScore(), 0);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,2))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        // we have reached the tile
        assertEquals(maze.getTile(9,2), Tile.EE); // Pacman has eater the dot
        assertEquals(testBoard.getScore(), 50);
        assertEquals(maze.getNumberOfDots(), nbdots -1);
        // Pacman stops for 3 frames
        int x = pacman.getX();
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        // then moves again
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x-1);
    }

    @Test
    public void testEatManyDots() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();

        testBoard.startActors();
        pacman.setDirection(Direction.UP);
        Maze maze = testBoard.getMaze();
        int nbdots = maze.getNumberOfDots();
        // Pacman is in (9,4)
        // We eat all the dots up to (3,4)
        while (!pacman.getCurrentTile().equals(new TilePosition(3,4))) {
            testBoard.nextFrame();
        }
        assertEquals(testBoard.getScore(), 90);
        assertEquals(maze.getNumberOfDots(), nbdots - 5);
    }

    @Test
    public void testBoardState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getBoardState(), BoardState.INITIAL);
        board.startActors();
        board.start();
        assertEquals(board.getBoardState(), BoardState.STARTED);
    }

    @Test
    public void testBoardStateEndLevel() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        Maze maze = testBoard.getMaze();
        // We remove all dots except one
        for(int i =0; i < maze.getHeight(); i++) {
            for(int j=0; j < maze.getWidth(); j++) {
                if(maze.getTile(i,j).hasDot() && (i != 9 || j != 2)) {
                    maze.setTile(i,j,maze.getTile(i,j).clearDot());
                }
            }
        }
        testBoard.startActors();
        testBoard.start();
        assertEquals(testBoard.getBoardState(), BoardState.STARTED);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,2))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        assertEquals(testBoard.getBoardState(), BoardState.LEVEL_OVER);
    }

    @Test
    public void testLifeOver() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor pacman = board.getPacMan();
        board.startActors();
        // At some point, a ghost should reach PacMan and the game is over
        boolean eaten = false;
        while(!eaten) {
            TilePosition pos = pacman.getCurrentTile();
            for(Ghost ghost : board.getGhosts()) {
                TilePosition ghostPos = ghost.getCurrentTile();
                if(pos.equals(ghostPos)) {
                    eaten = true;
                }
            }
            board.nextFrame();
        }
        // They are on the same tile, the game should be over
        assertEquals(board.getBoardState(), BoardState.LIFE_OVER);
    }

    /*********************************************************************/
    /**                    Step 3 Tests                                 **/
    /*********************************************************************/

    @Test
    public void testLevel() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getLevel(), 1);
        board.initializeNewLevel(2);
        assertEquals(board.getLevel(), 2);
    }

    @Test
    public void testBoardStateLevel() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        board.initializeNewLevel(2);
        assertEquals(board.getBoardState(), BoardState.INITIAL);
        board.startActors();
        board.start();
        assertEquals(board.getBoardState(), BoardState.STARTED);
    }

    @Test
    public void testSetGetNumberOfLives() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getNumberOfLives(), 3);  //On commence avec 3 vies par défaut dans notre code
        board.setNumberOfLives(5);
        assertEquals(board.getNumberOfLives(), 5);
    }

    @Test
    public void testInitializeNewLife() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        board.initializeNewLife();
        assertEquals(board.getBoardState(), BoardState.INITIAL);
        assertEquals(board.getNumberOfLives(), 3);  //on comence avec 3 vies
        board.startActors();
        board.start();
        assertEquals(board.getBoardState(), BoardState.STARTED);
    }

    @Test
    public void testGameOver() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        board.startActors();
        board.start();
        // At some point, the board state should change to LIFE_OVER
        while(board.getBoardState() == BoardState.STARTED) {
            board.nextFrame();
        }
        assertEquals(board.getBoardState(), BoardState.LIFE_OVER);
        assertEquals(board.getScore(), 70);
        assertEquals(board.getMaze().getNumberOfDots(), 237);
        board.initializeNewLife();
        assertEquals(board.getNumberOfLives(), 2);
        assertEquals(board.getScore(), 70);
        assertEquals(board.getMaze().getNumberOfDots(), 237);
        board.start();
        // At some point, the board state should change to LIFE_OVER
        while(board.getBoardState() == BoardState.STARTED) {
            board.nextFrame();
        }
        assertEquals(board.getBoardState(), BoardState.LIFE_OVER);
        board.initializeNewLife();
        assertEquals(board.getNumberOfLives(), 1);
        assertEquals(board.getScore(), 70);
        assertEquals(board.getMaze().getNumberOfDots(), 237);
        board.start();
        // At some point, the board state should change to GAME_OVER
        while(board.getBoardState() == BoardState.STARTED) {
            board.nextFrame();
        }
        assertEquals(board.getBoardState(), BoardState.GAME_OVER);
        assertEquals(board.getScore(), 70);
        assertEquals(board.getMaze().getNumberOfDots(), 237);
    }

    @Test
    public void testCreateGhosts() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        List<Ghost> ghosts = board.getGhosts();
        assertEquals(ghosts.size(), 4);
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        ghosts = testBoard.getGhosts();
        assertEquals(ghosts.size(), 0);
        assertNull(testBoard.getGhost(GhostType.BLINKY));
    }



    /**
     * Common function used for testing Scatter / chase alternance in different levels
     * @param board
     * @param stateTimes
     */
    @Test
    public void testStateTimes(Board board, int[] stateTimes) {

        GhostState state = GhostState.SCATTER;
        for(int i = 0; i < stateTimes.length ; i++) {
            for(int j = 0; j < stateTimes[i] ; j++) {
                for(Ghost ghost : board.getGhosts()) {
                    //assertEquals(ghost.getStateCounter(), count);
                    //System.out.println(ghost);
                    //System.out.println(count);
                    assertEquals(ghost.getGhostState(), state);
                }
                board.nextFrame();
            }
            switch (state){
                case CHASE :
                    state = GhostState.SCATTER;
                    break;
                case SCATTER:
                    state = GhostState.CHASE;
                    break;
            }
        }
        // Should then stay in chase for ever
        for(int j=0; j < 2000; j++) {
            for(Ghost ghost : board.getGhosts()) {
                assertEquals(ghost.getGhostState(), GhostState.CHASE);
            }
        }

    }

    @Test
    public void testStateTimeLevel1() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());
        board.start();
        testStateTimes(board, new int[]{420, 1200, 420, 1200, 300, 1200, 300});
    }

    @Test
    public void testStateTimeLevel2To4() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        for(int level =2; level < 5; level++) {
            board.initializeNewLevel(level);
            board.startActors();
            testStateTimes(board, new int[]{420, 1200, 420, 1200, 300, 61980,1});
        }
    }

    @Test
    public void testStateTimeLevel5to6() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        for(int level =5; level < 7; level++) {
            board.initializeNewLevel(level);
            board.startActors();
            testStateTimes(board, new int[]{300, 1200, 300, 1200, 300, 62220, 1});
        }
    }

    @Test
    public void testStateTimeLevel1NewLife() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());

        // We make a few frames
        for(int i =0; i < 100; i++) {
            board.nextFrame();
        }
        board.initializeNewLife();
        Configurations.blockGhosts(board.getMaze());
        testStateTimes(board, new int[]{420, 1200, 420, 1200, 300, 1200, 300});
    }

    @Test
    public void testDisableStateTime() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost ghost = board.getGhost(GhostType.BLINKY);
        ghost.changeGhostState(GhostState.CHASE);
        board.startActors();
        board.disableStateTime();
        Configurations.blockGhosts(board.getMaze());
        board.start();
        for(int j = 0; j < 2000; j++) {
            assertEquals(ghost.getGhostState(), GhostState.CHASE);
            board.nextFrame();
        }
    }

    /**
     * Common function to test Frighten state time in different level
     * @param board
     * @param frightenTime
     * @param frightenEndTime
     */
    public void testFrightenState(Board board, int frightenTime, int frightenEndTime) {
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        TilePosition pos = pacman.getCurrentTile();
        TilePosition next = new TilePosition(pos.getLine(), pos.getCol() -1);
        GhostState initial = board.getGhost(GhostType.BLINKY).getGhostState();
        // We place a big dot in front of pacman
        maze.setTile(next, Tile.BD);
        board.startActors();
        while(!pacman.getCurrentTile().equals(next)) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot, the ghosts should enter frightened mode
        // They stay in frighten mode for 360 frames
        // (depending on which order you do your next frame actions,
        // you might need a -1 on the loop end or not)
        for(int i = 0; i < frightenTime-1 ; i++) {
            for(Ghost ghost: board.getGhosts()) {
                assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED);
            }
            board.nextFrame();
        }
        // Now they should be in frighten_end mode for 150 frames
        for(int i = 0; i < frightenEndTime; i++) {
            for(Ghost ghost: board.getGhosts()) {
                assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED_END);
            }
            board.nextFrame();
        }
        // Now they should be back in their initial mode (scatter or chase depending on level)
        for(Ghost ghost: board.getGhosts()) {
            assertEquals(ghost.getGhostState(), initial);
        }
    }

    /**
     * Common function to test that no Frighten state is setup on certain levels
     * @param board
     */
    public void testNoFrightenState(Board board) {
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        TilePosition pos = pacman.getCurrentTile();
        TilePosition next = new TilePosition(pos.getLine(), pos.getCol() -1);
        GhostState initial = board.getGhost(GhostType.BLINKY).getGhostState();
        // We place a big dot in front of pacman
        maze.setTile(next, Tile.BD);
        board.startActors();
        while(!pacman.getCurrentTile().equals(next)) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot, but the ghosts should NOT enter frightened mode
        for(Ghost ghost : board.getGhosts()) {
            assertEquals(ghost.getGhostState(), initial);
        }
    }

    @Test
    public void testFrightenStateLevel1() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        testFrightenState(board, 360, 150);
    }

    @Test
    public void testFrightenStateTimeAllLevels() throws PacManException {
        int[] frightenStateTimes = {360, 300, 240, 180, 120, 300, 120, 120, 60, 300, 120, 60, 60, 180, 60, 60, -1, 60,};
        int[] frightEndTimes = {150, 150, 150, 150, 150, 150, 150, 150, 90, 150, 150, 90, 90, 150, 90, 90, -1, 90};
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        for(int level = 1; level < 19; level++) {
            if(frightenStateTimes[level-1] > 0) {
                board.initializeNewLevel(level);
                Configurations.blockGhosts(board.getMaze());
                testFrightenState(board, frightenStateTimes[level-1], frightEndTimes[level-1]);
            }
        }
    }

    @Test
    public void testNoFrightenStateLevels17_19andup() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        int[] levels = {17, 19, 20, 21, 22, 23};
        for(int level : levels) {
            board.initializeNewLevel(level);
            Configurations.blockGhosts(board.getMaze());
            testNoFrightenState(board);
        }
    }

    @Test
    public void testFrightenChangeDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        TilePosition pos = pacman.getCurrentTile();
        TilePosition next = new TilePosition(pos.getLine(), pos.getCol() -1);
        GhostState initial = board.getGhost(GhostType.BLINKY).getGhostState();
        // We place a big dot in front of pacman
        maze.setTile(next, Tile.BD);
        board.startActors();
        while(!pacman.getCurrentTile().equals(next)) {
            board.nextFrame();
        }
        // Pacman eats the dot
        // Blinky should have a go right intention as turining into frighten mode
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        assertEquals(blinky.getIntention(), Direction.RIGHT);
    }

    @Test
    public void testFrightenChangeDirectionLevel19() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.initializeNewLevel(19);
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        TilePosition pos = pacman.getCurrentTile();
        TilePosition next = new TilePosition(pos.getLine(), pos.getCol() -1);
        GhostState initial = board.getGhost(GhostType.BLINKY).getGhostState();
        // We place a big dot in front of pacman
        maze.setTile(next, Tile.BD);
        board.startActors();
        while(!pacman.getCurrentTile().equals(next)) {
            board.nextFrame();
        }
        // Pacman eats the dot
        // Blinky should have a go right intention even though it is not in frighten mode
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        assertEquals(blinky.getIntention(), Direction.RIGHT);
    }



    @Test
    public void testRandomDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        int[] counter = {0,0,0,0};
        for(int i = 0; i < 1000; i++) {
            Direction dir = board.getRandomDirection();
            assertNotNull(dir);
            counter[dir.ordinal()]++;
        }
        // We check that each direction appears between 200 and 300 times
        assertTrue(counter[0] > 200);
        assertTrue(counter[1] > 200);
        assertTrue(counter[2] > 200);
        assertTrue(counter[3] > 200);
        assertTrue(counter[0] < 300);
        assertTrue(counter[1] < 300);
        assertTrue(counter[2] < 300);
        assertTrue(counter[3] < 300);
    }

    @Test
    public void testPenEntry() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.penEntry(), new TilePosition(14, 13));
    }

    @Test
    public void testMinMaxYPen() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.minYPen(), 134);
        assertEquals(board.maxYPen(), 144);
    }

    @Test
    public void testGhostPenPositions() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.penGhostXPosition(GhostType.BLINKY), 112);
        assertEquals(board.penGhostYPosition(GhostType.BLINKY), 139);
        assertEquals(board.penGhostXPosition(GhostType.PINKY), 112);
        assertEquals(board.penGhostYPosition(GhostType.PINKY), 139);
        assertEquals(board.penGhostXPosition(GhostType.INKY), 96);
        assertEquals(board.penGhostYPosition(GhostType.INKY), 139);
        assertEquals(board.penGhostXPosition(GhostType.CLYDE), 128);
        assertEquals(board.penGhostYPosition(GhostType.CLYDE), 139);
    }

    @Test
    public void testOutPenPosition() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.outPenXPosition(), 112);
        assertEquals(board.outPenYPosition(), 115);
    }

    @Test
    public void testEat1Ghost() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableGhost(GhostType.PINKY);
        board.disableGhost(GhostType.INKY);
        board.disableGhost(GhostType.CLYDE);
        board.initialize();
        board.startActors();
        Configurations.smallSquareEatingGhost(board);
        Actor pacman = board.getPacMan();
        Ghost ghost = board.getGhost(GhostType.BLINKY);
        Maze maze = board.getMaze();
        board.start();
        for(int i = 0; i < 20 ; i++) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot in front of them

        assertEquals(board.getScore(), 50);
        assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED);
        while (!pacman.getCurrentTile().equals(ghost.getCurrentTile())) {
            board.nextFrame();
        }
        assertEquals(ghost.getGhostState(), GhostState.DEAD);
        assertEquals(board.getScore(), 250);
    }

    @Test
    public void testScoreEat2Ghosts() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableGhost(GhostType.INKY);
        board.disableGhost(GhostType.CLYDE);
        board.initialize();
        board.startActors();
        Configurations.smallSquareEatingGhost(board);
        Actor pacman = board.getPacMan();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Maze maze = board.getMaze();

        for(int i = 0; i < 20; i++) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot in front of them
        assertEquals(board.getScore(), 50);
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        while (true) {
            board.nextFrame();
            int nb = 0;
            for(Ghost ghost : board.getGhosts()) {
                if(ghost.getGhostState() == GhostState.DEAD) {
                    nb++;
                }
            }
            if(nb == 1) {
                assertEquals(board.getScore(), 250);
            }
            if(nb == 2) {
                assertEquals(board.getScore(), 650);
                break;
            }
        }
    }

    @Test
    public void testScoreEat3Ghosts() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableGhost(GhostType.CLYDE);
        board.initialize();
        board.startActors();
        Configurations.smallSquareEatingGhost(board);
        Actor pacman = board.getPacMan();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Maze maze = board.getMaze();

        for(int i = 0; i < 20; i++) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot in front of them
        assertEquals(board.getScore(), 50);
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        while (true) {
            board.nextFrame();
            int nb = 0;
            for(Ghost ghost : board.getGhosts()) {
                if(ghost.getGhostState() == GhostState.DEAD) {
                    nb++;
                }
            }
            if(nb == 1) {
                assertEquals(board.getScore(), 250);
            }
            if(nb == 2) {
                assertEquals(board.getScore(), 650);
            }
            if(nb == 3) {
                assertEquals(board.getScore(), 1450);
                break;
            }
        }
    }

    @Test
    public void testScoreEat4Ghosts() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.smallSquareEatingGhost(board);
        Actor pacman = board.getPacMan();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Maze maze = board.getMaze();
        board.start();

        for(int i = 0; i < 20; i++) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot in front of them
        assertEquals(board.getScore(), 50);
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        while (true) {
            board.nextFrame();
            int nb = 0;
            for(Ghost ghost : board.getGhosts()) {
                if(ghost.getGhostState() == GhostState.DEAD) {
                    nb++;
                }
            }
            if(nb == 1) {
                assertEquals(board.getScore(), 250);
            }
            if(nb == 2) {
                assertEquals(board.getScore(), 650);
            }
            if(nb == 3) {
                assertEquals(board.getScore(), 1450);
            }
            if(nb == 4) {
                assertEquals(board.getScore(), 3050);
                break;
            }
        }
    }

    @Test
    public void testScoreEat1GhostTwice() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        board.disableGhost(GhostType.PINKY);
        board.disableGhost(GhostType.INKY);
        board.disableGhost(GhostType.CLYDE);
        Configurations.smallSquareEatingGhost(board);
        Actor pacman = board.getPacMan();
        Ghost ghost = board.getGhost(GhostType.BLINKY);
        Maze maze = board.getMaze();
        maze.setTile(19,9, Tile.BD);

        for(int i = 0; i < 20; i++) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot in front of them
        assertEquals(board.getScore(), 50);
        assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED);
        while (!pacman.getCurrentTile().equals(ghost.getCurrentTile())) {
            board.nextFrame();
        }
        assertEquals(ghost.getGhostState(), GhostState.DEAD);

        assertEquals(board.getScore(), 250);
        while(ghost.getGhostState() == GhostState.DEAD) {
            board.nextFrame();
        }

        while (ghost.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        pacman.setIntention(Direction.UP);
        while (ghost.getGhostState() != GhostState.DEAD) {
            board.nextFrame();
        }
        // PacMan has re-eaten the ghost on a different frighten session
        assertEquals(board.getScore(), 500);


    }

    @Test
    public void testGhostDotCounters() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Ghost inky = board.getGhost(GhostType.INKY);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Actor pacman = board.getPacMan();
        // We reduce the dot counter limit for testing purpose
        inky.getDotCounter().setLimit(7);
        clyde.getDotCounter().setLimit(8);

        // Let's eat one dot
        while(board.getScore() == 0) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 1);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertNotEquals(pinky.getGhostPenState(), GhostPenState.IN);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        // Pacman moves to the wall, eating 7 dots in total
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 7);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(inky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        pacman.setIntention(Direction.DOWN);
        // Pacman moves to the wall, eating 3 more dots
        board.nextFrame();
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 7);
        assertEquals(clyde.getDotCounter().getValue(), 3);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        pacman.setIntention(Direction.LEFT);
        // Pacman moves to the wall, eating 5 more dots
        board.nextFrame();
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 7);
        assertEquals(clyde.getDotCounter().getValue(), 8);
        assertEquals(clyde.getGhostPenState(), GhostPenState.GET_OUT);
    }

    @Test
    public void testNoDotCounterInitialization() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Counter noDot = board.noDotCounter();
        assertNotNull(noDot);
        assertEquals(noDot.getValue(), 0);
        assertEquals(noDot.getLimit(), 4*60);
    }

    @Test
    public void testNoDotCounterInitilizationOtherLevels() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.initializeNewLevel(4);
        assertEquals(board.noDotCounter().getLimit(), 4*60);
        board.initializeNewLevel(5);
        assertEquals(board.noDotCounter().getLimit(), 3*60);
        board.initializeNewLevel(19);
        assertEquals(board.noDotCounter().getLimit(), 3*60);
    }

    @Test
    public void testNoDotCounter() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        board.nextFrame();
        assertEquals(board.noDotCounter().getValue(), 1);
        board.nextFrame();
        assertEquals(board.noDotCounter().getValue(), 2);
        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();
        while (maze.getNumberOfDots() == initialDots) {
            board.nextFrame();
        }
        // Pacman just ate a dot, the nodotcounter should be reset
        assertEquals(board.noDotCounter().getValue(), 0);
    }

    @Test
    public void testNoDotCounterNewLifeReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        board.nextFrame();
        board.nextFrame();
        board.initializeNewLife();
        assertEquals(board.noDotCounter().getValue(), 0);
    }

    @Test
    public void testNoDotCounterNewLevelReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        board.nextFrame();
        board.nextFrame();
        board.initializeNewLevel(2);
        assertEquals(board.noDotCounter().getValue(), 0);
    }

    @Test
    public void testNoDotCounterGetOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());

        Actor pacman = board.getPacMan();
        while(!pacman.isBlocked()) {
            board.nextFrame();
        }
        // Now pacman is blocked and has stopped eating dots
        int v = board.noDotCounter().getValue();
        while (v < board.noDotCounter().getLimit()) {
            board.nextFrame();
            v++;
        }
        // The no do counter should have reached its limit and released a ghost
        Ghost inky = board.getGhost(GhostType.INKY);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        assertEquals(inky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        // The no dot counter should have been reset
        assertEquals(board.noDotCounter().getValue(), 0);
        v = 0;
        // We keep not eating dots
        while (v < board.noDotCounter().getLimit()) {
            board.nextFrame();
            v++;
        }
        assertEquals(clyde.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(board.noDotCounter().getValue(), 0);
        v = 0;
        // We keep not eating dots
        while (v < board.noDotCounter().getLimit()) {
            board.nextFrame();
            v++;
        }
        // Even when no ghost are released, the no dot counter should be reset
        assertEquals(board.noDotCounter().getValue(), 0);
    }

    @Test
    public void testNoDotCounterGhostCounters() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());

        Actor pacman = board.getPacMan();
        while(!pacman.isBlocked()) {
            board.nextFrame();
        }
        // We eat no dot until Inky is released
        Ghost inky = board.getGhost(GhostType.INKY);
        while (inky.getGhostPenState() == GhostPenState.IN) {
            board.nextFrame();
        }
        // Now we start eating dot again
        // This should affect CLyde's dot counter
        pacman.setIntention(Direction.UP);
        Maze maze = board.getMaze();
        int nbdots = maze.getNumberOfDots();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        while (maze.getNumberOfDots() == nbdots) {
            board.nextFrame();
        }
        assertEquals(clyde.getDotCounter().getValue(), 1);
    }

    @Test
    public void testSpecialDotCounter() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Ghost inky = board.getGhost(GhostType.INKY);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        clyde.getDotCounter().setLimit(5);
        Actor pacman = board.getPacMan();
        board.initializeNewLife();

        assertEquals(board.specialDotCounter().getValue(), 0);
        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();

        // Let's eat one dot
        while(maze.getNumberOfDots() == initialDots) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 1);
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        // Pacman moves to the wall, eating 7 dots in total
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }

        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 7);
        // The special dot counter has reached 7 and should release Pinky
        assertEquals(pinky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        pacman.setIntention(Direction.UP);
        while (initialDots - maze.getNumberOfDots() < 17) {
            board.nextFrame();
        }
        board.nextFrame();
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 17);
        assertEquals(inky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 29);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        pacman.setIntention(Direction.RIGHT);
        board.nextFrame();
        while (initialDots - maze.getNumberOfDots() < 32) {
            board.nextFrame();
        }
        board.nextFrame();
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 32);
        // The special counter has reached 32 and clyde is inside : now the system will roll back to ghost counters
        // But Clyde stays inside
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 3);
        assertEquals(board.specialDotCounter().getValue(), 32);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        pacman.setIntention(Direction.DOWN);
        while (initialDots - maze.getNumberOfDots() < 37) {
            board.nextFrame();
        }
        board.nextFrame();
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 5);
        assertEquals(board.specialDotCounter().getValue(), 32);
        assertEquals(clyde.getGhostPenState(), GhostPenState.GET_OUT);
    }

    @Test
    public void testSpecialDotCounterNoClyde() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        board.disableGhost(GhostType.CLYDE);
        Configurations.blockGhosts(board.getMaze());
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Ghost inky = board.getGhost(GhostType.INKY);
        Actor pacman = board.getPacMan();
        board.initializeNewLife();

        assertEquals(board.specialDotCounter().getValue(), 0);
        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();

        // Let's eat one dot
        while(maze.getNumberOfDots() == initialDots) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 1);
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        // Pacman moves to the wall, eating 7 dots in total
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }

        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 7);
        // The special dot counter has reached 7 and should release Pinky
        assertEquals(pinky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        pacman.setIntention(Direction.UP);
        while (initialDots - maze.getNumberOfDots() < 17) {
            board.nextFrame();
        }
        board.nextFrame();
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 17);
        assertEquals(inky.getGhostPenState(), GhostPenState.GET_OUT);
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 29);
        pacman.setIntention(Direction.RIGHT);
        board.nextFrame();
        while (initialDots - maze.getNumberOfDots() < 32) {
            board.nextFrame();
        }
        board.nextFrame();
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 32);
        // The special counter has reached 32 but Clyde is out of the game (so not in the pen house)
        // The special counter works indefinitly
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 35);
    }

    @Test
    public void testSpecialDotCounterNoClyde2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();

        Ghost pinky = board.getGhost(GhostType.PINKY);
        Ghost inky = board.getGhost(GhostType.INKY);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Actor pacman = board.getPacMan();
        board.initializeNewLife();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());
        assertEquals(board.specialDotCounter().getValue(), 0);
        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();
        // We artificially force clyde out of the pen
        clyde.setGhostPenState(GhostPenState.GET_OUT);
        // Let's eat one dot
        while(maze.getNumberOfDots() == initialDots) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 1);
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        assertNotEquals(clyde.getGhostPenState(), GhostPenState.IN);
        // Pacman moves to the wall, eating 7 dots in total
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }

        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 7);
        // The special dot counter has reached 7 and should release Pinky
        assertEquals(pinky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        pacman.setIntention(Direction.UP);
        while (initialDots - maze.getNumberOfDots() < 17) {
            board.nextFrame();
        }
        board.nextFrame();
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 17);
        assertEquals(inky.getGhostPenState(), GhostPenState.GET_OUT);
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 29);
        pacman.setIntention(Direction.RIGHT);
        board.nextFrame();
        while (initialDots - maze.getNumberOfDots() < 32) {
            board.nextFrame();
        }
        board.nextFrame();
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 32);
        // The special counter has reached 32 but Clyde is out the pen
        // The special counter works indefinitly
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(board.specialDotCounter().getValue(), 35);
    }

    @Test
    public void testSpecialDotCounterGhostStuckForEver() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();

        Ghost pinky = board.getGhost(GhostType.PINKY);
        Ghost inky = board.getGhost(GhostType.INKY);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Actor pacman = board.getPacMan();
        board.initializeNewLife();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());
        assertEquals(board.specialDotCounter().getValue(), 0);
        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();
        // We artificially force clyde out of the pen
        clyde.setGhostPenState(GhostPenState.GET_OUT);
        // Let's eat one dot
        while(maze.getNumberOfDots() == initialDots) {
            board.nextFrame();
        }
        // Pacman moves to the wall, eating 7 dots in total
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        pacman.setIntention(Direction.UP);
        while (initialDots - maze.getNumberOfDots() < 17) {
            board.nextFrame();
        }
        board.nextFrame();
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        pacman.setIntention(Direction.RIGHT);
        board.nextFrame();
        while (initialDots - maze.getNumberOfDots() < 32) {
            board.nextFrame();
        }
        board.nextFrame();
        // The special counter has reached 32 but Clyde is out the pen
        // The special counter works indefinitly
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(board.specialDotCounter().getValue(), 35);
        // Let's kill the ghost and wait for them to enter the pen
        pinky.changeGhostState(GhostState.DEAD);
        inky.changeGhostState(GhostState.DEAD);
        clyde.changeGhostState(GhostState.DEAD);
        // We also increase the limit of the nodotcounter so that it does not release the ghosts
        board.noDotCounter().setLimit(100*60);
        while (pinky.getGhostPenState() != GhostPenState.IN) {
            board.nextFrame();
        }
        while (inky.getGhostPenState() != GhostPenState.IN) {
            board.nextFrame();
        }
        while (clyde.getGhostPenState() != GhostPenState.IN) {
            board.nextFrame();
        }
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        // Now, the ghost are trapped in the ghost pen
        // The special counter stays activated and never goes back to ghost counters
        pacman.setIntention(Direction.DOWN);
        board.nextFrame();
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        assertEquals(board.specialDotCounter().getValue(), 39);
        assertEquals(pinky.getDotCounter().getValue(), 0);
        assertEquals(inky.getDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 0);
    }

    @Test
    public void testSpecialDotCounterNewLifeReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.blockGhosts(board.getMaze());
        board.initializeNewLife();

        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();
        while (initialDots - maze.getNumberOfDots() < 3) {
            board.nextFrame();
        }
        assertEquals(board.specialDotCounter().getValue(), 3);
        board.initializeNewLife();
        board.startActors();
        assertEquals(board.specialDotCounter().getValue(), 0);
    }

    @Test
    public void testSpecialDotCounterNewLevelReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        board.initializeNewLife();
        board.startActors();
        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();
        while (initialDots - maze.getNumberOfDots() < 3) {
            board.nextFrame();
        }
        assertEquals(board.specialDotCounter().getValue(), 3);
        board.initializeNewLevel(2);
        board.startActors();
        assertEquals(board.specialDotCounter().getValue(), 0);
    }

    @Test
    public void testSpecialDotCounterNewLifeNewLevel() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        board.initializeNewLife();
        board.startActors();
        Maze maze = board.getMaze();
        int initialDots = maze.getNumberOfDots();
        while (initialDots - maze.getNumberOfDots() < 3) {
            board.nextFrame();
        }
        assertEquals(board.specialDotCounter().getValue(), 3);
        board.initializeNewLevel(2);
        board.startActors();
        maze = board.getMaze();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        while (initialDots - maze.getNumberOfDots() < 3) {
            board.nextFrame();
        }
        assertEquals(board.specialDotCounter().getValue(), 0);
        assertEquals(clyde.getDotCounter().getValue(), 3);
    }

    @Test
    public void testFrightenStateSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        TilePosition pos = pacman.getCurrentTile();
        TilePosition next = new TilePosition(pos.getLine(), pos.getCol() -1);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        // We place a big dot in front of pacman
        maze.setTile(next, Tile.BD);
        board.startActors();
        while(!pacman.getCurrentTile().equals(next)) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot, the ghosts should enter frightened mode
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        while (blinky.getGhostState() == GhostState.FRIGHTENED) {
            for(Ghost ghost: board.getGhosts()) {
                assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED);
                if(ghost.getGhostPenState() == GhostPenState.OUT) {
                    assertEquals(ghost.getSpeed(), board.getFrightGhostSpeed());
                } else {
                    assertEquals(ghost.getSpeed(), board.getTunnelGhostSpeed());
                }
            }
            board.nextFrame();
        }
        // Now they should be in frighten_end mode
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED_END);
        while(blinky.getGhostState() == GhostState.FRIGHTENED_END) {
            for(Ghost ghost: board.getGhosts()) {
                assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED_END);
                if(ghost.getGhostPenState() == GhostPenState.OUT) {
                    assertEquals(ghost.getSpeed(), board.getFrightGhostSpeed());
                } else {
                    assertEquals(ghost.getSpeed(), board.getTunnelGhostSpeed());
                }
            }
            board.nextFrame();
        }
    }

    @Test
    public void testFrightenStateSpeedLevel4() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.initializeNewLevel(4);
        Configurations.blockGhosts(board.getMaze());
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        TilePosition pos = pacman.getCurrentTile();
        TilePosition next = new TilePosition(pos.getLine(), pos.getCol() -1);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        // We place a big dot in front of pacman
        maze.setTile(next, Tile.BD);
        board.startActors();
        while(!pacman.getCurrentTile().equals(next)) {
            board.nextFrame();
        }
        // Pacman has eaten the big dot, the ghosts should enter frightened mode
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        while (blinky.getGhostState() == GhostState.FRIGHTENED) {
            for(Ghost ghost: board.getGhosts()) {
                assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED);
                if(ghost.getGhostPenState() == GhostPenState.OUT) {
                    assertEquals(ghost.getSpeed(), board.getFrightGhostSpeed());
                } else {
                    assertEquals(ghost.getSpeed(), board.getTunnelGhostSpeed());
                }
            }
            board.nextFrame();
        }
        // Now they should be in frighten_end mode
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED_END);
        while(blinky.getGhostState() == GhostState.FRIGHTENED_END) {
            for(Ghost ghost: board.getGhosts()) {
                assertEquals(ghost.getGhostState(), GhostState.FRIGHTENED_END);
                if(ghost.getGhostPenState() == GhostPenState.OUT) {
                    assertEquals(ghost.getSpeed(), board.getFrightGhostSpeed());
                } else {
                    assertEquals(ghost.getSpeed(), board.getTunnelGhostSpeed());
                }
            }
            board.nextFrame();
        }
    }

    @Test
    public void testDeadGhostSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Configurations.smallSquareEatingGhost(board);
        Actor pacman = board.getPacMan();
        Ghost blinky = board.getGhost(GhostType.BLINKY);

        for(int i = 0; i < 20; i++) {
            board.nextFrame();
        }
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        while (true) {
            board.nextFrame();
            int nb = 0;
            for(Ghost ghost : board.getGhosts()) {
                if(ghost.getGhostState() == GhostState.DEAD) {
                    assertEquals(ghost.getSpeed(), board.getDeadGhostSpeed());
                    nb++;
                }
            }
            if(nb == board.getGhosts().size()) {
                assertEquals(board.getScore(), 3050);
                break;
            }
        }
        boolean[] hasBeenIn = new boolean[board.getGhosts().size()];
        for(int i = 0; i < hasBeenIn.length; i++) {
            hasBeenIn[i] = false;
        }
        int nbHasBeenIn =0;
        while (true) {
            board.nextFrame();
            int i = 0;
            for (Ghost ghost : board.getGhosts()) {
                if(ghost.getGhostPenState() != GhostPenState.OUT) {
                    assertEquals(ghost.getSpeed(), board.getTunnelGhostSpeed());
                }
                if((ghost.getGhostPenState() == GhostPenState.IN || ghost.getGhostPenState() == GhostPenState.GET_OUT) && !hasBeenIn[i]) {
                    nbHasBeenIn++;
                    hasBeenIn[i] = true;
                }
                i++;
            }
            if(nbHasBeenIn == board.getGhosts().size()) {
                break;
            }
        }
    }

    @Test
    public void testGetLevelPacManSpeedValues() throws PacManException{
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getLevelPacManSpeed(), 1);
        for(int i = 2; i <= 4; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getLevelPacManSpeed(), 1.14);
        }
        for(int i = 5; i <= 20; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getLevelPacManSpeed(), 1.26);
        }
        board.initializeNewLevel(21);
        assertEquals(board.getLevelPacManSpeed(), 1.14);
    }

    @Test
    public void testGetFrightPacManSpeedValues() throws PacManException{
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getFrightPacManSpeed(), 1.14);
        for(int i = 2; i <= 4; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getFrightPacManSpeed(), 1.2);
        }
        for(int i = 5; i <= 21; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getFrightPacManSpeed(), 1.26);
        }
    }

    @Test
    public void testGetLevelGhostSpeedValues() throws PacManException{
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getLevelGhostSpeed(), .94);
        for(int i = 2; i <= 4; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getLevelGhostSpeed(), 1.07);
        }
        for(int i = 5; i <= 21; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getLevelGhostSpeed(), 1.2);
        }
    }

    @Test
    public void testGetTunnelGhostSpeedValues() throws PacManException{
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getTunnelGhostSpeed(), .5);
        for(int i = 2; i <= 4; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getTunnelGhostSpeed(), .57);
        }
        for(int i = 5; i <= 21; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getTunnelGhostSpeed(), .63);
        }
    }

    @Test
    public void testGetFrightGhostSpeedValues() throws PacManException{
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getFrightGhostSpeed(), .63);
        for(int i = 2; i <= 4; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getFrightGhostSpeed(), .69);
        }
        for(int i = 5; i <= 21; i++) {
            board.initializeNewLevel(i);
            assertEquals(board.getFrightGhostSpeed(), .75);
        }
    }

    @Test
    public void testGetDeadGhostSpeedValue() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getDeadGhostSpeed(), 1.26);
    }

}
