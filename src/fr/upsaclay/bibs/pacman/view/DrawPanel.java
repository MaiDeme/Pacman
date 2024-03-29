package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A customized implementation of JPanel to draw the ongoing simulation
 *
 * @author Viviane Pons
 *
 */
public class DrawPanel extends JPanel {

    private Board board;

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

    public void paintPacMan(Graphics g, int i, int j,boolean open) {
        int size = BoardView.PIXELS_PER_CELLS;
        i = i * size;
        j = j * size;
        String filename;
        if (open){
            filename= "resources/pacman.txt";
        }else{
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Maze maze = board.getMaze();
        TilePosition Pacpos = board.getPacMan().getCurrentTile();

        if (board != null) {
            for (int i = 0; i < maze.getPixelHeight(); i+=8) {
                for (int j = 0; j < maze.getPixelWidth(); j+=8) {
                    TilePosition pos = maze.getTilePosition(j, i);
                    Tile tile = maze.getTile(pos);
                    if (tile.isWall()) {
                        paintWalls(g, j, i, tile);
                    } else if (pos.equals(Pacpos)) {
                        paintPacMan(g, j, i,true);
                    }
                    if (tile.hasDot()) {
                        paintDot(g, j, i, tile);
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
