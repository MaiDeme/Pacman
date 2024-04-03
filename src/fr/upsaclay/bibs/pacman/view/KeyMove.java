package fr.upsaclay.bibs.pacman.view;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyMove implements KeyListener{
    private final Controller controller;

    public KeyMove(Controller controller) {
        this.controller = controller;
    }
   
    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                try {
                    controller.receiveAction(GameAction.UP);
                } catch (PacManException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_DOWN:
                try {
                    controller.receiveAction(GameAction.DOWN);
                } catch (PacManException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                break;
            case KeyEvent.VK_LEFT:
                try {
                    controller.receiveAction(GameAction.LEFT);
                } catch (PacManException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                    
                    break;
            case KeyEvent.VK_RIGHT:
                try {
                    controller.receiveAction(GameAction.RIGHT);
                } catch (PacManException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                try {
                    controller.receiveAction(GameAction.PAUSE);
                } catch (PacManException e1) {
                    e1.printStackTrace();
                }
                break;
        
            default:
                break;
        }
    }


    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

}
