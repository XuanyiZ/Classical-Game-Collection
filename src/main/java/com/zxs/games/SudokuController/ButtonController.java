package com.zxs.games.SudokuController;

import com.zxs.games.SudokuModel.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonController implements ActionListener {
    private Game game;

    /**
     * Constructor
     */
    public ButtonController(Game game) {
        this.game = game;
    }

    /**
     * Performs action after user pressed button.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New")){
            game.newGame();
        }
        else if (e.getActionCommand().equals("Check")){
            game.checkGame();
        }
        else{
            game.setSelectedNumber(Integer.parseInt(e.getActionCommand()));
        }
    }
}