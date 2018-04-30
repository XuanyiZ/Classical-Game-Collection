package com.zxs.games.ChessProgram;

public class Bishop extends ChessPiece {
    public int player;
    public Bishop(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        int rowChange = Math.abs(startRow - endRow);
        int colChange = Math.abs(startCol - endCol);
        if (rowChange != colChange )
            return false;
        ChessPiece pieceToGo = chessBoard.chessBoard[endRow][endCol];
        if ( pieceToGo!= null &&  pieceToGo.getPlayer() == player)
            return false;
        double k = (endRow - startRow)*1.0/ (endCol - startCol);
        for(int row = Math.min(startRow, endRow)+1; row< Math.max(startRow,endRow) ; row++){
            for(int col = Math.min(startCol, endCol) +1; col < Math.max(startCol, endCol); col++){
                double newk = ( row - startRow)*1.0 / (col - startCol);
                if (Math.abs(k - newk)<0.0001 ){
                    pieceToGo = chessBoard.chessBoard[row][col];
                    if ( pieceToGo!= null )
                        return false;
                }
            }
        }
        return true;
    }
    public String toString(){
        if (player == 0){
            return "B";
        }else
            return "b";
    }

    public ChessPiece makeCopy(ChessPiece other){
        return new Bishop(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackBishop.png";
        }else
            return "WhiteBishop.png";
    }
}
