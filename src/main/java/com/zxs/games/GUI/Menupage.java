package com.zxs.games.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Menupage extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    int width, height;

    JButton play = new JButton("play");
    JButton settings = new JButton("settings");
    JButton exit = new JButton("exit");
    JButton mainMenu = new JButton("main menu");
    JButton snakeGame = new JButton("Snake Game II");
    JButton chessGame = new JButton("Chess Game");
    JButton sudokuGame = new JButton("Sudoku Game");
    JButton snakeGameSingle = new JButton("Snake Game I");

    CardLayout layout = new CardLayout();

    JPanel panel = new JPanel();
    JPanel game = new JPanel();
    JPanel menu = new JPanel();

    public Menupage(int width, int height) {
        this.width = width;
        this.height = height;

        panel.setLayout(layout);
        addButtons();

        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("ZXS Classic Game Collection");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();

    }

    private void addButtons() {

        play.addActionListener(this);
        settings.addActionListener(this);
        exit.addActionListener(this);
        mainMenu.addActionListener(this);

        snakeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               SnakeThread myRunnable = new SnakeThread();
               Thread t = new Thread(myRunnable);
               t.start();
            }
        });
        snakeGameSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeSingleThread myRunnableSnake = new SnakeSingleThread();
                Thread t = new Thread(myRunnableSnake);
                t.start();
            }
        });
        chessGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ChessThread myRunnableChess = new ChessThread();
                Thread t = new Thread(myRunnableChess);
                t.start();
            }
        });

        sudokuGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SodokuThread myRunnableSodo = new SodokuThread();
                Thread t = new Thread(myRunnableSodo);
                t.start();
            }
        });



        play.setPreferredSize(new Dimension(400, 60));

        settings.setPreferredSize(new Dimension(400, 60));
        exit.setPreferredSize(new Dimension(400, 60));

        //menu buttons
        menu.add(play);
        menu.add(settings);
        menu.add(exit);

        //game buttons

        mainMenu.setPreferredSize(new Dimension(400, 60));

        snakeGame.setPreferredSize(new Dimension(400, 60));
        snakeGameSingle.setPreferredSize(new Dimension(400, 60));
        chessGame.setPreferredSize(new Dimension(400, 60));
        sudokuGame.setPreferredSize(new Dimension(400, 60));

        game.add(mainMenu);
        game.add(snakeGameSingle);
        game.add(snakeGame);
        game.add(chessGame);
        game.add(sudokuGame);

        //background colors

        game.setBackground(Color.PINK);

        menu.setBackground(Color.ORANGE);

        //adding children to parent Panel
        panel.add(menu,"Menu");
        panel.add(game,"Game");
        add(panel);
        layout.show(panel,"Menu");

    }

    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == play) {
            layout.show(panel, "Game");
        } else if (source == settings){

        } else if (source == mainMenu){
            layout.show(panel, "Menu");
        }

    }

    public static void main(String[] args) {
        new Menupage(500,500);
    }
}
