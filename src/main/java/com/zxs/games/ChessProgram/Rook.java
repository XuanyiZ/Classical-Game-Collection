package com.zxs.games.ChessProgram;

public class Rook extends ChessPiece {
    public int player;
    public Rook(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        int rowChange = Math.abs(startRow - endRow);
        int colChange = Math.abs(startCol - endCol);
        if ( (rowChange == 0 && colChange != 0 ) || (rowChange!=0 && colChange == 0)){
            ChessPiece pieceToGo = chessBoard.chessBoard[endRow][endCol];
            if (pieceToGo != null && pieceToGo.getPlayer()==player)
                return false;
            if (startRow == endRow){
                for(int col = Math.min(startCol,endCol)+1; col < Math.max(startCol,endCol); col++ ){
                    if (chessBoard.chessBoard[startRow][col] != null)
                        return false;
                }
                return true;
            }else { // startCol == endCol
                for(int row = Math.min(startRow, endRow)+1; row < Math.max(startRow,endRow); row++ ){
                    if (chessBoard.chessBoard[row][startCol] != null)
                        return false;

                }
                return true;
            }
        }else{
            return false;
        }

    }
    public String toString(){
        if (player == 0){
            return "R";
        }else
            return "r";
    }

    public ChessPiece makeCopy(ChessPiece other){
        return new Rook(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackRook.png";
        }else
            return "WhiteRook.png";
    }
}
