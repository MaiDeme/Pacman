package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyGameOver implements KeyListener {

    private final Controller controller;

    public KeyGameOver(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                try {
                    controller.receiveAction(GameAction.NEW_GAME);
                } catch (PacManException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                try {
                    controller.receiveAction(GameAction.TITLE_SCREEN);
                } catch (PacManException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_DELETE:
                try {
                    controller.receiveAction(GameAction.QUIT);
                } catch (PacManException e1) {
                    e1.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
