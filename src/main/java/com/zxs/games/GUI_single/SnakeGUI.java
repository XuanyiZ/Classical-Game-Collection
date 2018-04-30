package com.zxs.games.GUI_single;

import com.zxs.games.Snake_single.SnakeGame;
import com.zxs.games.Snake_single.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class SnakeGUI extends JPanel implements ActionListener{
    int height = 20, width =20;
    String player;
    boolean pause=false;
    public JMenuBar menuBar;
    JFrame window;
    SnakeGame snakeGame ;
    private JButton[][] buttonGrid = new JButton[height][width];
    boolean isRunning = true;
    ImageIcon grasslandIcon = new ImageIcon("Images/grassland.jpg");
    ImageIcon snakeGridIcon = new ImageIcon("Images/snakeGrid.png");

    ImageIcon snakeHeadLeft = new ImageIcon("Images/snakeHeadLeft.png");
    ImageIcon snakeHeadRight = new ImageIcon("Images/snakeHeadRight.png");
    ImageIcon snakeHeadUp = new ImageIcon("Images/snakeHeadUp.png");
    ImageIcon snakeHead = new ImageIcon("Images/snakeHead.png");

    ImageIcon appleIcon = new ImageIcon("Images/apple.png");
    ImageIcon stoneIcon = new ImageIcon("Images/stone.jpg");

    ImageIcon getColor(String color){
        String image = "";
        if (color.equals("white")){
            return grasslandIcon;
        }else if (color.equals("green")){
            return snakeGridIcon;
        }else if (color.equals("red")){
            if (snakeGame.direction == DIRECTION.LEFT) {
                return snakeHeadLeft;
            } else if (snakeGame.direction == DIRECTION.RIGHT) {
                return snakeHeadRight;
            } else if (snakeGame.direction == DIRECTION.UP) {
                return snakeHeadUp;
            } else {
                return snakeHead;
            }

        }else if (color.equals("blue")){
            return appleIcon;
        }else  {
            return stoneIcon;
        }

    }

    public SnakeGUI(){
        snakeGame = new SnakeGame(height, width);
        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }

        window = new JFrame("Classic Collection---Snake Game");
        window.setLayout(new GridLayout(height, width));
        window.setSize(800, 800);
        window.setResizable(false);
        JPanel myPanel = initializePanel();
        initializeButton(myPanel);


        this.menuBar = createMenuBar();
        window.setJMenuBar(menuBar);
        myPanel.setFocusable(true);
        myPanel.requestFocusInWindow();
        myPanel.addKeyListener(new TAdapter());
        window.setContentPane(myPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                isRunning = false;
                return;
            }
        });
        JOptionPane.showMessageDialog(window,"Welcome to ZXS Snake Game");
        String PlayerName = JOptionPane.showInputDialog(
                window,
                "Please Enter player's name",
                "Name Setup",
                JOptionPane.WARNING_MESSAGE
        );

        this.player=PlayerName;
        while(true){
            if (!isRunning)
                break;
            while(!snakeGame.gameEnd){
                if (!isRunning)
                    break;
                while(pause){
                    if (!isRunning)
                        break;
                    System.out.println("You have paused the game.");
                    try{
                        Thread.sleep(100);
                    } catch ( InterruptedException ex){
                        Thread.currentThread().interrupt();
                    }
                }
                snakeGame.move();
                drawBoard();
                try{
                    Thread.sleep((long)(400/snakeGame.speed));
                } catch ( InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
            if (!isRunning)
                break;
            try{
                Thread.sleep(100);
            } catch ( InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }

    }

    public JMenuBar createMenuBar() {
        JMenuBar myMenu =  new JMenuBar();

        JMenu editeMenu  = createEditMenu();

        myMenu.add(editeMenu);

        return myMenu;
    }

    public JMenu createEditMenu(){
        JMenu editMenu  = new JMenu("Menu");

        JMenuItem forfeit  = new JMenuItem("Forfeit");
        JMenuItem printinfo  = new JMenuItem("Gameinfo");
        JMenuItem restart  = new JMenuItem("Restart");


        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Forfeit Game?", "", JOptionPane.YES_NO_OPTION);
                if(reply == 0) {

                }
            }
        });



        printinfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printinfoHelper();
            }
        });

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause = false;
                if(snakeGame.gameEnd==false){
                    snakeGame = new SnakeGame(height, width);
                    System.out.println("Game not end... Restarting ..");
                }
                //SnakeGUI gui=new SnakeGUI();
                else{
                    System.out.println("Game ended... Restarting...");
                    snakeGame = new SnakeGame(height, width);
                    //snakeGame.initializeBoard();
                    System.out.println(snakeGame.gameEnd);
                    String name = JOptionPane.showInputDialog(window,
                            "What is your name?", null);



//                        SnakeGUI s=new SnakeGUI();
//                        s.requestFocus();
//                        s.setFocusable(true);
//                        s.requestFocusInWindow();
                      /*
                        while(!snakeGame.gameEnd){
                            while(pause){
                                System.out.println("ZZZzzz...");
                            }
                            snakeGame.move();
                            drawBoard();

                        }
                        */
                }
                //snakeGame.gameEnd = false;

            }
        });



        editMenu.add(forfeit);
        editMenu.add(printinfo);
        editMenu.add(restart);

        return editMenu;
    }

    public int printinfoHelper(){
        String score= Integer.toString(this.snakeGame.score);

        String turn="";

        showMessageDialog(null,
                "the current score of the player "+ this.player +" is: "+score+ "\n" +
                        turn, "Current Information", INFORMATION_MESSAGE);

        return 0;
    }

    public void drawBoard(){
        for (int row = 0; row < height ; row ++){
            for (int col = 0; col < width; col ++){
                buttonGrid[row][col].setIcon(getColor(snakeGame.board[row][col].color));
            }
        }
    }
    /**
     * initialize button for all chess pieces
     */
    private void initializeButton(JPanel myPanel) {
        for(int row = 0;row < height;row++){
            for(int col = 0;col<width;col++){
                JButton myButton = new JButton();

                myButton.setIcon(getColor(snakeGame.board[row][col].color));
                myButton.setOpaque(true);
                myButton.setBorderPainted(false);
                myButton.setFocusable(false);
                buttonGrid[row][col] = myButton;
                myPanel.add(myButton);
            }
        }
    }
    /**
     * initialize JPanel for the chess board
     */
    private JPanel initializePanel() {
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(800,800));
        myPanel.setLayout(new GridLayout(height,width));
        //myPanel.setBorder(new LineBorder(Color.BLACK));
        return myPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!snakeGame.gameEnd) {

            snakeGame.move();
            drawBoard();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT)) {
                snakeGame.changeDirection(DIRECTION.LEFT);
                System.out.println(snakeGame.direction);
            }

            if ((key == KeyEvent.VK_RIGHT) ) {
                snakeGame.changeDirection(DIRECTION.RIGHT);
                System.out.println(snakeGame.direction);
            }

            if ((key == KeyEvent.VK_UP) ) {
                snakeGame.changeDirection(DIRECTION.UP);
                System.out.println(snakeGame.direction);
            }

            if ((key == KeyEvent.VK_DOWN) ) {
                snakeGame.changeDirection(DIRECTION.DOWN);
                System.out.println(snakeGame.direction);
            }

            if ((key == KeyEvent.VK_P) ) {
                pause=!pause;
            }
        }
    }
    public static void main(String[] args) {
        new SnakeGUI();
    }
}
