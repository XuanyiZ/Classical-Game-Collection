package com.zxs.games.ChessProgram;

public class moveStats {
    public int startRow,startCol,endRow,endCol;
    public ChessPiece startPiece = null ,endPiece = null;
    public moveStats(int startRow, int startCol,int endRow,int endCol, ChessPiece startPiece, ChessPiece endPiece){
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        Class start = startPiece.getClass();
        if (startPiece != null)
            this.startPiece = startPiece.makeCopy(startPiece);
        if (endPiece != null)
            this.endPiece = endPiece.makeCopy(endPiece);
    }
}
