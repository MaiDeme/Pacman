package fr.upsaclay.bibs.pacman.control;

/**
 * List of Game Actions that the view can send to the controller
 */
public enum GameAction {
    /** Tell PacMan to go up **/
    UP,
    /** tell Pacman to go left **/
    LEFT,
    /** tell PacMan to go down **/
    DOWN,
    /** tell PacMan to go right **/
    RIGHT,
    /** Ask to pause the game **/
    PAUSE,
    /** ask to resume the game (after pause) **/
    RESUME,
    /** Ask to start the game **/
    START,
    /** Ask to move to next game frame **/
    NEXT_FRAME,

    /** Step 2 actions **/
    NEXT_LEVEL,

    NEW_GAME,

    /** Step 3 actions **/

    NEW_LIFE;

}
