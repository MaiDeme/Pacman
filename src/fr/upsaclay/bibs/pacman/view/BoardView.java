package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.VisualController;
import fr.upsaclay.bibs.pacman.control.GameAction;
import fr.upsaclay.bibs.pacman.model.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import javax.swing.border.Border;

public class BoardView extends JFrame implements PacManView {

    private Controller controller;

    public static final int PIXELS_PER_CELLS = 2;

    DrawPanel drawPanel;

    Timer timer;

    JPanel initialPanel;
    JPanel playPanel;
    JPanel pausePanel;

    public BoardView(String name, int width, int height) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Create the drawPanel (where we draw the board)
        drawPanel = new DrawPanel(width,height);

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
        // General initialization
        drawPanel.initialize();
        add(drawPanel);

        // Ecran de titre
        try {
            initialPanel = new TitleScreen(drawPanel.getPreferredSize().width,drawPanel.getPreferredSize().height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initialPanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));

        JButton QuitButton;

        QuitButton = new JButton("Quit");
        QuitButton.addActionListener(new ButtonListener(controller, GameAction.QUIT));
        initialPanel.add(QuitButton);

        JButton initialStartButton;
        initialStartButton = new JButton("Start");
        initialStartButton.addActionListener(new ButtonListener(controller, GameAction.START));
        initialPanel.add(initialStartButton);

        drawPanel.add(initialPanel);

        // Timer initialization
        timer.addActionListener(new ButtonListener(controller, GameAction.NEXT_FRAME));

        // The play panel (when the game is running)
        playPanel = new JPanel();
        playPanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));

        JButton PauseButton;
        PauseButton = new JButton("Pause");
        PauseButton.addActionListener(new ButtonListener(controller, GameAction.PAUSE));
        playPanel.add(PauseButton);
        playPanel.setBackground(new Color(0,0,0,0) ); // panel transparent mais les boutons sont visibles
        drawPanel.add(playPanel);
       

        // The pause panel (when the game is on pause)
        pausePanel = new JPanel(new GridBagLayout());
        pausePanel.setPreferredSize(
                new Dimension(drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height));


        GridBagConstraints gbc = new GridBagConstraints();

        JButton ResumeButton;
        ResumeButton = new JButton("Resume");
        ResumeButton.addActionListener(new ButtonListener(controller, GameAction.RESUME));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // 10 pixels of padding above and below

        pausePanel.add(ResumeButton,gbc);

        pausePanel.setBackground(new Color (128,128,128,100)); //semi transparent parce que le jeu est en pause
        JLabel pauseLabel = new JLabel("Game Paused");
        pauseLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;

        pausePanel.add(pauseLabel,gbc);

        drawPanel.add(pausePanel);
        gbc.gridy = 2;

        pausePanel.add(QuitButton,gbc);

        
        Key keylist= new Key(controller);
        addKeyListener(keylist);
        setFocusable(true);
        requestFocusInWindow();
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

    private void drawInitView() {
        initialPanel.setVisible(true);
        add(drawPanel,BorderLayout.CENTER);
        drawPanel.setVisible(true);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);

    }

    private void drawPlayView() {
        timer.start();
        add(drawPanel, BorderLayout.CENTER);
        drawPanel.setVisible(true);
        initialPanel.setVisible(false);
        playPanel.setVisible(true);
        pausePanel.setVisible(false);

    }

    private void drawPauseView() {
        add(drawPanel, BorderLayout.CENTER);
        drawPanel.setVisible(true);
        initialPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(true);
    }
}
