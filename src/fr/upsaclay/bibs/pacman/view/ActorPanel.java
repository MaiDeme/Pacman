package fr.upsaclay.bibs.pacman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActorPanel extends JPanel {

    private Board board;
    private int frameCounter;

    private int openMouth = 1;
    private Map<String,String[][]> DeathFrames;

    private Map<String,String[][]> PacmanSprites;
    //on a 9 sprites pour les animations normal de pacman
    // elles seront stockées dans PacmanSprites dans l'ordre
    // closed mouth,bigmouth open up,left,down,right puis pacman up,left,down,right

    private Map<String,String[][]> GhostSprites;

    public ActorPanel(){
        super();
        setBackground(new Color(0,0,0,0));

        //Loading Sprites
        SpriteLoader loader = new SpriteLoader();

        //Pacman Death sprite
        DeathFrames = new HashMap<>();
        for (int i=1 ; i<13 ; i++) {
            String filename = "resources/dying/sprite" + i;
            String[][] frame = loader.loadSprites(filename);
            DeathFrames.put(String.valueOf(i),frame);
        }
        
        //Pacman sprite
        PacmanSprites = new HashMap<>();
        PacmanSprites.put("pacman_closedmouth",loader.loadSprites("resources/pacman_closedmouth.txt"));
        PacmanSprites.put("pacman_bigmouth_UP",loader.loadSprites("resources/pacmanbigmouthopen_UP.txt"));
        PacmanSprites.put("pacman_bigmouth_LEFT",loader.loadSprites("resources/pacmanbigmouthopen_LEFT.txt"));
        PacmanSprites.put("pacman_bigmouth_DOWN",loader.loadSprites("resources/pacmanbigmouthopen_DOWN.txt"));
        PacmanSprites.put("pacman_bigmouth_RIGHT",loader.loadSprites("resources/pacmanbigmouthopen_RIGHT.txt"));
        PacmanSprites.put("pacman_UP",loader.loadSprites("resources/pacman_UP.txt"));
        PacmanSprites.put("pacman_LEFT",loader.loadSprites("resources/pacman_LEFT.txt"));
        PacmanSprites.put("pacman_DOWN",loader.loadSprites("resources/pacman_DOWN.txt"));
        PacmanSprites.put("pacman_RIGHT",loader.loadSprites("resources/pacman_RIGHT.txt"));

        

        //Ghost sprite
        GhostSprites = new HashMap<>();
        GhostSprites.put("ghost_UP",loader.loadSprites("resources/ghosts/UP.txt"));
        GhostSprites.put("ghost_LEFT",loader.loadSprites("resources/ghosts/LEFT.txt"));
        GhostSprites.put("ghost_DOWN",loader.loadSprites("resources/ghosts/DOWN.txt"));
        GhostSprites.put("ghost_RIGHT",loader.loadSprites("resources/ghosts/RIGHT.txt"));



        //timer pour jouer les frames
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //future delaying for death animation
                //TODO
                repaint();
            }
        });

    }
    

 

    public void paintDying(Graphics g ,  int i, int j ){
        //TODO
        int size = BoardView.PIXELS_PER_CELLS;
        i= i *size;
        j= j*size;
        String filename;
        Actor Pacman = this.board.getPacMan();
    
    }

    public void paintGhost(Graphics g, int i, int j,Ghost ghost) {
        int size = BoardView.PIXELS_PER_CELLS;
        Direction dir=ghost.getDirection();

        
        String[][] sprite = GhostSprites.get("ghost_"+dir);
        

        
        // Draw the sprite
        for (int y = 0; y < sprite.length; y++) {
            for (int x = 0; x < sprite[y].length; x++) {
                if (sprite[y][x].equals("1")) {
                    switch (ghost.getGhostType()) {
                        case BLINKY:
                            g.setColor(Color.RED);break;
                        case PINKY:
                            g.setColor(Color.PINK);break;
                        case INKY:
                            g.setColor(Color.CYAN);break;
                        case CLYDE:
                            g.setColor(Color.ORANGE);break;                                
                    }
                    g.fillRect( (x+i)*size ,  (y+j)*size,size,size);
                } else if (sprite[y][x].equals("2")) {
                    g.setColor(Color.WHITE);
                    g.fillRect( (x+i)*size ,  (y+j)*size,size,size);
                }else if (sprite[y][x].equals("3")){
                    g.setColor(Color.BLACK);
                    g.fillRect( (x+i)*size ,  (y+j)*size,size,size);
                }
            }
        }
    }


    public void paintPacMan(Graphics g, int i, int j) {
        int size = BoardView.PIXELS_PER_CELLS;
        String spriteName;
        Actor Pacman = this.board.getPacMan();
        switch (openMouth) {
            case 1:
                spriteName = "pacman_closedmouth";
                break;
            case 2:
                spriteName = "pacman_" + Pacman.getDirection();
                break;
            case 3:
                spriteName = "pacman_bigmouth_" + Pacman.getDirection();
                break;
            default:
                spriteName = "pacman_closedmouth";
        }
        String[][] sprite = PacmanSprites.get(spriteName);
       
        for (int y=0 ; y<sprite.length ; y++){
            for (int x=0 ; x<sprite[y].length ; x++){
                if (sprite[y][x].equals("1")){
                    g.setColor(Color.YELLOW);
                    g.fillRect((x+i)*size,(y+j)*size,size,size);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Maze maze = board.getMaze();
        TilePosition Pacpos = board.getPacMan().getCurrentTile();
        List<Ghost> ghosts = board.getGhosts();
  

        frameCounter++;
        if (frameCounter % 6 == 0) {
            openMouth++;
            if (openMouth > 3) {
                openMouth = 1;
                frameCounter = 0;
            }

        }
        
        for (int i = 0; i < maze.getPixelHeight(); i += 8) {
            for (int j = 0; j < maze.getPixelWidth(); j += 8) {
                TilePosition pos = maze.getTilePosition(j, i);
                for (Ghost ghost : ghosts) {
                    if (pos.equals(ghost.getCurrentTile())) {
                        paintGhost(g, j, i, ghost);
                    }
                    if (pos.equals(Pacpos)) {
                        paintPacMan(g, j, i);
                    }
                }
            }
        }
        

    }


    public void setBoard(Board board){
        this.board=board;
    }
}

    


