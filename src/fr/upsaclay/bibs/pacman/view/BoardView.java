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

public class BoardView extends JFrame implements PacManView {

    private Controller controller;

    public static final int PIXELS_PER_CELLS = 2;

    DrawPanel drawPanel;
    Timer timer;

    JPanel initialPanel;
    JPanel pausePanel;
    JPanel gameOverPanel;
    JPanel nextLevelPanel;
    ActorPanel actorPanel;
    Font arcadeFont;

    KeyStart startkey;
    KeyMove keylist;
    KeyLevel keyLevel;
    KeyGameOver keyGameOver;

    public BoardView(String name, int width, int height) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Create the drawPanel (where we draw the board)
        drawPanel = new DrawPanel(width, height);
        actorPanel = new ActorPanel();

        // Create the timer
        this.timer = new Timer(17, null);

    }

    @Override
    public void setBoard(Board board) {
        drawPanel.setBoard(board);
        actorPanel.setBoard(board);
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

        add(initialPanel);
        drawPanel.setFont(arcadeFont);

        // Timer initialization
        timer.addActionListener(new ButtonListener(controller, GameAction.NEXT_FRAME));


        // The pause panel (when the game is on pause)
        pausePanel = new JPanel(new GridBagLayout());
        pausePanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));
        pausePanel.setBackground(new Color(128, 128, 128, 100));
        drawPanel.add(pausePanel);

        GridBagConstraints gbc = new GridBagConstraints();


        /// Création du label pour afficher "GAME PAUSED"
        JLabel pausedLabel = new JLabel("GAME PAUSED");
        try {
            Font arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Emulogic-zrEw.ttf")).deriveFont(30f);
            // Définir la taille de la police à 30
            pausedLabel.setFont(arcadeFont); // Police en gras, taille 30
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        pausedLabel.setForeground(Color.WHITE); // Couleur jaune

    // Positionnement du label en haut du panneau pause
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_START; // Ancrer en haut du panneau
        pausePanel.add(pausedLabel, gbc);

    // Décalage de la position des boutons en dessous du label
        gbc.gridy = 1;

    // Création des boutons avec une police personnalisée et une taille de police spécifique
        JButton resumeButton = new JButton("Resume");
        styleButton(resumeButton);
        resumeButton.addActionListener(new ButtonListener(controller, GameAction.RESUME));
        pausePanel.add(resumeButton, gbc);

        JButton restartButton = new JButton("Start New Game");
        styleButton(restartButton);
        restartButton.addActionListener(new ButtonListener(controller, GameAction.NEW_GAME));
        gbc.gridy++; // Incrémenter la position en y pour le bouton suivant
        pausePanel.add(restartButton, gbc);

        JButton titleButton = new JButton("Back to title screen");
        styleButton(titleButton);
        titleButton.addActionListener(new ButtonListener(controller, GameAction.TITLE_SCREEN));
        gbc.gridy++; // Incrémenter la position en y pour le bouton suivant
        pausePanel.add(titleButton, gbc);

        JButton quitButton = new JButton("Quit");
        styleButton(quitButton);
        quitButton.addActionListener(new ButtonListener(controller, GameAction.QUIT));
        gbc.gridy++; // Incrémenter la position en y pour le bouton suivant
        pausePanel.add(quitButton, gbc);




        keylist = new KeyMove(controller);


        //Game over panel
        gameOverPanel= new GameOver();
        gameOverPanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));
        drawPanel.add(gameOverPanel);
        gameOverPanel.setFont(arcadeFont);
        keyGameOver = new KeyGameOver(controller);



        //Next Level Panel
        nextLevelPanel = new JPanel(new GridBagLayout());
        nextLevelPanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));
        nextLevelPanel.setBackground(new Color(128, 128, 128, 100));

        keyLevel = new KeyLevel(controller);

        JLabel nextLevelLabel = new JLabel("PRESS SPACE TO START THE NEXT LEVEL");
        Font labelFont = nextLevelLabel.getFont();
        float fontSize = labelFont.getSize() + 10;
        nextLevelLabel.setFont(labelFont.deriveFont(fontSize));
        nextLevelLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;

        nextLevelPanel.add(nextLevelLabel, gbc);

        drawPanel.add(nextLevelPanel);


        //Pacman panel
        actorPanel.setPreferredSize(new Dimension(drawPanel.getPreferredSize().width,drawPanel.getPreferredSize().height));
        drawPanel.add(actorPanel);


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
                pause();
                drawGameOverView();
                break;
            case LEVEL_OVER:
                pause();
                drawNextLevelView();
                break;
            case LIFE_OVER:
                drawDeathAnimation();
                break;
        }

    }

    @Override
    public void update() {
        repaint();

    }

    private void drawInitView() {
        initialPanel.addKeyListener(startkey);
        drawPanel.removeKeyListener(keyGameOver);
        initialPanel.setVisible(true);
        initialPanel.setFocusable(true);
        initialPanel.requestFocusInWindow();
        add(drawPanel, BorderLayout.CENTER);

        drawPanel.setVisible(false);
        actorPanel.setVisible(false);
        pausePanel.setVisible(false);
        gameOverPanel.setVisible(false);
        nextLevelPanel.setVisible(false);


    }

    private void drawPlayView() {
        timer.start();
        add(drawPanel, BorderLayout.CENTER);

        initialPanel.removeKeyListener(startkey);
        drawPanel.removeKeyListener(keyLevel);
        drawPanel.removeKeyListener(keylist);
        drawPanel.removeKeyListener(keyGameOver);
        initialPanel.setFocusable(false);
        initialPanel.setVisible(false);

        drawPanel.setVisible(true);

        drawPanel.addKeyListener(keylist);
        drawPanel.setFocusable(true);
        drawPanel.requestFocusInWindow();
        actorPanel.setVisible(true);
        pausePanel.setVisible(false);
        gameOverPanel.setVisible(false);
        nextLevelPanel.setVisible(false);

    }

    private void drawPauseView() {
        timer.stop();
        drawPanel.removeKeyListener(keylist);
        drawPanel.removeKeyListener(keyLevel);
        drawPanel.setFocusable(false);
        add(drawPanel, BorderLayout.CENTER);
        initialPanel.setVisible(false);
        pausePanel.setVisible(true);
        nextLevelPanel.setVisible(false);
    }

    private void drawGameOverView() {

        timer.stop();
        drawPanel.removeKeyListener(keylist);
        drawPanel.removeKeyListener(keyLevel);
        actorPanel.setFocusable(false);

        add(gameOverPanel, BorderLayout.CENTER);


        drawPanel.setFocusable(true);
        drawPanel.requestFocusInWindow();
        drawPanel.addKeyListener(keyGameOver);


        gameOverPanel.setVisible(true);
        drawPanel.setVisible(true);
        System.out.println("KeyGameOver ajoute");
    }

    private void drawDeathAnimation() {

        drawPanel.removeKeyListener(keylist);
        drawPanel.removeKeyListener(keyLevel);
        drawPanel.removeKeyListener(keyGameOver);
        drawPanel.setFocusable(false);
        add(drawPanel, BorderLayout.CENTER);
        drawPanel.setVisible(true);
        initialPanel.setVisible(false);
        pausePanel.setVisible(false);
        gameOverPanel.setVisible(false);
        nextLevelPanel.setVisible(false);
    }
    private void drawNextLevelView() {
        timer.stop();
        drawPanel.removeKeyListener(keylist);
        drawPanel.addKeyListener(keyLevel);
        drawPanel.setFocusable(true);
        drawPanel.requestFocusInWindow();


        nextLevelPanel.setVisible(true);
        pausePanel.setVisible(false);
        gameOverPanel.setVisible(false);
        initialPanel.setVisible(false);

    }


    // Fonction pour appliquer le style aux boutons
    private void styleButton(JButton button) {
        try {
            Font arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Emulogic-zrEw.ttf")).deriveFont(15f);
            // Définir la taille de la police à 20
            button.setFont(arcadeFont); // Définir la police
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        button.setOpaque(false);
        button.setContentAreaFilled(false); // Assurez-vous que la zone de contenu est également transparente
        button.setBorderPainted(false); // Masquer le contour
        button.setForeground(Color.YELLOW); // Couleur du texte
    }

}
