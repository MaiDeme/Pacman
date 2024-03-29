package fr.upsaclay.bibs.pacman.view;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.upsaclay.bibs.pacman.model.board.Board;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.Image;



public class TitleScreen extends JPanel {
    Image background;
    private Board board;


    public TitleScreen(int width,int height) throws Exception{
        Image originalImage = ImageIO.read(new File("resources/title_screen.jpg"));
        background = originalImage.getScaledInstance(width,height, Image.SCALE_SMOOTH);

    }
  

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }


        public Board getBoard() {
        return board;
    }


    public void setBoard(Board board) {
        this.board = board;
    }

}
