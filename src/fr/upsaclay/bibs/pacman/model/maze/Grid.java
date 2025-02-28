package fr.upsaclay.bibs.pacman.model.maze;

import fr.upsaclay.bibs.pacman.model.Direction;

import static java.lang.Math.floor;

public class Grid implements Maze {
    private int width;
    private int height;

    private int High_score;


    private int number_of_dots;

    static Tile[][] plateau;

    public Grid(int height, int width) {
        this.High_score = 0;
        this.number_of_dots = 0;
        this.height = height;
        this.width = width;
        this.plateau = new Tile[height][width];

        for (int i =0; i< width; i++){
            for (int j = 0; j < width; j++){
                plateau[i][j] = Tile.EE;
            }
        }

    }


    public int getHigh_score() {
        return this.High_score;
    }

    public void setHigh_score(int score) {
        if (this.High_score < score)
            this.High_score = score;
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
                line = line -1;
                break;

            case LEFT:
                col = col -1;
                break;

            case RIGHT :
                col = col +1;
                break;

            case DOWN:
                line = line +1;
                break;

        }

        if (col< 0){
            col = this.getWidth() -1 ;
        }else if (col > this.getWidth()-1){
            col = 0;
        }

        if (line< 0){
            line = this.getHeight() -1 ;
        }else if (line > this.getHeight() -1){
            line = 0;
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

        if (plateau[line][col] != null){

            Tile prev_tile = plateau[line][col];

            if (prev_tile.hasDot() && !tile.hasDot()){
                this.number_of_dots -= 1;
            }else if (!prev_tile.hasDot() && tile.hasDot()){
                this.number_of_dots += 1;
            }

        }else{
            if (tile.hasDot()){
                this.number_of_dots += 1;
            }
        }

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



    public int getNumberOfDots() {
        return this.number_of_dots;
    }

    public boolean IsIntersection(TilePosition tile, Direction dir){
        Tile Up = getNeighbourTile(tile, Direction.UP);
        Tile Down = getNeighbourTile(tile, Direction.DOWN);
        Tile Left = getNeighbourTile(tile, Direction.LEFT);
        Tile Right = getNeighbourTile(tile, Direction.RIGHT);

        switch (dir){
            case DOWN:
            case UP:
                if (!Left.isWall() || !Right.isWall()){
                    return true;
                }else{
                    return false;
                }
            default:
                if (!Down.isWall() || !Up.isWall()){
                    return true;
                }else{
                    return false;
                }
        }
    }


}