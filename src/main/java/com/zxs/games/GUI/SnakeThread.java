package com.zxs.games.GUI;

public class SnakeThread extends Thread {

    public void run() {
        new SnakeGUI();
    }

    public static void main(String args[]) {
        (new SnakeThread()).start();
    }

}