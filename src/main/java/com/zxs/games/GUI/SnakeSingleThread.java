package com.zxs.games.GUI;
public class SnakeSingleThread extends Thread{
    public void run() {
        new  com.zxs.games.GUI_single.SnakeGUI();
    }

    public static void main(String args[]) {
        (new SnakeSingleThread()).start();
    }
}
