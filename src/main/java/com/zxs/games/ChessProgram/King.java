package com.zxs.games.ChessProgram;

public class King extends ChessPiece {
    public int player;
    public King(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        int rowChange = Math.abs(startRow - endRow);
        int colChange = Math.abs(startCol - endCol);
        if (rowChange >1 || colChange >1)
            return false;
        ChessPiece pieceToGo = chessBoard.chessBoard[endRow][endCol];
        if ( pieceToGo!= null &&  pieceToGo.getPlayer() == player)
            return false;
        return true;
    }
    public String toString(){
        if (player == 0){
            return "K";
        }else
            return "k";
    }
    public ChessPiece makeCopy(ChessPiece other){
        return new King(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackKing.png";
        }else
            return "WhiteKing.png";
    }
}
