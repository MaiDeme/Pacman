package fr.upsaclay.bibs.pacman.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends JPanel {
    private Color currentColor = Color.RED;

    public GameOver() {
        super();
        this.setBackground(new Color(0, 0, 0, 0));

        Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentColor.equals(Color.RED)) {
                    currentColor = new Color(0, 0, 0, 0);
                } else {
                    currentColor = Color.RED;
                }

                // Redraw the panel
                repaint();
            }
        });

        // Start the timer
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(getFont().deriveFont(Font.BOLD, 15));
        g.setColor(currentColor);

        String[] lines = {
                "Game Over",
                "Space : Start a new Game",
                "Escape : Back to Title Screen",
                "Delete : Quit"
        };

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int lineHeight = metrics.getHeight();

        int y = (this.getHeight() - (lineHeight * lines.length)) / 2;

        // Draw each line separately
        for (String line : lines) {
            int x = (this.getWidth() - metrics.stringWidth(line)) / 2;
            g.drawString(line, x, y);
            y += lineHeight + 20; // Move to the next line
        }

    }
}
