package com.zxs.games.Snake;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.*;

public class SnakeGame {
    public int height = 100, width = 100;
    public Grid[][] board;
    public int score = 0;
    public boolean gameEnd = false;
    public int winIndex = 0;
    public LinkedList<Grid> snake; // snake[0] is head, snake[-1] is tail
    public DIRECTION direction;
    public double speed = 1;
    public boolean isChanging = false;

    public Snake snake1,snake2;
    Random rn = new Random();
    public HashMap snakeName = new HashMap<Integer, String>();

    public void initializeBoard(){
        gameEnd = false;
        //initialize board
        for(int row = 0; row < height; row ++){
            for(int col =0; col < width; col ++){
                board[row][col] = new Grid(row, col, "white", "empty");
            }
        }
        //initialize snake
        snake1 = new Snake(1);
        snake2 = new Snake(2);
        initializeSnake(snake1);
        initializeSnake(snake2);
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

    public void initializeSnake(Snake snake){
        snake.snakeBody = new LinkedList<Grid>();
        // generate snake
        int headRow, headCol;
        if (snake.index == 2){
            headRow = height/2;
            headCol = width/4;
        } else {
            headRow = height/2;
            headCol = width/4*3;
        }
        board[headRow][headCol].color = "red";
        board[headRow][headCol].type = "head";
        snake.snakeBody.add(board[headRow][headCol]);
        headRow++;
        if (snake.index == 1)
            board[headRow][headCol].color = "green";
        else
            board[headRow][headCol].color = "grey";
        board[headRow][headCol].type = "body";
        snake.snakeBody.add(board[headRow][headCol]);
        // get direction
        snake.direction = DIRECTION.UP;
    }

    public int getHeadRow(Snake snake){
        return snake.snakeBody.get(0).x;
    }

    public int getHeadCol(Snake snake){
        return snake.snakeBody.get(0).y;
    }

    public int getTailRow(Snake snake){
        return snake.snakeBody.get(snake.snakeBody.size()-1).x;
    }

    public int getTailCol(Snake snake){
        return snake.snakeBody.get(snake.snakeBody.size()-1).y;
    }

    public PairInt getNextHead(Snake snake){
        int x = getHeadRow(snake), y = getHeadCol(snake);
        switch (snake.direction) {
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
        snakeName.put(1, "Green");
        snakeName.put(2, "Black");
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
        snakeName.put(1, "Green Snake");
        snakeName.put(2, "Black Snake");
        snakeName.put(0, "You Both");
    }

    /**
     *  check if the snake can move by direction
     * @return false : hit wall or border or itself(except tail) , die, end game
     *         true : hit apple or empty grid, can move
     *
     */
    public boolean canMove(Snake snake){
        if (gameEnd) return false;
        PairInt nextHead = getNextHead(snake);
        int x = nextHead.x, y = nextHead.y;
        if ( x < 0 || x >= height || y < 0 || y >= width){ // hit border
            return false;
        }
        if ( board[x][y].type.equals("obstacle")){ // hit obstacle
            return false;
        }
        if ( board[x][y].type.equals("body") && !( x == getTailRow(snake1) && y == getTailCol(snake1)) &&
                !( x == getTailRow(snake2) && y == getTailCol(snake2))){ // hit body (not tail)
            return false;
        }
        return true;
    }

    /**
     * move the snake according to direction,
     */
    public void moveToNextGrid(Snake snake) {
        PairInt nextHead = getNextHead(snake);
        int x = nextHead.x, y = nextHead.y;

        int currentX = getHeadRow(snake);
        int currentY = getHeadCol(snake);
        board[x][y].type = "head";
        //board[x][y].color = board[currentX][currentY].color;
        board[x][y].color="red";
        board[currentX][currentY].type = "body";
        if (snake.index == 1)
            board[currentX][currentY].color = "green";
        else
            board[currentX][currentY].color = "grey";
        snake.snakeBody.add(0,board[x][y]);
    }
    public void move(Snake snake){
        //first, check canMove()
        if (gameEnd) return;
        //if can move, check if is apple or gird,
        snake.isChanging = false;
        if(canMove(snake)){
            PairInt nextHead = getNextHead(snake);
            int x = nextHead.x, y = nextHead.y;
            if (board[x][y].type.equals("apple")) { // eating an apple
                moveToNextGrid(snake);
                generateApple();
                snake.score++;
                System.out.println("Current score "+score);
                speed+=0.1;
                System.out.println("Current speed "+speed);
            } else { // go to an empty grid or tail
                if (board[x][y].type.equals("empty")){
                    board[getTailRow(snake)][getTailCol(snake)].color = "white";
                    board[getTailRow(snake)][getTailCol(snake)].type = "empty";
                }
                moveToNextGrid(snake);
                snake.snakeBody.removeLast();
            }
        } else {
            System.out.println("You Die!");
            winIndex = 3 - snake.index;
            gameEnd = true;
        }
    }

    /**
     *  move both snake
     */
    public void moveBothSnake(){
        //check if will move to the same grid first

        PairInt nextHead1 = getNextHead(snake1);
        PairInt nextHead2 = getNextHead(snake2);
        if (nextHead1.x == nextHead2.x  && nextHead1.y == nextHead2.y){ // moving to the same spot
            winIndex = 0;
            gameEnd = true;
            System.out.println("You Die Together!");
            return;
        }
        if (nextHead1.x == getTailRow(snake2) && nextHead1.y == getTailCol(snake2) ){
            move(snake2);
            move(snake1);
        } else if (nextHead2.x == getTailRow(snake1) && nextHead2.y == getTailCol(snake1)){
            move(snake1);
            move(snake2);
        } else {
            move(snake1);
            move(snake2);
        }
    }
    /**
     * change the direction to the new direction
     * Only possible if newDirection is perpendicular to the current direction
     * !!: should move after changing direction to prevent changing direction twice
     * @param newDirection
     */
    public boolean changeDirection(Snake snake, DIRECTION newDirection){
        if (snake.isChanging) //
            return false;
        snake.isChanging = true;
        boolean canChange = false;
        if (snake.direction == DIRECTION.UP || snake.direction == DIRECTION.DOWN) {
            if (newDirection == DIRECTION.LEFT || newDirection == DIRECTION.RIGHT) {
                canChange = true;
            }
        }

        if (snake.direction == DIRECTION.LEFT || snake.direction == DIRECTION.RIGHT) {
            if (newDirection == DIRECTION.UP || newDirection == DIRECTION.DOWN) {
                canChange = true;
            }
        }

        if (canChange) {
            snake.direction = newDirection;
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


    /**
     * get the next movement of the AI snake
     * @return 0,1,2,3 for up, right, down, left. Otherwise, don't move
     */
    public int getAIMove(){
        //if can't move, change direction to a non wall
        DIRECTION curDir = snake2.direction;
        DIRECTION nextDir1 = clockwise(curDir,1);
        DIRECTION nextDir2 = clockwise(curDir,3);
        System.out.println("Current direction:" + dirToNum(snake2.direction) );
        if (canMove(snake2)){
            //find a path to the apple
            // if headed to an apple , stay
            if (appleInFront(snake2)) {
                System.out.println("Apple in front, don't move!");
                return -1;
            }
            //if making a turn can goes to apple, turn
            changeDirection(snake2, nextDir1);
            snake2.isChanging = false;
            if (canMove(snake2) && appleInFront(snake2)){
                changeDirection(snake2, curDir);
                snake2.isChanging = false;
                System.out.println("Turning right to get apple!!");
                return dirToNum(nextDir1);
            }
            changeDirection(snake2, curDir);
            snake2.isChanging = false;
            changeDirection(snake2, nextDir2);
            snake2.isChanging = false;
            if (canMove(snake2) && appleInFront(snake2)){
                changeDirection(snake2, curDir);
                snake2.isChanging = false;
                System.out.println("Turning left to get apple!!");
                return dirToNum(nextDir2);
            }
            changeDirection(snake2, curDir);
            snake2.isChanging = false;
            // else stay
            System.out.println("Can move, just stay for now!");
            return -1;
        }

        //can't move

        System.out.println("Can't move!! Turning");
        DIRECTION nextDir[] = new DIRECTION[2];
        if (rn.nextBoolean()){
            DIRECTION temp = nextDir1;
            nextDir1 = nextDir2;
            nextDir2 = temp;
        }
        //try a random next direction
        changeDirection(snake2, nextDir2);
        snake2.isChanging = false;
        if (canMove(snake2)){
            changeDirection(snake2, curDir);
            snake2.isChanging = false;
            return dirToNum(nextDir2);
        }
        changeDirection(snake2, curDir);
        snake2.isChanging = false;
        return dirToNum(nextDir1);
    }

    /**
     * move the direction by a certain degree, 1 for 90', 2 for 180', 3 for 270'
     * @param dir
     * @param i
     * @return
     */
    public DIRECTION clockwise(DIRECTION dir, int i){
        int curdir = dirToNum(dir);
        curdir+=i;
        curdir%=4;
        return numToDir(curdir);
    }

    /**
     * transform a number to a direction as follows
     * 0-UP, 1-RIGHT, 2-DOWN, 3-LEFT
     * @param curdir
     * @return
     */
    public DIRECTION numToDir(int curdir){
        if (curdir == 0){
            return DIRECTION.UP;
        } else if (curdir == 1){
            return DIRECTION.RIGHT;
        } else if (curdir == 2){
            return DIRECTION.DOWN;
        } else
            return DIRECTION.LEFT;
    }

    /**
     * transform a direction to a number as follows
     * 0-UP, 1-RIGHT, 2-DOWN, 3-LEFT
     * @param dir
     * @return
     */
    public int dirToNum(DIRECTION dir){
        if (dir == DIRECTION.UP){
            return 0;
        } else if (dir == DIRECTION.RIGHT){
            return 1;
        } else if (dir == DIRECTION.DOWN){
            return 2;
        } else
            return 3;
    }

    /**
     * check if there is an apple in the snake's current path
     * @param snake the snake to check
     * @return boolean
     */
    public boolean appleInFront(Snake snake){
        int x = getHeadRow(snake);
        int y = getHeadCol(snake);
        if (snake.direction ==  DIRECTION.UP){
            for(int row = x-1; row >=0; row-- ){
                if (board[row][y].type.equals("apple"))
                    return true;
            }
        } else if (snake.direction == DIRECTION.RIGHT){
            for(int col = y+1; col < width; col++){
                if (board[x][col].type.equals("apple"))
                    return true;
            }
        } else if (snake.direction == DIRECTION.DOWN){
            for(int row = x+1; row <height; row++){
                if (board[row][y].type.equals("apple"))
                    return true;
            }
        } else {
            for(int col = y-1; col >=0 ; col--){
                if (board[x][col].type.equals("apple"))
                    return true;
            }
        }
        return false;
    }
    public static void main(String args[]){
        return;
    }
}
