package fr.upsaclay.bibs.pacman.model.maze;

import fr.upsaclay.bibs.pacman.model.Direction;

import java.io.File;
import java.io.FileNotFoundException;

public class Labyrinthe implements Maze {
    private  int width ;
    private  int height ;

    private Tile [][] plateau;

    public Labyrinthe(int width, int height) {
        this.height = height;
        this.width = width;
        this.plateau = new Tile[height][width];
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
        return null;
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
        getNeighbourTile(pos.getLine(), pos.getCol(), dir);
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

    static Maze emptyMaze(int width, int height) {
        new Labyrinthe(width, height);

    }


    static Maze loadFromFile(String fileName) throws FileNotFoundException {

        java.util.Scanner lecteur ;
        File f  = new File(fileName);
        lecteur = new java.util.Scanner(f);

        int widht;
        int height;





	/* ou bien
	   java.io.InputStream entree =
	   LireFichierTexteBis.class.getResourceAsStream((arg[0]));
	   lecteur = new java.util.Scanner(entree);
	*/

        int somme = 0;
        while (lecteur.hasNextInt()) somme += lecteur.nextInt();
        System.out.println(somme);
    }

        String string;
        string = getName() ;

        for (int i = 0; i < liste.size(); i++ ){
            Amazone amazone = (Amazone) liste.get(i);
            string = string + "\n" +amazone.getType() + "\n" + amazone.getName() + "\n" + amazone.getExperience() + "\n" + amazone.getLifePoints() ;
        }


        Tribe firstTribe = Tribe.loadTribe("resources/goth.txt");
        System.out.println("First Tribe : ");
        showTribe(firstTribe);
        Tribe secondTribe = Tribe.loadTribe("resources/antiopians.txt");
        System.out.println("Second Tribe : ");
        showTribe(secondTribe);

        for(int i = 0; i < 5; i++) {
            action(firstTribe, secondTribe);
            action(secondTribe, firstTribe);
            showTribe(firstTribe);
            showTribe(secondTribe);


    }

    int getNumberOfDots();{

        return i;
    }
}
