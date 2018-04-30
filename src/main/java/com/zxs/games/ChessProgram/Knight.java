package com.zxs.games.ChessProgram;

public class Knight extends ChessPiece {
    public int player;
    public Knight(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        int rowChange = Math.abs(startRow- endRow);
        int colChange = Math.abs(startCol - endCol);
        if ( (rowChange == 2 && colChange ==1 ) || (rowChange == 1 && colChange == 2) ){
            ChessPiece pieceToGo = chessBoard.chessBoard[endRow][endCol];
            if ( pieceToGo!= null &&  pieceToGo.getPlayer() == player) {
                return false;
            }else
                return true;
        }else{
            return false;
        }

    }
    public String toString(){
        if (player == 0){
            return "N";
        }else
            return "n";
    }
    public ChessPiece makeCopy(ChessPiece other){
        return new Knight(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackKnight.png";
        }else
            return "WhiteKnight.png";
    }
}
