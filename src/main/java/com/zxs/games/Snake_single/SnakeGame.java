package com.zxs.games.Snake_single;

import java.util.LinkedList;
import java.util.*;

public class SnakeGame {
    public int height = 100, width = 100;
    public Grid[][] board;
    public int score = 0;
    public boolean gameEnd = false;
    public LinkedList<Grid> snake; // snake[0] is head, snake[-1] is tail
    public DIRECTION direction;
    public double speed = 1;
    public boolean isChanging = false;
    Random rn = new Random();
    public void initializeBoard(){
        gameEnd = false;
        //initialize board
        for(int row = 0; row < height; row ++){
            for(int col =0; col < width; col ++){
                board[row][col] = new Grid(row, col, "white", "empty");
            }
        }
        //initialize snake
        initializeSnake();
        // generate 10 obstacles
        for (int i = 0; i < 10; i++){
            generateWall();
        }
        int counter = 10;
        while (counter != 0) {
            generateApple();
            counter--;
        }

    }

    public void initializeSnake(){
        snake = new LinkedList<Grid>();
        // generate snake
        int headRow = height/2;
        int headCol = width/2;
        board[headRow][headCol].color = "red";
        board[headRow][headCol].type = "head";
        snake.add(board[headRow][headCol]);
        headRow++;
        board[headRow][headCol].color = "green";
        board[headRow][headCol].type = "body";
        snake.add(board[headRow][headCol]);
        // get direction
        direction = DIRECTION.UP;
    }

    public int getHeadRow(){
        return snake.get(0).x;
    }

    public int getHeadCol(){
        return snake.get(0).y;
    }

    public int getTailRow(){
        return snake.get(snake.size()-1).x;
    }

    public int getTailCol(){
        return snake.get(snake.size()-1).y;
    }

    public PairInt getNextHead(){
        int x = getHeadRow(), y = getHeadCol();
        switch (direction) {
            case UP:
                x--;
                break;
            case DOWN:
                x++;
                break;
            case LEFT:
                y--;
                break;
            case RIGHT:
                y++;
                break;
        }
        return new PairInt(x,y);
    }

    /**
     *  default constructor
     */
    public SnakeGame(){
        board = new Grid[height][width];
        initializeBoard();
    }

    /**
     *
     *   constructor using height and width
     */
    public SnakeGame(int height, int width){
        this.height = height;
        this.width = width;
        board = new Grid[height][width];
        initializeBoard();
    }

    /**
     *  check if the snake can move by direction
     * @return false : hit wall or border or itself(except tail) , die, end game
     *         true : hit apple or empty grid, can move
     *
     */
    public boolean canMove(){
        if (gameEnd) return false;
        PairInt nextHead = getNextHead();
        int x = nextHead.x, y = nextHead.y;
        if ( x < 0 || x >= height || y < 0 || y >= width){ // hit border
            return false;
        }
        if ( board[x][y].type.equals("obstacle")){ // hit obstacle
            return false;
        }
        if ( board[x][y].type.equals("body") && !( x == getTailRow() && y == getTailCol())){ // hit body (not tail)
            return false;
        }
        return true;
    }

    /**
     * move the snake according to direction,
     */
    public void moveToNextGrid() {
        PairInt nextHead = getNextHead();
        int x = nextHead.x, y = nextHead.y;

        int currentX = getHeadRow();
        int currentY = getHeadCol();
        board[x][y].type = "head";
        //board[x][y].color = board[currentX][currentY].color;
        board[x][y].color="red";
        board[currentX][currentY].type = "body";
        board[currentX][currentY].color="green";
        snake.add(0,board[x][y]);
    }
    public void move(){
        //first, check canMove()

        if (gameEnd) return;
        //if can move, check if is apple or gird,
        isChanging = false;
        if(canMove()){
            PairInt nextHead = getNextHead();
            int x = nextHead.x, y = nextHead.y;
            if (board[x][y].type.equals("apple")) { // eating an apple
                moveToNextGrid();
                generateApple();
                score++;
                speed+=0.1;
            } else { // go to an empty grid or tail
                if (board[x][y].type.equals("empty")){
                    board[getTailRow()][getTailCol()].color = "white";
                    board[getTailRow()][getTailCol()].type = "empty";
                }
                moveToNextGrid();
                // move tail

                snake.removeLast();
            }
        } else {
            System.out.println("You Die!");
            gameEnd = true;
        }
    }

    /**
     * change the direction to the new direction
     * Only possible if newDirection is perpendicular to the current direction
     * !!: should move after changing direction to prevent changing direction twice
     * @param newDirection
     */
    public boolean changeDirection(DIRECTION newDirection){
        if (isChanging) //
            return false;
        isChanging = true;
        boolean canChange = false;
        if (this.direction == DIRECTION.UP || this.direction == DIRECTION.DOWN) {
            if (newDirection == DIRECTION.LEFT || newDirection == DIRECTION.RIGHT) {
                canChange = true;
            }
        }

        if (this.direction == DIRECTION.LEFT || this.direction == DIRECTION.RIGHT) {
            if (newDirection == DIRECTION.UP || newDirection == DIRECTION.DOWN) {
                canChange = true;
            }
        }

        if (canChange) {
            this.direction = newDirection;
            // move() // OR NOT?
            return true;
        } else {
            System.out.println("Please change the valid moving direction");
            return false;
        }
    }

    /**
     * generate a random apple, (grid with no snake or obstacle)
     */
    public void generateApple(){
        boolean generate = false;
        int Low = 0;
        int High = width - 1;
        while (!generate) {
            int x = rn.nextInt(High-Low);
            int y = rn.nextInt(High-Low);
            if (board[x][y].type.equals("empty")){
                Grid apple = new Grid(x, y, "blue", "apple");
                board[x][y] = apple;
                generate = true;
            }
        }

    }

    /**
     * generate a random obstacle
     */
    public void generateWall(){
        Random rand = new Random();
        int row = rand.nextInt(height);
        int col = rand.nextInt(width);
        //height is the maximum and the 1 is our minimum
        if(!board[row][col].type.equals("empty")){
            generateWall();
            return;
        }
        board[row][col].color = "black";
        board[row][col].type= "obstacle";
        return;
    }

    public static void main(String args[]){
        return;
    }
}
