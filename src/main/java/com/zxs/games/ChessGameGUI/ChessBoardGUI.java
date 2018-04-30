package com.zxs.games.ChessGameGUI;

import com.zxs.games.ChessProgram.ChessBoard;
import com.zxs.games.ChessProgram.moveStats;
import com.zxs.games.ChessProgram.*;
import javafx.util.Pair;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChessBoardGUI {
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private JButton[][] buttonGrid = new JButton[ROWS][COLS];
    private ChessBoard board = new ChessBoard(1, false);
    private JButton selectedPiece = null;
    private final JPanel GUI = new JPanel(new BorderLayout(3,3));
    private final JLabel message = new JLabel(" Chess game is ready to play! White's turn ");
    private final JLabel scoreInfo = new JLabel("Player0 0 : 0 Player1");
    private final JLabel turnInfo = new JLabel("Player0 plays Black");
    private static String players[] = new String[] {"Player0", "Player1"};
    private static int blackPlayer = 0;
    private static int playerScores[] = new int []{0,0};
    private static JPanel myPanel;
    JFrame window;

    public ChessBoardGUI() {
        window = new JFrame("Chess Game");
        window.setSize(600, 600);
        window.setResizable(false);
        myPanel = initializePanel();
        initializeButton(myPanel);
        initializeGUI(myPanel);
        window.setContentPane(GUI);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialize the entire GUI, including toolbars, scoreinformation and the main chessboard
     * @param chessBoardGUI
     */
    private void initializeGUI(JPanel chessBoardGUI){
        GUI.setBorder(new EmptyBorder(5,5,5,5));

        JToolBar tools = new JToolBar();
        //tools.setPreferredSize(new Dimension(600,50) );
        tools.setFloatable(false);
        GUI.add(tools, BorderLayout.PAGE_START);
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameClicked(e);
            }
        });
        tools.add(newGameButton);
        tools.addSeparator();
        JButton forfeitButton = new JButton("Forfeit");
        forfeitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forfeitClicked(e);
            }
        });
        tools.add(forfeitButton);
        tools.addSeparator();
        JButton changeSideButton = new JButton("Change side");
        changeSideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeSideClicked(e);
            }
        });
        tools.add(changeSideButton);
        tools.addSeparator();
        JButton newPlayersButton = new JButton("New Players");
        newPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPlayersClicked(e);
            }
        });
        tools.add(newPlayersButton);
        tools.addSeparator();
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoClicked(e);
            }
        });
        tools.add(undoButton);
        tools.addSeparator();
        tools.add(message);
        JPanel pageEndPanel = new JPanel();
        pageEndPanel.setLayout(new GridLayout(1,3));
        pageEndPanel.add(new JLabel("                                       Score:"));
        pageEndPanel.add(scoreInfo);
        pageEndPanel.add(turnInfo);
        GUI.add(pageEndPanel, BorderLayout.PAGE_END);
        GUI.add(chessBoardGUI);
    }

    /**
     * initialize button for all chess pieces
     */
    private void initializeButton(JPanel myPanel) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton myButton = new JButton();
                buttonGrid[row][col] = myButton;
                setButtonIcon(row,col);
                if ((row + col) % 2 == 0)
                    myButton.setBackground(Color.WHITE);
                else
                    myButton.setBackground(Color.GRAY);
                myButton.setOpaque(true);
                myButton.setBorderPainted(false);
                myButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pieceButtonClicked(e);
                    }
                });
                myPanel.add(myButton);
            }
        }
    }

    /**
     * event listener when a chess piece is clicked
     * @param e
     */
    protected void pieceButtonClicked(ActionEvent e) {
        if (board.ended) return;
        JButton button = (JButton) e.getSource();
        Pair<Integer, Integer> coord = getJButtonCoord(button);
        int row = coord.getKey();
        int col = coord.getValue();

        if (board.isCurrentPlayerPiece(row, col)) {
            if (selectedPiece != null) {
                setAllToDefaultColor();
                //selectedPiece.setBackground(getDefaultColor(selectedPiece));
            }
            selectedPiece = button;
            selectedPiece.setBackground(Color.BLUE);
            drawPossibleMoves();
        } else {
            if (selectedPiece != null) { //try move selectedPiece to coord
                Pair<Integer,Integer> coordSelected = getJButtonCoord(selectedPiece);
                int startRow = coordSelected.getKey();
                int startCol = coordSelected.getValue();
                int status = board.movePiece(startRow,startCol,row,col);
                if (status == 0){ // invalid move
                    message.setText("illegal move");
                }else {
                    //move the piece
                    button.setIcon(selectedPiece.getIcon());
                    selectedPiece.setIcon(null);
                    selectedPiece.setBackground(getDefaultColor(selectedPiece));
                    selectedPiece = null;
                    setAllToDefaultColor();
                    if (status == 1) {
                        Boolean isChecked = board.isChecked(board.turn);
                        String nextTurn = board.turn == 0 ? "Black" : "White";
                        if (isChecked)
                            nextTurn = "CHECK! " + nextTurn;
                        message.setText(nextTurn + "'s turn");
                    }else if (status == 2){ // black wins
                        message.setText("Black Wins!");
                        playerScores[blackPlayer]++;
                        refreshScore();
                    } else if (status == 3){ // white wins
                        playerScores[ 1- blackPlayer] ++;
                        refreshScore();
                        message.setText("White Wins!");
                    } else if (status == 4 ) { // draws
                        playerScores[0]++;
                        playerScores[1]++;
                        refreshScore();
                        message.setText("Draws!");
                    }
                }
            }
        }
    }

    /**
     * event listener for new game button
     * will start new game and initialize everything except player information
     * @param e
     */
    protected void newGameClicked(ActionEvent e) {
        startNewGame();
    }

    /**
     * event listener for new player button
     * ask users to input player names and initialize the entire game
     * @param e
     */
    protected void newPlayersClicked(ActionEvent e) {
        getPlayerNames();
        blackPlayer = 0;
        playerScores[0] = 0;
        playerScores[1] = 0;
        refreshScore();
        updateTurnInfo();
        startNewGame();
    }

    /**
     * find all the possible moves and change the background to green
     */
    protected void drawPossibleMoves(){
        if (selectedPiece == null) return;
        Pair<Integer,Integer> coord = getJButtonCoord(selectedPiece);
        int startRow = coord.getKey();
        int startCol = coord.getValue();
        for(int row = 0;row < ROWS;row ++){
            for(int col = 0; col < COLS ;col++){
                if (board.canMove(startRow,startCol,row,col)){
                    buttonGrid[row][col].setBackground(Color.GREEN);
                }
            }
        }


    }
    /**
     * set the button at (row,col) to its corresponding icon according to the piece information
     * @param row
     * @param col
     */
    protected void setButtonIcon(int row,int col){
        JButton button = buttonGrid[row][col];
        button.setIcon(null);
        if (board.chessBoard[row][col] == null)
            return;

        String fileName = board.chessBoard[row][col].getIconFileName();
        //getFileName(row, col);
        BufferedImage img = null;
        if (!fileName.equals("")) {
            try {
                File f = new File(System.getProperty("user.dir") + "/src/main/java/com/zxs/games/ChessPng/" + fileName);
                img = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (img != null) {
            ImageIcon icon = new ImageIcon(img);
            button.setIcon(icon);
        }
    }

    /**
     * event listener for the undo button
     * undo the last move and reset the corresponding icon and turn information
     * @param e
     */
    protected void undoClicked(ActionEvent e) {
        setAllToDefaultColor();
        moveStats lastMove = board.undo();
        if (lastMove == null) {
            message.setText("Can't undo now");
            return; //cannot undo
        }

        if (selectedPiece != null){
            selectedPiece.setBackground(getDefaultColor(selectedPiece));
            selectedPiece = null;
        }
        setButtonIcon(lastMove.startRow,lastMove.startCol);
        setButtonIcon(lastMove.endRow,lastMove.endCol);
        message.setText(getTurnMessage());

    }

    /**
     * event listener for change side button
     * will change the color of two players
     * and start a new game!!
     * @param e
     */
    protected void changeSideClicked(ActionEvent e) {
        blackPlayer = 1 - blackPlayer;
        updateTurnInfo();
        startNewGame();
    }

    /**
     * set the message info at the top to default for a new game
     */
    private void setDefulatMessage(){
        message.setText(" Chess game is ready to play! White's turn ");
    }

    /**
     * event listener for forfeit button
     * the opponent gains a point and restart the name
     * @param e
     */
    protected void forfeitClicked(ActionEvent e) {
        if (board.ended){
            message.setText("The game is ended. Please start new game");
            return ;
        }
        int curturn = board.turn;
        if (curturn == 0){
            playerScores[ 1- blackPlayer] ++;
        }else
            playerScores[blackPlayer] ++;
        refreshScore();
    }

    /**
     * refresh the score board
     */
    private void refreshScore(){
        String info = players[0] + " "+playerScores[0]+":" +playerScores[1]+ " "+players[1];
        scoreInfo.setText(info);
    }

    /**
     * update to info to show which player is playing black right now
     */
    private void updateTurnInfo(){
        String info = players[blackPlayer] + " plays Black";
        turnInfo.setText(info);
    }

    /**
     * initialize everything,except player information,
     * and start a new game
     */
    private void startNewGame(){
        if (selectedPiece!=null)
            selectedPiece.setBackground(getDefaultColor(selectedPiece));
        selectedPiece = null;
        setAllToDefaultColor();
        setDefulatMessage();
        board = new ChessBoard(1, false);
        for(int row = 0 ; row < ROWS; row++){
            for(int col = 0; col < COLS ; col++){
                setButtonIcon(row,col);
            }
        }
    }

    /**
     * initialize JPanel for the chess board
     */
    private JPanel initializePanel() {
        JPanel myPanel = new JPanel();
        //myPanel.setPreferredSize(new Dimension(600,600));

        myPanel.setLayout(new GridLayout(8,8));
        myPanel.setSize(600,600);
        myPanel.setLocation(100,100);
        myPanel.setBorder(new LineBorder(Color.BLACK));
        return myPanel;
    }

    /**
     * get the coordinate of a piece button
     * @param button
     * @return
     */
    public Pair<Integer,Integer> getJButtonCoord(JButton button){
        int row=0,col=0;
        Boolean foundButton = false;
        for(row =0 ; row < ROWS; row ++){
            for(col = 0; col < COLS; col++){
                if (buttonGrid[row][col] == button){
                    foundButton = true;
                    break;
                }
            }
            if (foundButton) break;
        }
        Pair<Integer,Integer> coord = new Pair<Integer,Integer> (row,col);
        return coord;
    }

    /**
     * get the default background color of a piece button
     * @param button
     * @return
     */
    public Color getDefaultColor(JButton button){
        Pair<Integer,Integer> coord = getJButtonCoord(button);
        int row = coord.getKey();
        int col = coord.getValue();
        if  ((row + col ) %2 == 0){
            return Color.white;
        }else
            return Color.gray;
    }

    /**
     *
     */
    public void setAllToDefaultColor(){
        for(int row =0 ; row < ROWS; row++){
            for(int col =0;col < COLS;col++){
                buttonGrid[row][col].setBackground(getDefaultColor(buttonGrid[row][col]));
            }
        }
    }
    /**
     * ask the user to input their names in a prompt
     */
    private void getPlayerNames(){
        players[0] = JOptionPane.showInputDialog("Please input the name for the first player");
        players[1] = JOptionPane.showInputDialog("Please input the name for the second player");
    }

    /**
     * get the message to show  who's turn
     * @return
     */
    private String getTurnMessage(){
        if (board.turn ==0)
            return "Black's turn";
        else
            return "White's turn";
    }

    public static void main(String[] args) {

        new ChessBoardGUI();
    }
}