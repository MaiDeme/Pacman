package fr.upsaclay.bibs.pacman.view;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.upsaclay.bibs.pacman.model.board.Board;

import java.awt.Font;
import java.awt.FontMetrics;
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
        g.setFont(getFont().deriveFont(Font.BOLD, 18));
        g.setColor(java.awt.Color.WHITE);
        g.drawImage(background, 0, 0, this);

        String message = "Press SPACE to start";
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (this.getWidth() - metrics.stringWidth(message)) / 2;
        int y = 100;  // Adjust this value to move the text up or down
    
        g.drawString(message, x, y);

        g.drawString(message, x, y);
    }


        public Board getBoard() {
        return board;
    }


    public void setBoard(Board board) {
        this.board = board;
    }

}
