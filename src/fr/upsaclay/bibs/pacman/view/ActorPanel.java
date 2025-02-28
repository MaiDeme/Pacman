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
import fr.upsaclay.bibs.pacman.model.actors.GhostState;
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
    private Map<String,String[][]> BonusSprites;

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
        GhostSprites.put("ghost_frightened",loader.loadSprites("resources/ghosts/frightened.txt"));
        GhostSprites.put("ghost_dead",loader.loadSprites("resources/ghosts/dead.txt"));

        //Bonus sprite
        BonusSprites = new HashMap<>();
        BonusSprites.put("apple",loader.loadSprites("resources/bonus/APPLE.txt"));
        BonusSprites.put("bell",loader.loadSprites("resources/bonus/BELL.txt"));
        BonusSprites.put("galaxian",loader.loadSprites("resources/bonus/GALAXIAN.txt"));
        BonusSprites.put("grapes",loader.loadSprites("resources/bonus/GRAPES.txt"));
        BonusSprites.put("key",loader.loadSprites("resources/bonus/KEY.txt"));
        BonusSprites.put("peach",loader.loadSprites("resources/bonus/PEACH.txt"));
        BonusSprites.put("cherry",loader.loadSprites("resources/bonus/CHERRY.txt"));
        BonusSprites.put("strawberry",loader.loadSprites("resources/bonus/STRAWBERRY.txt"));

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


        if (ghost.getGhostState().equals(GhostState.FRIGHTENED)) {
            String[][] sprite = GhostSprites.get( "ghost_"+"frightened");
            for (int y = 0; y < sprite.length; y++) {
                for (int x = 0; x < sprite[y].length; x++) {
                    if (sprite[y][x].equals("1")) {
                        g.setColor(Color.BLUE);
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

        } else if (ghost.getGhostState().equals(GhostState.FRIGHTENED_END)) {
            String[][] sprite = GhostSprites.get("ghost_" + "frightened");
            if (ghost.getFrightenedCounter() % 10 > 0 && ghost.getFrightenedCounter() % 10 < 5) {
                for (int y = 0; y < sprite.length; y++) {
                    for (int x = 0; x < sprite[y].length; x++) {
                        if (sprite[y][x].equals("1")) {
                            g.setColor(Color.BLUE);
                            g.fillRect((x + i) * size, (y + j) * size, size, size);
                        } else if (sprite[y][x].equals("2")) {
                            g.setColor(Color.WHITE);
                            g.fillRect((x + i) * size, (y + j) * size, size, size);
                        } else if (sprite[y][x].equals("3")) {
                            g.setColor(Color.BLACK);
                            g.fillRect((x + i) * size, (y + j) * size, size, size);
                        }
                    }
                }
            }
        }else if (ghost.getGhostState().equals(GhostState.DEAD)){
            String[][] sprite = GhostSprites.get( "ghost_"+"dead");
            for (int y = 0; y < sprite.length; y++) {
                for (int x = 0; x < sprite[y].length; x++) {
                    if (sprite[y][x].equals("2")) {
                        g.setColor(Color.WHITE);
                        g.fillRect((x + i) * size, (y + j) * size, size, size);
                    } else if (sprite[y][x].equals("3")) {
                        g.setColor(Color.BLACK);
                        g.fillRect((x + i) * size, (y + j) * size, size, size);
                    }

                }
            }

        }else {
            String[][] sprite = GhostSprites.get("ghost_"+dir);
            // Draw the sprite
            for (int y = 0; y < sprite.length; y++) {
                for (int x = 0; x < sprite[y].length; x++) {
                    if (sprite[y][x].equals("1")) {
                        switch (ghost.getGhostType()) {
                            case BLINKY:
                                g.setColor(Color.RED);
                                break;
                            case PINKY:
                                g.setColor(Color.PINK);
                                break;
                            case INKY:
                                g.setColor(Color.CYAN);
                                break;
                            case CLYDE:
                                g.setColor(Color.ORANGE);
                                break;
                        }
                        g.fillRect((x + i) * size, (y + j) * size, size, size);
                    } else if (sprite[y][x].equals("2")) {
                        g.setColor(Color.WHITE);
                        g.fillRect((x + i) * size, (y + j) * size, size, size);
                    } else if (sprite[y][x].equals("3")) {
                        g.setColor(Color.BLACK);
                        g.fillRect((x + i) * size, (y + j) * size, size, size);
                    }
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
            case 4:
                spriteName = "pacman_" + Pacman.getDirection();
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
  
        if (board.getCurrentBonus() != null && board.getCurrentBonus().isActive()) {
            paintBonus(g, (int) board.getCurrentBonus().getX(), (int) board.getCurrentBonus().getY(), board.getCurrentBonus().getBonusType().name().toLowerCase());
        }
        
        frameCounter++;
        if (frameCounter % 8 == 0) {
            openMouth++;
            if (openMouth > 4) {
                openMouth = 1;
                frameCounter = 0;
            }

        }
        
        if (board.getCurrentBonus() != null && board.getCurrentBonus().isActive()) {
            paintBonus(g, (int) board.getCurrentBonus().getX(), (int) board.getCurrentBonus().getY(), board.getCurrentBonus().getBonusType().name().toLowerCase());
        }

        for (int i = 0; i < maze.getPixelHeight(); i += 8) {
            for (int j = 0; j < maze.getPixelWidth(); j += 8) {
                TilePosition pos = maze.getTilePosition(j, i);
                for (Ghost ghost : ghosts) {
                    if (pos.equals(ghost.getCurrentTile())) {
                        paintGhost(g, j-5, i-5, ghost);
                    }
                    if (pos.equals(Pacpos)) {
                        paintPacMan(g, j-3, i-5);
                    }
                }
            }
        }
        

    }

    public void paintBonus(Graphics g, int i, int j, String bonusType) {
        int size = BoardView.PIXELS_PER_CELLS;
        String[][] sprite = BonusSprites.get(bonusType);
            for (int y = 0; y < sprite.length; y++) {
            for (int x = 0; x < sprite[y].length; x++) {
                Color color;
                if (sprite[y][x].equals("1")) {
                    color = new Color(165, 42, 42); // Marron pour la tige
                } else if (sprite[y][x].equals("2")) {
                    color = Color.RED; // Rouge pour la pomme
                } else if (sprite[y][x].equals("3")) {
                    color = Color.PINK; // Rouge clair pour la lueur
                } else {
                    color = new Color(0, 0, 0, 0); // Transparent
                }
                if (!color.equals(new Color(0, 0, 0, 0))) { // Si la couleur n'est pas transparente
                    g.setColor(color);
                    g.fillRect((x + i) * size, (y + j) * size, size, size);
                }
            }
        }
    }

    public void setBoard(Board board){
        this.board=board;
    }
}

    


