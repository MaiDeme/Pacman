package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;
import fr.upsaclay.bibs.pacman.model.board.Board;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JFrame implements PacManView{

    private Controller controller;

    public static final int PIXELS_PER_CELLS = 2;
    DrawBoard drawBoard ;
    public final Timer timer = new Timer(1, null);

    public JPanel initialPanel;
    public JPanel playPanel;
    public JPanel pausePanel;

    public BoardView(String name, Board board) {
        super(name);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Create the drawBoard
        drawBoard = new DrawBoard(board);
    }

    @Override
    public void setBoard(Board board) {
        drawBoard.setBoard(board);
    }



    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {
        // General initialization

        drawBoard.initialize();
        add( drawBoard );


        // The initial panel (quand pon lance la fonction main)
        initialPanel = new JPanel();
        initialPanel.setPreferredSize(new Dimension(drawBoard.getPreferredSize().width, drawBoard.getPreferredSize().height));

        JButton initialStartButton;
        initialStartButton = new JButton("Start");
        initialStartButton.addActionListener(new ButtonListener(controller, GameAction.START));
        initialPanel.add(initialStartButton);
        drawBoard.add(initialPanel);


        // Timer initialization
        timer.addActionListener(new ButtonListener(controller, GameAction.NEXT_FRAME));

        // The play panel (when the simulation is running)
        playPanel = new JPanel();
        playPanel.setPreferredSize(new Dimension(drawBoard.getPreferredSize().width, drawBoard.getPreferredSize().height));
        JButton PauseButton;
        PauseButton = new JButton("Pause");
        PauseButton.addActionListener(new ButtonListener(controller, GameAction.PAUSE));
        playPanel.add(PauseButton);
        drawBoard.add(playPanel);


        // The pause panel (when the game is on pause)
        pausePanel = new JPanel();
        pausePanel.setPreferredSize(new Dimension(drawBoard.getPreferredSize().width, drawBoard.getPreferredSize().height));
        JButton ResumeButton;
        ResumeButton = new JButton("Resume");
        ResumeButton.addActionListener(new ButtonListener(controller, GameAction.RESUME));
        pausePanel.add(ResumeButton);
        drawBoard.add(pausePanel);


        pack();
        setVisible(true);

    }

    @Override
    public void setLoopDelay(int ms) {
        timer.setDelay(ms);
    }

    @Override
    public void setLayout(PacManLayout layout) {

        switch (layout) {
            case INIT : drawInitView();break;
        }
        switch (layout) {
            case GAME_ON  : drawPlayView();break;
        }
        switch (layout) {

            case PAUSE  : drawPauseView();break;
        }



    }

    @Override
    public void update() {
        repaint();

    }

    private void drawInitView() {
        initialPanel.setVisible(true);
        playPanel.setVisible(false);
        pausePanel.setVisible(false);

    }

    private void drawPlayView() {
        timer.start();
        initialPanel.setVisible(false);
        playPanel.setVisible(true);
        pausePanel.setVisible(false);

    }

    private void drawPauseView() {
        timer.stop();
        initialPanel.setVisible(false);
        playPanel.setVisible(false);
        pausePanel.setVisible(true);

    }
}
