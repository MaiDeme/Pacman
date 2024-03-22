package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.*;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import java.util.List;

public abstract class AbstractBoard implements Board {

    // Etape 1
    private final GameType gameType;
    private Maze maze;
    private Actor pacman;
    //

    // Pour les étapes 2 à 4 :
    private Bonus bonus;
    private int extraLifeScore;
    private int extraLives;
    private int level;
    private List<Ghost> ghosts;
    private int score;


    public AbstractBoard(GameType gameType) {
        this.gameType = gameType;
    }

    /**
     * Return the type of game of the board
     * Depending on the type, the maze or other initializations might be different
     *
     * @return the game type
     */
    @Override
    public GameType getGameType() {
        return gameType;
    }

    public Actor getPacMan() {
        return pacman;
    }
    /**
     * Initialization of the board
     * (loads the maze, create and place the actors, etc.)
     *
     * @throws PacManException in case something went wrong
     */
    @Override
    public abstract void initialize() throws PacManException;

    /**
     * Start the actors
     * Perform all necessary actions to start actors at the beginning of the game
     */
    public void startActors() {
        pacman.start();
       
    }

    /**
     * Return the maze
     *
     * @return the maze
     */
    public Maze getMaze() {
        return maze;
    }



    /**
     * Return PacMan
     *
     * @return the PacMan actor
     */
    /**
     * Perform all necessary actions for the next game frame
     * This might require to move the actors,
     * perform some checks, etc.
     */
    @Override
    public void nextFrame() {
        pacman.nextFrame();
    }

    /**
     * Create a board depending on the game type
     *
     * @param type a game type
     * @return the board
     */
    static Board createBoard(GameType type) throws PacManException {
        Board board = null;
        switch (type) {
            case GameType.CLASSIC:
                board = new ClassicBoard();
                board.initialize();
            case GameType.TEST:
                board = new TestBoard();
                board.initialize();
        }
        return board;
    }


    // Step 2
    // The methods below won't be used / tested before step 2

    /**
     * Return the current score of the game
     *
     * @return the score
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Return the current state of the board
     * used to detect end of level / end of game
     *
     * @return the board state
     */
    public BoardState getBoardState(){
        return null;
    }

    /**
     * Return the ghost of a given type
     * If the board does not contain such a ghost, it returns null
     *
     * @param ghostType, the type of ghost
     * @return a ghost or null
     */
    public Ghost getGhost(GhostType ghostType){
        for(Ghost g : this.ghosts){
            if (g.getGhostType() == ghostType){
                return g;
            }
        }
        return null;
    }

    /**
     * Return the list of ghosts present on the board
     *
     * @return a list of ghost (might be empty)
     */
    public List<Ghost> getGhosts(){
        return this.ghosts;
    }

    // Step 3
    // The methods below won't be used / tested before step 3

    /**
     * Return the current level of the game
     *
     * @return a positive integer
     */
    public int getLevel(){
        return this.level;
    }

    /**
     * Perfom all necessary actions to initiliaze a new level
     * (might need to load the maze, to place the actors, etc)
     *
     * @param level, a positive integer
     * @throws PacManException if anything goes wrong
     */
    public void initializeNewLevel(int level) throws PacManException{

    }

    /**
     * Sets the number of extra lives that pacman has
     *
     * @param nbLives, a non negative integer
     */
    public void setNumberOfLives(int nbLives){
        this.extraLives = nbLives;
    }

    /**
     * Return the current number of extra lives
     *
     * @return a non negative integer
     */
    public int getNumberOfLives(){
        return this.extraLives;
    }

    /**
     * Perform all necessary actions to initialize the game after a life has been lost
     * (reduce the nb of lives, replace the actors, re-initialize certain values)
     */
    public void initializeNewLife(){

    }

    /**
     * Return whether the board contain a certain type of ghost
     *
     * @param ghostType the type of ghost
     * @return true if this ghost is on the board
     */
    public boolean hasGhost(GhostType ghostType){
        return false;
    }

