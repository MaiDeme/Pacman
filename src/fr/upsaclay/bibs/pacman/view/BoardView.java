package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.VisualController;
import fr.upsaclay.bibs.pacman.control.GameAction;
import fr.upsaclay.bibs.pacman.model.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.border.Border;
import java.awt.Font;
import java.awt.FontFormatException;

public class BoardView extends JFrame implements PacManView {

    private Controller controller;

    public static final int PIXELS_PER_CELLS = 2;

    DrawPanel drawPanel;
    Timer timer;

    JPanel initialPanel;
    JPanel playPanel;

    JPanel GOPanel;
    JPanel pausePanel;
    Font arcadeFont;

    KeyStart startkey;

    public BoardView(String name, int width, int height) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Create the drawPanel (where we draw the board)
        drawPanel = new DrawPanel(width, height);

        // Create the timer
        this.timer = new Timer(1, null);

    }

    @Override
    public void setBoard(Board board) {
        drawPanel.setBoard(board);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {

        // Loading fonts

        try {
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Emulogic-zrEw.ttf"));
            setFont(arcadeFont);

            // Now you can use arcadeFont with any component that allows setting a font
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // General initialization
        drawPanel.initialize();
        add(drawPanel);

        // Ecran de titre
        try {
            initialPanel = new TitleScreen(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height);
            initialPanel.setFont(arcadeFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initialPanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));

        // Start key Listener
        startkey = new KeyStart(controller);
        initialPanel.addKeyListener(startkey);

        drawPanel.add(initialPanel);
        drawPanel.setFont(arcadeFont);

        // Timer initialization
        timer.addActionListener(new ButtonListener(controller, GameAction.NEXT_FRAME));

        // The play panel (when the game is running)
        playPanel = new JPanel();
        playPanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));

        playPanel.setBackground(new Color(0, 0, 0, 0)); // panel transparent mais les boutons sont visibles
        drawPanel.add(playPanel);

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// The pause panel (when the game is on pause)
        pausePanel = new JPanel(new GridBagLayout());
        pausePanel.setPreferredSize(new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));

        GridBagConstraints gbc = new GridBagConstraints();

        pausePanel.setBackground(new Color(128, 128, 128, 100)); // semi-transparent because the game is paused
        JLabel pauseLabel = new JLabel("Game Paused");
        pauseLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        pausePanel.add(pauseLabel, gbc);

        JButton ResumeButton = new JButton("Resume");
        ResumeButton.addActionListener(new ButtonListener(controller, GameAction.RESUME));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0); // 10 pixels of padding above and below
        pausePanel.add(ResumeButton, gbc);

        JButton RestartButton = new JButton("Start New Game");
        RestartButton.addActionListener(new ButtonListener(controller, GameAction.NEW_GAME));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0); // 10 pixels of padding above and below
        pausePanel.add(RestartButton, gbc);

        JButton TitleButton = new JButton("Back to title screen");
        TitleButton.addActionListener(new ButtonListener(controller, GameAction.TITLE_SCREEN));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 0, 10, 0); // 10 pixels of padding above and below
        pausePanel.add(TitleButton, gbc);

        JButton QuitButton = new JButton("Quit");
        QuitButton.addActionListener(new ButtonListener(controller, GameAction.QUIT));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 10, 0); // 10 pixels of padding above and below
        pausePanel.add(QuitButton, gbc);

        add(drawPanel, BorderLayout.CENTER);
        drawPanel.add(pausePanel);

        KeyMove keylist = new KeyMove(controller);
        drawPanel.addKeyListener(keylist);

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// Panel de Game Over

        GOPanel = new JPanel();
        GOPanel.setPreferredSize(new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));
        GOPanel.setBackground(new Color(128, 128, 128, 100)); // semi-transparent because the game is paused

        JButton New_GameButton = new JButton("Restart");
        New_GameButton.addActionListener(new ButtonListener(controller, GameAction.START));
        GOPanel.add(New_GameButton);

        add(GOPanel); // Add GOPanel directly to the frame or container, not to drawPanel



        pack();
        setVisible(true);

    }

    public void setLoopDelay(int ms) {
        timer.setDelay(ms);
    }

    public void pause() {
        timer.stop();

    }

    @Override
    public void setLayout(PacManLayout layout) {

        switch (layout) {
            case INIT:
                drawInitView();
                break;
            case GAME_ON:
                drawPlayView();
                break;
            case PAUSE:
                drawPauseView();
                pause();
                break;
            case GAME_OVER:
                Game_OverView();
                pause();
                break;
            case LEVEL_OVER:
                break;
            case LIFE_OVER:
                break;
        }

    }


    @Override
    public void update() {
        repaint();

    }

    private void Game_OverView() {

        add(drawPanel, BorderLayout.CENTER);
        initialPanel.setVisible(false);
        drawPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        GOPanel.setVisible(true);


    }

    private void drawInitView() {
        initialPanel.setVisible(true);
        initialPanel.setFocusable(true);
        initialPanel.requestFocusInWindow();
        add(drawPanel, BorderLayout.CENTER);
        drawPanel.setVisible(true);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);
        GOPanel.setVisible(false);

    }

    private void drawPlayView() {
        initialPanel.removeKeyListener(startkey);
        initialPanel.setFocusable(false);
        drawPanel.setFocusable(true);
        drawPanel.requestFocusInWindow();
        timer.start();
        add(drawPanel, BorderLayout.CENTER);
        drawPanel.setVisible(true);
        initialPanel.setVisible(false);
        playPanel.setVisible(true);
        pausePanel.setVisible(false);
        GOPanel.setVisible(false);

    }

    private void drawPauseView() {
        add(drawPanel, BorderLayout.CENTER);
        drawPanel.setVisible(true);
        initialPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(true);
        GOPanel.setVisible(false);
    }
}
