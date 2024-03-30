package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.actors.Pacman;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.model.Direction;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Font;
import java.awt.FontFormatException;


/**
 * A customized implementation of JPanel to draw the ongoing simulation
 *
 * @author Viviane Pons
 *
 */
public class DrawPanel extends JPanel {

    private Board board;
    private int score;
    private int frameCounter;
    private int openMouth = 1;


    public DrawPanel(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
    }

    public void initialize() {
        setBackground(Color.BLACK);
    }

    public void paintWalls(Graphics g, int i, int j, Tile texture) {
        int size = BoardView.PIXELS_PER_CELLS;
        i = i * size;
        j = j * size;
        try (Scanner scanner = new Scanner(new File("resources/tiles/" + texture + ".txt"))) {
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] chars = line.split(" ");
                for (int x = 0; x < chars.length; x++) {
                    if (chars[x].equals("1")) {
                        g.setColor(Color.BLUE);
                        g.fillRect(x * size + i, y * size + j, size, size);
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void paintPacMan(Graphics g, int i, int j) {
        int size = BoardView.PIXELS_PER_CELLS;
        i = i * size;
        j = j * size;
        String filename;
        Actor Pacman = this.board.getPacMan();
        int x_pac = Pacman.getX();
        int y_pac = Pacman.getY();




        switch (openMouth) {
            case 1:
                filename = "resources/pacman_closedmouth.txt";

                break;
            case 2:

                if(Pacman.getDirection() == Direction.LEFT){
                    filename= "resources/pacman_left.txt";
                }else if (Pacman.getDirection() == Direction.RIGHT){
                    filename= "resources/pacman_right.txt";
                } else if (Pacman.getDirection() == Direction.UP){
                    filename= "resources/pacman_up.txt";
                }else{
                    filename= "resources/pacman_down.txt";
                }

                break;
            case 3:
                if(Pacman.getDirection() == Direction.LEFT){
                    filename= "resources/pacmanbigmouthopen_left.txt";
                }else if(Pacman.getDirection() == Direction.RIGHT){
                    filename= "resources/pacmanbigmouthopen_right.txt";
                } else if (Pacman.getDirection() == Direction.UP) {
                    filename= "resources/pacmanbigmouthopen_up.txt";
                }else{
                    filename= "resources/pacmanbigmouthopen_down.txt";
                }
                break;
            default:
                filename = "resources/pacman_closedmouth.txt";
        }
        try (Scanner scanner = new Scanner(new File(filename))) {
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] chars = line.split(" ");
                for (int x = 0; x < chars.length; x++) {
                    if (chars[x].equals("1")) {
                        g.setColor(Color.YELLOW);
                        g.fillRect(x * size + i, y * size + j, size, size);
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    public void paintBlinky(Graphics g, int i, int j) {
        int size = BoardView.PIXELS_PER_CELLS;
        i = i * size;
        j = j * size;
        String filename = "resources/ghosts/LEFT.txt";

        Direction dir = board.getGhost(GhostType.BLINKY).getDirection();
        if( dir == Direction.LEFT) {
            filename = "resources/ghosts/LEFT.txt";
        }else if(dir == Direction.RIGHT){
            filename = "resources/ghosts/RIGHT.txt";
        } else if (dir == Direction.UP){
            filename = "resources/ghosts/UP.txt";
        }else if (dir == Direction.DOWN){
            filename = "resources/ghosts/DOWN.txt";
        }


        try (Scanner scanner = new Scanner(new File(filename))) {
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] chars = line.split(" ");
                for (int x = 0; x < chars.length; x++) {
                    if (chars[x].equals("1")) {
                        g.setColor(Color.RED);
                        g.fillRect(x * size + i, y * size + j, size, size);
                    } else if (chars[x].equals("2")) {
                        g.setColor(Color.WHITE);
                        g.fillRect(x * size + i, y * size + j, size, size);
                    }if (chars[x].equals("3")) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x * size + i, y * size + j, size, size);
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void paintDot(Graphics g, int i, int j, Tile texture) {
        int size = BoardView.PIXELS_PER_CELLS;
        i = i * size;
        j = j * size;
        try (Scanner scanner = new Scanner(new File("resources/tiles/" + texture + ".txt"))) {
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] chars = line.split(" ");
                for (int x = 0; x < chars.length; x++) {
                    if (chars[x].equals("1")) {
                        g.setColor(new Color(236,212,83));
                        g.fillRect(x * size + i, y * size + j, size, size);
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void paintScore(Graphics g) {
        g.setColor(Color.WHITE);
        float newSize = 15;
        g.setFont(getFont().deriveFont(newSize));
        g.drawString(Integer.toString(score), 10, 40);
    }

    public void paintHighScore(Graphics g) {
        g.setColor(Color.WHITE);
        float newSize = 15;
        g.setFont(getFont().deriveFont(newSize));
        g.drawString("HIGH SCORE " + Integer.toString(getBoard().getMaze().getHigh_score()), 10, 20);
    }


    public void paintGhost(Graphics g, int i, int j,Ghost ghost) {
        int size = BoardView.PIXELS_PER_CELLS;
        i = i * size;
        j = j * size;
        Direction dir=ghost.getDirection();
        String filename= "resources/ghosts/"+dir+".txt";
        try (Scanner scanner = new Scanner(new File(filename))) {
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] chars = line.split(" ");
                for (int x = 0; x < chars.length; x++) {
                    if (chars[x].equals("1")) {
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
                        g.fillRect(x * size + i, y * size + j, size, size);
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateScore(int newScore) {
        this.score = newScore;
        
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Maze maze = board.getMaze();
        TilePosition Pacpos = board.getPacMan().getCurrentTile();
        TilePosition Blinkypos = board.getGhost(GhostType.BLINKY).getCurrentTile();
        updateScore(board.getScore());
        paintScore(g);
        paintHighScore(g);
        frameCounter++;
        if (frameCounter%6 == 0) {
            openMouth++;
            if (openMouth >3) {
                openMouth = 1;
                frameCounter = 0;
            }
           
        }
        if (board != null) {
            for (int i = 0; i < maze.getPixelHeight(); i+=8) {
                for (int j = 0; j < maze.getPixelWidth(); j+=8) {
                    TilePosition pos = maze.getTilePosition(j, i);
                    Tile tile = maze.getTile(pos);
                    if (tile.isWall()) {
                        paintWalls(g, j, i, tile);
                    }else if (tile.hasDot()) {
                        paintDot(g, j, i, tile);
                    } else if (pos.equals(Pacpos)) {
                        paintPacMan(g, j, i);

                    } else if (pos.equals(this.board.getGhost(GhostType.BLINKY).getCurrentTile())){
                        paintBlinky(g, i, j);
                    }
                }
            }
        }
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    
}
