package com.zxs.games.ChessProgram;

public class Pawn extends ChessPiece {
    public int player;
    public Pawn(int player){
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    public Boolean canMove(int startRow,int startCol, int endRow, int endCol, ChessBoard chessBoard){
        Boolean isInStartPosition = inStartPosition(startRow);
        int dist = isInStartPosition?  2 : 1;
        if (startRow == endRow){
            return false;
        }
        if ( startCol == endCol ){ // move vertically
            if (player == 0){ // move down
                if (endRow -  startRow<=dist && startRow < endRow){
                    for(int i =startRow+1 ;i<= endRow;i++){
                        if (chessBoard.chessBoard[i][startCol]!= null)
                            return false;
                    }
                    return true;
                }else
                    return false;
            }else { // move up

                if (startRow - endRow <= dist && startRow > endRow) {
                    for(int i =endRow ;i< startRow;i++){
                        if (chessBoard.chessBoard[i][startCol]!= null)
                            return false;
                    }
                    return true;
                }else
                    return false;
            }
        }else{ // eat next col
            if (player == 0){ // move down
                if ( endRow == startRow+1 && (endCol == startCol+1 || endCol == startCol-1)  ){
                    ChessPiece pieceToEat = chessBoard.chessBoard[endRow][endCol];
                    if (pieceToEat == null || pieceToEat.getPlayer() == player)
                        return false;
                    return true;
                }else
                    return false;
            }else{ // move up
                if ( endRow == startRow-1 && (endCol == startCol+1 || endCol == startCol-1)  ){
                    ChessPiece pieceToEat = chessBoard.chessBoard[endRow][endCol];
                    if (pieceToEat == null || pieceToEat.getPlayer() == player)
                        return false;
                    return true;
                }else
                    return false;
            }
        }


    };
    public Boolean inStartPosition(int startRow){
        if (player == 0  && startRow == 1)
            return true;
        if (player == 1 && startRow == 6)
            return true;
        return false;
    }
    public String toString(){
        if (player == 0){
            return "P";
        }else
            return "p";
    }
    public ChessPiece makeCopy(ChessPiece other){
        return new Pawn(other.getPlayer());
    }

    public String getIconFileName(){
        if (player == 0){
            return "BlackPawn.png";
        }else
            return "WhitePawn.png";
    }
}
