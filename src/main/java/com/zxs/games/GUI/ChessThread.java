package com.zxs.games.GUI;

import com.zxs.games.ChessGameGUI.ChessBoardGUI;

public class ChessThread extends Thread {

    public void run() {
        new ChessBoardGUI();
    }

    public static void main(String args[]) {
        (new ChessThread()).start();

    }

}