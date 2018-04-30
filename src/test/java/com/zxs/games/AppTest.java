package com.zxs.games;

import static org.junit.Assert.assertTrue;
import static com.zxs.games.Snake.DIRECTION.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import com.zxs.games.Snake.DIRECTION;
import com.zxs.games.Snake.PairInt;
import com.zxs.games.Snake.Snake;
import com.zxs.games.Snake.SnakeGame;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void main() {
        SnakeGame g1 = new SnakeGame(50,50);
        SnakeGame g = new SnakeGame();
        Snake snake1=g.snake1;
        Snake snake2=g.snake2;

        assertTrue(50==g.getHeadRow(snake1));
        assertTrue(51==g.getTailRow(snake1));
        assertTrue(75==g.getHeadCol(snake1));
        assertTrue(75==g.getTailCol(snake1));

        PairInt head2=g.getNextHead(snake1);

        assertTrue(49==head2.getX());
        assertTrue(75==head2.getY());

        System.out.println(g.snake1.direction); //UP
        assertTrue(false==g.changeDirection(snake1,UP));
        snake1.isChanging=false;
        assertTrue(false==g.changeDirection(snake1,DOWN));
        snake1.isChanging=false;
        assertTrue(true==g.changeDirection(snake1,LEFT));
        snake1.isChanging=false;

        PairInt head3=g.getNextHead(snake1);
        assertTrue(50==head3.getX());

        assertTrue(74==head3.getY());

        assertTrue(true==g.changeDirection(snake1,UP));

        assertTrue(true==g.canMove(snake1));

        assertTrue(50==g.getHeadRow(snake1));

        assertTrue(75==g.getHeadCol(snake1));

        g.move(snake1);
        assertTrue(49==g.getHeadRow(snake1));
        assertTrue(75==g.getHeadCol(snake1));

        g.move(snake1);
        assertTrue(48==g.getHeadRow(snake1));
        assertTrue(75==g.getHeadCol(snake1));

        g.move(snake1);
        assertTrue(47==g.getHeadRow(snake1));
        assertTrue(75==g.getHeadCol(snake1));

        g.board[46][75].type="apple";
        g.move(snake1);


        assertTrue(1==snake1.score);

        g.moveBothSnake();

        for(int i=0; i<50; i++){
            g.move(snake1);
        }
        assertTrue(true==g.gameEnd);
    }

    @Test
    public void changeDirectionTest(){
        SnakeGame g = new SnakeGame();
        Snake snake1=g.snake1;
        Snake snake2=g.snake2;
        assertTrue(g.snake1.direction == DIRECTION.UP);
        snake1.isChanging=false;
        assertFalse(g.changeDirection(snake1,DIRECTION.DOWN));
        snake1.isChanging=false;
        assertTrue(g.changeDirection(snake1,DIRECTION.LEFT));
        snake1.isChanging=false;
        assertFalse(g.changeDirection(snake1,DIRECTION.LEFT));
        snake1.isChanging=false;
        assertTrue(g.changeDirection(snake1,DIRECTION.UP));
        snake1.isChanging=false;
    }

    @Test
    public void canMoveTest(){
        SnakeGame g = new SnakeGame();
        Snake snake1=g.snake1;
        Snake snake2=g.snake2;
        PairInt nextHead = g.getNextHead(snake1);
        g.board[nextHead.x][nextHead.y].type = "obstacle";
        assertFalse(g.canMove(snake1));
        g.board[nextHead.x][nextHead.y].type = "empty";
        assertTrue(g.canMove(snake1));
        g.board[nextHead.x][nextHead.y].type = "apple";
        assertTrue(g.canMove(snake1));

        SnakeGame g1 = new SnakeGame();
        Snake snake11=g1.snake1;
        Snake snake12=g1.snake2;
        g1.changeDirection(snake11,DIRECTION.RIGHT);
        g1.changeDirection(snake12,DIRECTION.LEFT);
        for(int i=0; i<50; i++){
            g1.moveBothSnake();
        }

    }

    @Test
    public void canMoveTestTail(){
        SnakeGame g = new SnakeGame();
        String args[] = new String [1];
        args[0] = "hahaha";
        SnakeGame.main(args);
        Snake snake1=g.snake1;
        Snake snake2=g.snake2;
        int row = 51, col = 49;
        g.board[row][col].type = "body";
        g.snake1.snakeBody.add(g.board[row][col]);
        row = 50; col = 49;
        g.board[row][col].type = "body";
        g.snake1.snakeBody.add(g.board[row][col]);
        g.direction = DIRECTION.LEFT;
        assertTrue(g.canMove(snake1));
        row = 49; col = 49;
        g.board[row][col].type = "body";
        g.snake1.snakeBody.add(g.board[row][col]);
        assertTrue(g.canMove(snake1));
    }
}
