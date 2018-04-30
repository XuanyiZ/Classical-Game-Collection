package com.zxs.games.SudokuView;

import com.zxs.games.SudokuController.ButtonController;
import com.zxs.games.SudokuModel.UpdateAction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


/**
 * This class draws the button panel and reacts to updates from the SudokuModel.
 */
public class ButtonPanel extends JPanel implements Observer {
    ButtonGroup bgNumbers;          // Group for grouping the toggle buttons.
    JButton newButton, checkButton;   // Used buttons.
    JToggleButton[] numberButtons;     // Used toggle buttons.

    /**
     * Constructs the panel and arranges all components.
     */
    public ButtonPanel() {
        super(new BorderLayout());
        JPanel pnlAlign = new JPanel();
        pnlAlign.setLayout(new BoxLayout(pnlAlign, BoxLayout.PAGE_AXIS));
        add(pnlAlign, BorderLayout.NORTH);

        JPanel pnlOptions = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pnlOptions.setBorder(BorderFactory.createTitledBorder(" Options "));
        pnlAlign.add(pnlOptions);

        newButton = new JButton("New");
        newButton.setFocusable(false);
        pnlOptions.add(newButton);

        checkButton = new JButton("Check");
        checkButton.setFocusable(false);
        pnlOptions.add(checkButton);

        JPanel pnlNumbers = new JPanel();
        pnlNumbers.setLayout(new BoxLayout(pnlNumbers, BoxLayout.PAGE_AXIS));
        pnlNumbers.setBorder(BorderFactory.createTitledBorder(" Numbers "));
        pnlAlign.add(pnlNumbers);

        JPanel pnlNumbersNumbers = new JPanel(new FlowLayout(FlowLayout.LEADING));
        pnlNumbers.add(pnlNumbersNumbers);

        bgNumbers = new ButtonGroup();
        numberButtons = new JToggleButton[9];
        for (int i = 0; i < 9; i++) {
            numberButtons[i] = new JToggleButton("" + (i + 1));
            numberButtons[i].setPreferredSize(new Dimension(50, 50));
            numberButtons[i].setFocusable(false);
            bgNumbers.add(numberButtons[i]);
            pnlNumbersNumbers.add(numberButtons[i]);
        }
    }

    /**
     * Method called when SudokuModel sends update notification.
     *
     */
    public void update(Observable o, Object arg) {
        switch ((UpdateAction)arg) {
            case NEW_GAME:
            case CHECK:
                bgNumbers.clearSelection();
                break;
        }
    }

    /**
     * Adds GameController to all components.
     */
    public void setController(ButtonController buttonController) {
        newButton.addActionListener(buttonController);
        checkButton.addActionListener(buttonController);
        for (int i = 0; i < 9; i++)
            numberButtons[i].addActionListener(buttonController);
    }
}