package com.zxs.games.SudokuController;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import com.zxs.games.SudokuModel.Game;
import com.zxs.games.SudokuModel.UpdateAction;
import com.zxs.games.SudokuView.Field;
import com.zxs.games.SudokuView.GamePanel;

public class GameController implements MouseListener {
    private GamePanel gamePanel;    // Panel to control.
    private Game game;                  // Current Sudoku game.

    /**
     * Constructor
     */
    public GameController(GamePanel gamePanel, Game game) {
        this.game = game;
        this.gamePanel = gamePanel;
    }

    /**
     * update number in the filed to the user input one or clear the number if user right clicked
     */
    public void mousePressed(MouseEvent e) {
        JPanel panel = (JPanel)e.getSource();
        Component component = panel.getComponentAt(e.getPoint());
        if (component instanceof Field) {
            Field field = (Field)component;
            int x = field.getFieldX();
            int y = field.getFieldY();

            //left clicked, set user input number
            if (e.getButton() == MouseEvent.BUTTON1 && (game.getNumber(x, y) == 0 || field.getForeground().equals(Color.BLUE))) {
                int num = game.getSelectedNumber();
                if (num != -1){
                    game.setNumber(x, y, num);
                    field.setNumber(num, true);
                } else {
                    return;
                }
            }
            // right clicked, set number to 0
            else if (e.getButton() == MouseEvent.BUTTON3 && !field.getForeground().equals(Color.BLACK)) {
                game.setNumber(x, y, 0);
                field.setNumber(0, false);
            }
            gamePanel.update(game, UpdateAction.CANDIDATES);
        }
    }

    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
}