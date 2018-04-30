package com.zxs.games.ChessProgram;

public class Queen extends ChessPiece {
    public int player;
    public Queen(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        Bishop tempBishop = new Bishop(player);
        Rook tempRook = new Rook (player);
        return tempBishop.canMove(startRow, startCol, endRow, endCol, chessBoard)
                || tempRook.canMove(startRow,startCol,endRow,endCol, chessBoard) ;
    }
    public String toString(){
        if (player == 0){
            return "Q";
        }else
            return "q";
    }

    public ChessPiece makeCopy(ChessPiece other){
        return new Queen(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackQueen.png";
        }else
            return "WhiteQueen.png";
    }
}
