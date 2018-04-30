package com.zxs.games.ChessProgram;

public class RookKnight extends ChessPiece {
    public int player;
    public RookKnight(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        Knight tempKnight = new Knight(player);
        Rook tempRook = new Rook (player);
        return tempKnight.canMove(startRow, startCol, endRow, endCol, chessBoard)
                || tempRook.canMove(startRow,startCol,endRow,endCol, chessBoard) ;
    }
    public String toString(){
        if (player == 0){
            return "A";
        }else
            return "a";
    }

    public ChessPiece makeCopy(ChessPiece other){
        return new RookKnight(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackRookKnight.png";
        }else
            return "WhiteRookKnight.png";
    }
}
