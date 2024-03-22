package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import javax.swing.*;
import java.awt.*;



/**
 * A customized implementation of JPanel to draw the ongoing simulation
 *
 * @author Viviane Pons
 *
 */
public class DrawBoard extends JPanel {
    
    private Board board;
    

    public DrawBoard(Board board) {
        super();
        setPreferredSize(new Dimension(board.getMaze().getWidth() * BoardView.PIXELS_PER_CELLS, board.getMaze().getHeight() * BoardView.PIXELS_PER_CELLS));

    }

    public void initialize() {
        setBackground(Color.BLACK);
    }

    public void paintWalls(Graphics g, int i, int j) {
        int size = BoardView.PIXELS_PER_CELLS;
        g.setColor(Color.BLUE);
        g.fillRect(j * size, i * size, size, size);
    }

    public void paintPacMan(Graphics g, int i, int j) {
        int size = BoardView.PIXELS_PER_CELLS;
        g.setColor(Color.YELLOW);
        g.fillRect(j * size, i * size, size, size);
    }
    

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

         Maze maze = board.getMaze();
         TilePosition Pacpos = board.getPacMan().getCurrentTile();


        if(board != null) {
            for(int i = 0; i < maze.getPixelHeight(); i++) {
                for(int j = 0; j <maze.getPixelWidth(); j++) {
                    TilePosition pos = maze.getTilePosition(j, i);
                    Tile tile = maze.getTile(pos);
                    if (tile.isWall()){
                        paintWalls(g, i, j);
                    } else if (pos == Pacpos) {
                        paintPacMan(g,i,j);
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

