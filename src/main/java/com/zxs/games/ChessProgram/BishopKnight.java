package com.zxs.games.ChessProgram;

public class BishopKnight extends ChessPiece {
    public int player;
    public BishopKnight(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        Knight tempKnight = new Knight(player);
        Bishop tempBishop = new Bishop (player);
        return tempKnight.canMove(startRow, startCol, endRow, endCol, chessBoard)
                || tempBishop.canMove(startRow,startCol,endRow,endCol, chessBoard) ;
    }
    public String toString(){
        if (player == 0){
            return "C";
        }else
            return "c";
    }

    public ChessPiece makeCopy(ChessPiece other){
        return new BishopKnight(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackBishopKnight.png";
        }else
            return "WhiteBishopKnight.png";
    }
}
