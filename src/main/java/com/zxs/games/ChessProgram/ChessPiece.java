package com.zxs.games.ChessProgram;

public abstract class ChessPiece {
    abstract int getPlayer();
    abstract Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard);
    public abstract ChessPiece makeCopy(ChessPiece other);
    public abstract String getIconFileName();
}
