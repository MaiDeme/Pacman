package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;
import fr.upsaclay.bibs.pacman.PacManException;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyStart implements KeyListener{

    private final Controller controller;

    public KeyStart(Controller controller) {
        this.controller = controller;
    }
   
    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                try {
                    controller.receiveAction(GameAction.START);
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
