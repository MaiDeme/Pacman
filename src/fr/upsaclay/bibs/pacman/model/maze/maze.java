package fr.upsaclay.bibs.pacman.model.maze;

import fr.upsaclay.bibs.pacman.model.Direction;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NavigableSet;
import java.util.Scanner;

import static java.lang.Math.floor;

public class maze implements Maze {
    private int width;
    private int height;

    private static Tile[][] plateau;

    public maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.plateau = new Tile[height][width];

        for (int i =0; i< width; i++){
            for (int j = 0; j < width; j++){
                plateau[i][j] = Tile.EE;
            }
        }

    }

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
                col = col -1;
                break;

        }
        switch (dir) {
            case LEFT:
                line = line -1;
                break;

        }
        switch (dir) {

            case RIGHT :
                line = line +1;
                break;

        }
        switch (dir) {

            case DOWN:
                col = col +1;
                break;

        }

        TilePosition pos = new TilePosition(line, col);
        return pos;
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

        plateau[line][col] = tile;

    }

    @Override
    public void setTile(TilePosition pos, Tile tile) {
        setTile(pos.getLine(), pos.getCol(), tile);

    }

    @Override
    public TilePosition getTilePosition(int x, int y) {

        return new TilePosition((int) floor(y/8), (int) floor(x/8));
    }



    public int getNumberOfDots(){
        return 0;

    }

}