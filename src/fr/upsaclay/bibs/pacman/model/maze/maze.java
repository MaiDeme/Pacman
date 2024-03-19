package fr.upsaclay.bibs.pacman.model.maze;

import fr.upsaclay.bibs.pacman.model.Direction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class maze implements Maze {
    private int width;
    private int height;

    private static Tile[][] plateau;


    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getPixelWidth() {
        return this.width * TILE_WIDTH;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getPixelHeight() {
        return this.height * TILE_WIDTH;
    }

    @Override
    public Tile getTile(int line, int col) {
        return plateau[line][col];
    }

    @Override
    public Tile getTile(TilePosition pos) {
        return getTile(pos.getLine(), pos.getCol());
    }

    @Override
    public TilePosition getNeighbourTilePosition(int line, int col, Direction dir) {
        switch (dir) {
            case UP :
                return new TilePosition(line, col-1);
                break;
        }
        switch (dir) {
            case LEFT:
                return new TilePosition(line-1, col);
                break;
        }
        switch (dir) {

            case RIGHT ->  :
            return new TilePosition(line +1, col);
                break;
        }
        switch (dir) {

            case DOWN:
                return new TilePosition(line, col+1);
                break;
        }
    }

    @Override
    public TilePosition getNeighbourTilePosition(TilePosition pos, Direction dir) {
        return getNeighbourTilePosition(pos.getLine(), pos.getCol(), dir);
    }

    @Override
    public Tile getNeighbourTile(int line, int col, Direction dir) {
        TilePosition position = getNeighbourTilePosition(line, col, dir);
        return plateau[position.getLine()][position.getCol()];
    }

    @Override
    public Tile getNeighbourTile(TilePosition pos, Direction dir) {
        return getNeighbourTile(pos.getLine(), pos.getCol(), dir);
    }

    @Override
    public void setTile(int line, int col, Tile tile) {

    }

    @Override
    public void setTile(TilePosition pos, Tile tile) {

    }

    @Override
    public TilePosition getTilePosition(int x, int y) {
        return null;
    }



    static Maze loadFromFile(String fileName) throws FileNotFoundException {

        File f = new File(fileName);
        Scanner scan = new Scanner(f);
        String line = scan.nextLine();

        int width = Integer.parseInt(line.split("/t")[1]);
        int height = Integer.parseInt(line.split("/t")[2]);

        Maze lab = emptyMaze(width, height);

        for (int i = 0; i < height; i++) {

            line = scan.nextLine();
            String ligne[] = line.split("\t");

            for (int j = 0; j < width; j++) {

                //plateau[i][j] = Tile(ligne[j]);

            }


        }
        return lab;
    }

    public int getNumberOfDots(){
        return 0;

    }

}