    /**
     * Disables a certain ghost before game initialization
     *
     * @param ghostType the type of ghost to disable
     */
    public void disableGhost(GhostType ghostType){

    }

    /**
     * Disable the use of state time before game initialization
     */
    public void disableStateTime() {

    }

    /**
     * Return a pseudo-random direction
     * The random generator is initialized once at the beginning of the game
     *
     * @return a pseudo-random direction
     */
    public Direction getRandomDirection(){
        return null;
    }

    /**
     * Re turn the tile position of the pen entry
     * (this corresponds to the ghost target when dead)
     *
     * @return a tile position
     */
    public TilePosition penEntry(){
        return null;
    }

    /**
     * Return the minimum y value a ghost can take when inside the pen
     *
     * @return a positive integer
     */
    public int minYPen(){
        return 0;
    }

    /**
     * Return the maximal y value a ghost can take when inside the pen
     *
     * @return a positive integer
     */
    public int maxYPen(){
        return 0;
    }

    /**
     * Return the x position of given ghost type inside the pen
     *
     * @param type, a ghost type
     * @return a positive integer
     */
    public int penGhostXPosition(GhostType type){
        return 0;
    }

    /**
     * Return the y position of given ghost type inside the pen
     *
     * @param type, a ghost type
     * @return a positive integer
     */
    public int penGhostYPosition(GhostType type){
        return 0;
    }

    /**
     * Return the x position used by ghost to enter / leave the ghost pen
     *
     * @return a positive integer
     */
    public int outPenXPosition(){
        return 0;
    }

    /**
     * Return the y position used by ghost to enter / leave the ghost pen
     *
     * @return a positive integer
     */
    public int outPenYPosition(){
        return 0;
    }

    /**
     * Return the counter used to count the number of successive frames without eating dots
     * This is a way to count the time passed between 2 dots are eaten
     *
     * @return a counter
     */
    public Counter noDotCounter(){
        return null;
    }

    /**
     * Return the board "special counter" used for allowing ghosts out of the pen
     *
     * @return a counter
     */
    public Counter specialDotCounter(){
        return null;
    }

    /**
     * Return the normal speed of pacman at the board current level
     *
     * @return a speed as a decimal
     */
    public double getLevelPacManSpeed(){
        return 0;
    }

    /**
     * Return the speed of pacman in "fright" mode at the board current level
     *
     * @return a speed as a decimal
     */
    public double getFrightPacManSpeed(){
        return 0;
    }

    /**
     * Return the normal speed of ghost at the board current level
     *
     * @return a speed as a decimal
     */
    public double getLevelGhostSpeed(){
        return 0;
    }

    /**
     * Return the slow speed of ghosts at current level (inside the tunnel)
     *
     * @return a speed as a decimal
     */
    public double getTunnelGhostSpeed(){
        return 0;
    }

    /**
     * return the speed of ghosts in "fright" mode at current level
     *
     * @return a speed as a decimal
     */
    public double getFrightGhostSpeed(){
        return 0;
    }

    /**
     * return the speed of ghosts when dead at current level
     *
     * @return a speed as a decimal
     */
    public double getDeadGhostSpeed(){
        return 0;
    }

    // Step 4
    // The methods below won't be used / tested before step 4

    /**
     * Sets the score value at which pacman gains a new life
     *
     * @param score, a positive integer
     */
    public void setExtraLifeScore(int score) {
        this.extraLifeScore = score;
    }

    /**
     * Return the score value at which pacman gains a new life
     *
     * @return a positive integer
     */
    public int getExtraLifeScore() {
        return this.extraLifeScore;
    }

    /**
     * Return the current bonus in the board if existing, null otherwise
     *
     * @return a Bonus or null
     */
    public Bonus getCurrentBonus(){
        return this.bonus;
    }

    /**
     * Return the bonus type associated with given level
     *
     * @param level, a positive integer
     * @return the bonus type of this level
     */
    public BonusType getLevelBonusType(int level){
        return this.bonus.getBonusType();
    }

    /**
     * Place the bonus associated with current level on board at its intended position
     */
    public void setBonusOnBoard(){

    }

}
