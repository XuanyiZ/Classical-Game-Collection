package com.zxs.games.Snake;
import java.util.*;
public class Snake {
    public LinkedList<Grid> snakeBody;
    public int score = 0;
    public double speed = 1.0;
    public DIRECTION direction;
    public boolean isChanging = false;
    public int index = 0;
    public Snake(int index){
        this.index = index;
    }

}
