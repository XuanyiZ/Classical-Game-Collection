package com.zxs.games.SudokuView;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import com.zxs.games.SudokuController.ButtonController;
import com.zxs.games.SudokuController.GameController;
import com.zxs.games.SudokuModel.Game;

/**
 * Main class of program.
 */
public class Sudoku extends JFrame {
    public Sudoku() {
        super("Sudoku");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        Game game = new Game();
        ButtonPanel buttonPanel = new ButtonPanel();
        ButtonController buttonController = new ButtonController(game);
        buttonPanel.setController(buttonController);
        add(buttonPanel, BorderLayout.EAST);

        GamePanel gamePanel = new GamePanel();
        GameController gameController = new GameController(gamePanel, game);
        gamePanel.setField(game);
        gamePanel.setController(gameController);
        add(gamePanel, BorderLayout.CENTER);

        game.addObserver(gamePanel);
        game.addObserver(buttonPanel);


        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Main entry point of program.
     */
    public static void main(String[] args) {
        new Sudoku();
    }
}