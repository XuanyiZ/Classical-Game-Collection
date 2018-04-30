package com.zxs.games.GUI;

import com.zxs.games.Snake.SnakeGame;
import com.zxs.games.Snake.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


import javax.swing.JFrame;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class SnakeGUI extends JPanel implements ActionListener{
    int height = 20, width =20;
    String player, player2;
    boolean pause=false;
    public JMenuBar menuBar;
    JFrame window;
    SnakeGame snakeGame ;
    boolean AI = false;  // added
    private JButton[][] buttonGrid = new JButton[height][width];
    private final JLabel scoreInfo = new JLabel("Player0 0 : 0 Player1");
    private final JLabel speedInfo = new JLabel("Current Speed: 0");
    boolean isRunning = true;
    private final JPanel GUI = new JPanel(new BorderLayout(3,3));
    public boolean changeSkin = false;
    ImageIcon grasslandIcon = new ImageIcon("Images/grassland.jpg");
    ImageIcon snakeGridIcon = new ImageIcon("Images/snakeGrid.png");
    ImageIcon snakeGrid1Icon = new ImageIcon("Images/snakeGrid1.png");
    ImageIcon snakeHeadLeft = new ImageIcon("Images/snakeHeadLeft.png");
    ImageIcon snakeHeadRight = new ImageIcon("Images/snakeHeadRight.png");
    ImageIcon snakeHeadUp = new ImageIcon("Images/snakeHeadUp.png");
    ImageIcon snakeHead = new ImageIcon("Images/snakeHead.png");

    ImageIcon appleIcon = new ImageIcon("Images/apple.png");
    ImageIcon stoneIcon = new ImageIcon("Images/stone.jpg");

    ImageIcon newGrassLandIcon = new ImageIcon("Images/newGrassLand.jpg");
    ImageIcon newAppleIcon = new ImageIcon("Images/newApple.jpg");
    ImageIcon newSnakeBodyIcon1 = new ImageIcon("Images/newSnakeBody1.jpeg");
    ImageIcon newSnakeBodyIcon2 = new ImageIcon("Images/newSnakeBody2.jpeg");
    ImageIcon getColor(String color, Snake snake){
        String image = "";
        if (color.equals("white")){
            return grasslandIcon;
        }else if (color.equals("green")){
            return snakeGridIcon;
        } else if (color.equals("grey")) {
            return snakeGrid1Icon;
        }else if (color.equals("red")){
            if (snake.direction == DIRECTION.LEFT) {
                return snakeHeadLeft;
            } else if (snake.direction == DIRECTION.RIGHT) {
                return snakeHeadRight;
            } else if (snake.direction == DIRECTION.UP) {
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

    ImageIcon getNewColor(String color, Snake snake){
        String image = "";
        if (color.equals("white")){
            return newGrassLandIcon;
        }else if (color.equals("green")){
            return newSnakeBodyIcon1;
        } else if (color.equals("grey")) {
            return newSnakeBodyIcon2;
        }else if (color.equals("red")){
            if (snake.direction == DIRECTION.LEFT) {
                return snakeHeadLeft;
            } else if (snake.direction == DIRECTION.RIGHT) {
                return snakeHeadRight;
            } else if (snake.direction == DIRECTION.UP) {
                return snakeHeadUp;
            } else {
                return snakeHead;
            }
        }else if (color.equals("blue")){
            return newAppleIcon;
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

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,3));
        topPanel.add(new JLabel("                                       Score:"));
        topPanel.add(scoreInfo);
        topPanel.add(speedInfo);
        GUI.add(topPanel,BorderLayout.PAGE_START);
        GUI.add(myPanel);


        this.menuBar = createMenuBar();
        window.setJMenuBar(menuBar);
        myPanel.setFocusable(true);
        myPanel.requestFocusInWindow();
        myPanel.addKeyListener(new TAdapter());
        window.setContentPane(GUI);
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
        player2 = JOptionPane.showInputDialog(
                window,
                "Please Enter Second player's name",
                "Name Setup",
                JOptionPane.WARNING_MESSAGE
        );
        this.player=PlayerName;
        boolean popUp = false;
        while(true){
            if (!isRunning)
                break;
            while(!snakeGame.gameEnd){
                if (!isRunning)
                    break;
                refreshScore();
                updateSpeedInfo();
                popUp = false;
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
                snakeGame.moveBothSnake();
                drawBoard();

                //get AI movement, added
                if (AI) {
                    int AImove = snakeGame.getAIMove();
                    if (AImove == 0) {
                        snakeGame.changeDirection(snakeGame.snake2, DIRECTION.UP);
                    } else if (AImove == 1){
                        snakeGame.changeDirection(snakeGame.snake2, DIRECTION.RIGHT);
                    } else if (AImove == 2){
                        snakeGame.changeDirection(snakeGame.snake2, DIRECTION.DOWN);
                    } else if (AImove == 3){
                        snakeGame.changeDirection(snakeGame.snake2, DIRECTION.LEFT);
                    }
                }
                try{
                    Thread.sleep((long)(400/snakeGame.speed));
                } catch ( InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
            if (!isRunning)
                break;
            try{
                if (!popUp) {
                    JOptionPane.showMessageDialog(window,
                            snakeGame.snakeName.get(snakeGame.winIndex) + " win the game",
                            "A plain message",
                            JOptionPane.PLAIN_MESSAGE);
                }

                popUp = true;
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
        JMenuItem newSkin = new JMenuItem("newSkin");
        return myMenu;
    }

    public JMenu createEditMenu(){
        JMenu editMenu  = new JMenu("Menu");

        JMenuItem forfeit  = new JMenuItem("Forfeit");
        JMenuItem printinfo  = new JMenuItem("Gameinfo");
        JMenuItem restart  = new JMenuItem("Restart");
        JMenuItem newSkin = new JMenuItem("newSkin");
        final JMenuItem AIMenu = new JMenuItem("Turn on AI");
        AIMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AI = !AI;
                if (AI){
                    AIMenu.setText("Turn off AI");
                } else{
                    AIMenu.setText("Turn on AI");
                }
                snakeGame = new SnakeGame(height,width);
            }
        });
        newSkin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeSkin = !changeSkin;
                drawBoard();
            }
        });
        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Forfeit Game?", "", JOptionPane.YES_NO_OPTION);
                if(reply == 0) {
                    Thread.currentThread().interrupt();
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
                else{
                    snakeGame = new SnakeGame(height, width);
                }
            }
        });

        editMenu.add(forfeit);
        editMenu.add(printinfo);
        editMenu.add(restart);
        editMenu.add(newSkin);
        editMenu.add(AIMenu);
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
        int headRow1 = snakeGame.getHeadRow(snakeGame.snake1);
        int headCol1 = snakeGame.getHeadCol(snakeGame.snake1);
        for (int row = 0; row < height ; row ++){
            for (int col = 0; col < width; col ++){
                if (changeSkin) {
                    if (row == headRow1 && col == headCol1) {
                        buttonGrid[row][col].setIcon(getNewColor(snakeGame.board[row][col].color,snakeGame.snake1 ));
                    } else {
                        buttonGrid[row][col].setIcon(getNewColor(snakeGame.board[row][col].color,snakeGame.snake2 ));

                    }
                }
                else {
                    if (row == headRow1 && col == headCol1) {
                        buttonGrid[row][col].setIcon(getColor(snakeGame.board[row][col].color,snakeGame.snake1 ));
                    } else {
                        buttonGrid[row][col].setIcon(getColor(snakeGame.board[row][col].color,snakeGame.snake2 ));

                    }
                }
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

                //myButton.setIcon(getColor(snakeGame.board[row][col].color));
                myButton.setOpaque(true);
                myButton.setBorderPainted(false);
                myButton.setFocusable(false);
                buttonGrid[row][col] = myButton;
                myPanel.add(myButton);
            }
        }
        drawBoard();
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

            snakeGame.moveBothSnake();
            drawBoard();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT)) {
                snakeGame.changeDirection(snakeGame.snake1,DIRECTION.LEFT);
                ///System.out.println(snakeGame.direction);
            }

            else if ((key == KeyEvent.VK_RIGHT) ) {
                snakeGame.changeDirection(snakeGame.snake1,DIRECTION.RIGHT);
                //System.out.println(snakeGame.direction);
            }

            else if ((key == KeyEvent.VK_UP) ) {
                snakeGame.changeDirection(snakeGame.snake1,DIRECTION.UP);
                //System.out.println(snakeGame.direction);
            }

            else if ((key == KeyEvent.VK_DOWN) ) {
                snakeGame.changeDirection(snakeGame.snake1,DIRECTION.DOWN);
                //System.out.println(snakeGame.direction);
            }
            else if ((key == KeyEvent.VK_A)) {
                snakeGame.changeDirection(snakeGame.snake2,DIRECTION.LEFT);
                //System.out.println(snakeGame.direction);
            }

            else if ((key == KeyEvent.VK_D) ) {
                snakeGame.changeDirection(snakeGame.snake2,DIRECTION.RIGHT);
                //System.out.println(snakeGame.direction);
            }

            else if ((key == KeyEvent.VK_W) ) {
                snakeGame.changeDirection(snakeGame.snake2,DIRECTION.UP);
                //System.out.println(snakeGame.direction);
            }

            else if ((key == KeyEvent.VK_S) ) {
                snakeGame.changeDirection(snakeGame.snake2,DIRECTION.DOWN);
                //System.out.println(snakeGame.direction);
            }
            else if ((key == KeyEvent.VK_P) ) {
                pause=!pause;
            }
        }
    }

    private void refreshScore(){
        String info = player+"  "+snakeGame.snake1.score+ ":"+ snakeGame.snake2.score+"  "+player2;
        scoreInfo.setText(info);
    }

    private void updateSpeedInfo(){
        String info = "Current Speed: "+(int)(snakeGame.speed*10);
        speedInfo.setText(info);
    }


    public static void main(String[] args) {
        new SnakeGUI();
    }
}
