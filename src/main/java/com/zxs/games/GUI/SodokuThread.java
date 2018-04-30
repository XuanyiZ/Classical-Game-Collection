package com.zxs.games.GUI;

public class SodokuThread extends Thread {

    public void run() {
        new com.zxs.games.SudokuView.Sudoku();
    }

    public static void main(String args[]) {
        (new SodokuThread()).start();

    }

}
