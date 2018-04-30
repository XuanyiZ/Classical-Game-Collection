package com.zxs.games.Snake;

public class Grid {
    public int x,y;
    public String color;
    public String type; //"body", "head", "empty", "apple", "obstacle"
    public Grid(int x, int y, String color, String type){
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
    }
}
