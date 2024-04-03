package fr.upsaclay.bibs.pacman.view;

import java.awt.Graphics;
import java.awt.Image;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DeathAnimation extends JPanel {
    private List<String[]> deathFrames;
    private int currentFrame=0;
    private int x;
    private int y;

    public DeathAnimation() {
        super();


        //Loading the death frames
        SpriteLoader loader = new SpriteLoader();
        deathFrames = new ArrayList<>();

        for (int i=0 ; i<12 ; i++) {
            String filename = "resources/dying/sprite" + i + ".txt";
            List<String[]> frame = loader.loadSprites(filename);
            deathFrames.addAll(frame);


        }

        //timer pour jouer les frames
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //next frame
                currentFrame = (currentFrame + 1) % deathFrames.size();

                repaint();
            }
        });

        timer.start();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the current frame
        String[] frame = deathFrames.get(currentFrame);
        for (int i=0 ; i<frame.length ; i++) {
            if (frame[i].equals("1")) {
                
                g.fillRect(i*2, 0, 2, 2);
            }
        }
    }

    public void setcoordonate(int x, int y){
        this.x = x;
        this.y = y;

    }

}
